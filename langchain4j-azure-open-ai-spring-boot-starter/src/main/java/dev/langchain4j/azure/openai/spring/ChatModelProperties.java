package dev.langchain4j.azure.openai.spring;

import com.azure.core.credential.TokenCredential;
import com.azure.core.http.ProxyOptions;
import dev.langchain4j.model.Tokenizer;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.List;

@Getter
@Setter
public class ChatModelProperties {

    private String endpoint;
    private String serviceVersion;
    private String apiKey;
    private String nonAzureApiKey;
    private TokenCredential tokenCredential;
    private String deploymentName;
    private Tokenizer tokenizer;
    private Double temperature;
    private Double topP;
    private Integer maxTokens;
    private List<String> stop;
    private Double presencePenalty;
    private Double frequencyPenalty;
    private Duration timeout;
    private Integer maxRetries;
    private ProxyOptions proxyOptions;
    private Boolean logRequestsAndResponses;
}
