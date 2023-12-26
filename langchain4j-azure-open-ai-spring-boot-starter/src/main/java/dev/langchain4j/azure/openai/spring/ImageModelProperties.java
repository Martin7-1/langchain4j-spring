package dev.langchain4j.azure.openai.spring;

import com.azure.core.credential.TokenCredential;
import com.azure.core.http.ProxyOptions;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

@Getter
@Setter
public class ImageModelProperties {

    private String endpoint;
    private String serviceVersion;
    private String apiKey;
    private String nonAzureApiKey;
    private TokenCredential tokenCredential;
    private String deploymentName;
    private String quality;
    private String size;
    private String user;
    private String style;
    private String responseFormat;
    private Duration timeout;
    private Integer maxRetries;
    private ProxyOptions proxyOptions;
    private Boolean logRequestsAndResponses;
}
