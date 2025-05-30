package dev.langchain4j.http.client.spring.restclient;

import dev.langchain4j.exception.HttpException;
import dev.langchain4j.exception.TimeoutException;
import dev.langchain4j.http.client.HttpClient;
import dev.langchain4j.http.client.HttpRequest;
import dev.langchain4j.http.client.SuccessfulHttpResponse;
import dev.langchain4j.http.client.sse.ServerSentEventListener;
import dev.langchain4j.http.client.sse.ServerSentEventParser;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.io.InputStream;
import java.net.SocketTimeoutException;

import static dev.langchain4j.http.client.sse.ServerSentEventListenerUtils.ignoringExceptions;
import static dev.langchain4j.internal.Utils.getOrDefault;

public class SpringRestClient implements HttpClient {

    private final RestClient delegate;
    private final AsyncTaskExecutor streamingRequestExecutor;

    public SpringRestClient(SpringRestClientBuilder builder) {

        RestClient.Builder restClientBuilder = getOrDefault(builder.restClientBuilder(), RestClient::builder);

        ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.DEFAULTS;
        if (builder.connectTimeout() != null) {
            settings = settings.withConnectTimeout(builder.connectTimeout());
        }
        if (builder.readTimeout() != null) {
            settings = settings.withReadTimeout(builder.readTimeout());
        }
        ClientHttpRequestFactory clientHttpRequestFactory = ClientHttpRequestFactories.get(settings);

        this.delegate = restClientBuilder
                .requestFactory(clientHttpRequestFactory)
                .build();

        this.streamingRequestExecutor = getOrDefault(builder.streamingRequestExecutor(), () -> {
            if (builder.createDefaultStreamingRequestExecutor()) {
                return createDefaultStreamingRequestExecutor();
            } else {
                return null;
            }
        });
    }

    private static AsyncTaskExecutor createDefaultStreamingRequestExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.initialize();
        return taskExecutor;
    }

    public static SpringRestClientBuilder builder() {
        return new SpringRestClientBuilder();
    }

    @Override
    public SuccessfulHttpResponse execute(HttpRequest request) throws HttpException {
        try {
            ResponseEntity<String> responseEntity = toSpringRestClientRequest(request)
                    .retrieve()
                    .toEntity(String.class);

            return SuccessfulHttpResponse.builder()
                    .statusCode(responseEntity.getStatusCode().value())
                    .headers(responseEntity.getHeaders())
                    .body(responseEntity.getBody())
                    .build();
        } catch (RestClientResponseException e) {
            throw new HttpException(e.getStatusCode().value(), e.getMessage());
        } catch (Exception e) {
            if (e.getCause() instanceof SocketTimeoutException) {
                throw new TimeoutException(e);
            } else {
                throw e;
            }
        }
    }

    @Override
    public void execute(HttpRequest request, ServerSentEventParser parser, ServerSentEventListener listener) {
        streamingRequestExecutor.execute(() -> {
            try {
                toSpringRestClientRequest(request)
                        .exchange((springRequest, springResponse) -> {

                            int statusCode = springResponse.getStatusCode().value();

                            if (!springResponse.getStatusCode().is2xxSuccessful()) {
                                String body = springResponse.bodyTo(String.class);

                                HttpException exception = new HttpException(statusCode, body);
                                ignoringExceptions(() -> listener.onError(exception));
                                return null;
                            }

                            SuccessfulHttpResponse response = SuccessfulHttpResponse.builder()
                                    .statusCode(statusCode)
                                    .headers(springResponse.getHeaders())
                                    .build();
                            ignoringExceptions(() -> listener.onOpen(response));

                            try (InputStream inputStream = springResponse.getBody()) {
                                parser.parse(inputStream, listener);
                                ignoringExceptions(listener::onClose);
                            }

                            return null;
                        });
            } catch (Exception e) {
                if (e.getCause() instanceof SocketTimeoutException) {
                    ignoringExceptions(() -> listener.onError(new TimeoutException(e)));
                } else {
                    ignoringExceptions(() -> listener.onError(e));
                }
            }
        });
    }

    private RestClient.RequestBodySpec toSpringRestClientRequest(HttpRequest request) {
        RestClient.RequestBodySpec requestBodySpec = delegate
                .method(org.springframework.http.HttpMethod.valueOf(request.method().name()))
                .uri(request.url())
                .headers(httpHeaders -> httpHeaders.putAll(request.headers()));

        if (request.body() != null) {
            requestBodySpec.body(request.body());
        }

        return requestBodySpec;
    }
}
