package dev.langchain4j.azure.openai.spring;

import dev.langchain4j.model.azure.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import static dev.langchain4j.azure.openai.spring.AzureOpenAiProperties.PREFIX;

@AutoConfiguration
@EnableConfigurationProperties(AzureOpenAiProperties.class)
public class AuzreOpenAiAutoConfiguration {

    @Bean
    @ConditionalOnProperty(PREFIX + ".chat-model")
    AzureOpenAiChatModel azureOpenAiChatModel(AzureOpenAiProperties properties) {
        ChatModelProperties chatModelProperties = properties.getChatModel();
        return AzureOpenAiChatModel.builder()
                .endpoint(chatModelProperties.getEndpoint())
                .serviceVersion(chatModelProperties.getServiceVersion())
                .apiKey(chatModelProperties.getApiKey())
                .nonAzureApiKey(chatModelProperties.getNonAzureApiKey())
                .tokenCredential(chatModelProperties.getTokenCredential())
                .deploymentName(chatModelProperties.getDeploymentName())
                .tokenizer(chatModelProperties.getTokenizer())
                .temperature(chatModelProperties.getTemperature())
                .topP(chatModelProperties.getTopP())
                .maxTokens(chatModelProperties.getMaxTokens())
                .stop(chatModelProperties.getStop())
                .presencePenalty(chatModelProperties.getPresencePenalty())
                .frequencyPenalty(chatModelProperties.getFrequencyPenalty())
                .timeout(chatModelProperties.getTimeout())
                .maxRetries(chatModelProperties.getMaxRetries())
                .proxyOptions(chatModelProperties.getProxyOptions())
                .logRequestsAndResponses(chatModelProperties.getLogRequestsAndResponses())
                .build();
    }

    @Bean
    @ConditionalOnProperty(PREFIX + ".streaming-chat-model")
    AzureOpenAiStreamingChatModel azureOpenAiStreamingChatModel(AzureOpenAiProperties properties) {
        ChatModelProperties chatModelProperties = properties.getChatModel();
        return AzureOpenAiStreamingChatModel.builder()
                .endpoint(chatModelProperties.getEndpoint())
                .serviceVersion(chatModelProperties.getServiceVersion())
                .apiKey(chatModelProperties.getApiKey())
                .nonAzureApiKey(chatModelProperties.getNonAzureApiKey())
                .tokenCredential(chatModelProperties.getTokenCredential())
                .deploymentName(chatModelProperties.getDeploymentName())
                .tokenizer(chatModelProperties.getTokenizer())
                .temperature(chatModelProperties.getTemperature())
                .topP(chatModelProperties.getTopP())
                .maxTokens(chatModelProperties.getMaxTokens())
                .stop(chatModelProperties.getStop())
                .presencePenalty(chatModelProperties.getPresencePenalty())
                .frequencyPenalty(chatModelProperties.getFrequencyPenalty())
                .timeout(chatModelProperties.getTimeout())
                .maxRetries(chatModelProperties.getMaxRetries())
                .proxyOptions(chatModelProperties.getProxyOptions())
                .logRequestsAndResponses(chatModelProperties.getLogRequestsAndResponses())
                .build();
    }

    @Bean
    @ConditionalOnProperty(PREFIX + ".language-model")
    AzureOpenAiLanguageModel azureOpenAiLanguageModel(AzureOpenAiProperties properties) {
        LanguageModelProperties languageModelProperties = properties.getLanguageModel();
        return AzureOpenAiLanguageModel.builder()
                .endpoint(languageModelProperties.getEndpoint())
                .serviceVersion(languageModelProperties.getServiceVersion())
                .apiKey(languageModelProperties.getApiKey())
                .nonAzureApiKey(languageModelProperties.getNonAzureApiKey())
                .tokenCredential(languageModelProperties.getTokenCredential())
                .deploymentName(languageModelProperties.getDeploymentName())
                .tokenizer(languageModelProperties.getTokenizer())
                .temperature(languageModelProperties.getTemperature())
                .topP(languageModelProperties.getTopP())
                .maxTokens(languageModelProperties.getMaxTokens())
                .presencePenalty(languageModelProperties.getPresencePenalty())
                .frequencyPenalty(languageModelProperties.getFrequencyPenalty())
                .timeout(languageModelProperties.getTimeout())
                .maxRetries(languageModelProperties.getMaxRetries())
                .proxyOptions(languageModelProperties.getProxyOptions())
                .logRequestsAndResponses(languageModelProperties.getLogRequestsAndResponses())
                .build();
    }

    @Bean
    @ConditionalOnProperty(PREFIX + ".streaming-language-model")
    AzureOpenAiStreamingLanguageModel azureOpenAiStreamingLanguageModel(AzureOpenAiProperties properties) {
        LanguageModelProperties languageModelProperties = properties.getStreamingLanguageModel();
        return AzureOpenAiStreamingLanguageModel.builder()
                .endpoint(languageModelProperties.getEndpoint())
                .serviceVersion(languageModelProperties.getServiceVersion())
                .apiKey(languageModelProperties.getApiKey())
                .nonAzureApiKey(languageModelProperties.getNonAzureApiKey())
                .tokenCredential(languageModelProperties.getTokenCredential())
                .deploymentName(languageModelProperties.getDeploymentName())
                .tokenizer(languageModelProperties.getTokenizer())
                .temperature(languageModelProperties.getTemperature())
                .topP(languageModelProperties.getTopP())
                .maxTokens(languageModelProperties.getMaxTokens())
                .presencePenalty(languageModelProperties.getPresencePenalty())
                .frequencyPenalty(languageModelProperties.getFrequencyPenalty())
                .timeout(languageModelProperties.getTimeout())
                .maxRetries(languageModelProperties.getMaxRetries())
                .proxyOptions(languageModelProperties.getProxyOptions())
                .logRequestsAndResponses(languageModelProperties.getLogRequestsAndResponses())
                .build();
    }

    @Bean
    @ConditionalOnProperty(PREFIX + ".embedding-model.api-key")
    AzureOpenAiEmbeddingModel azureOpenAiEmbeddingModel(AzureOpenAiProperties properties) {
        EmbeddingModelProperties embeddingModelProperties = properties.getEmbeddingModel();
        return AzureOpenAiEmbeddingModel.builder()
                .endpoint(embeddingModelProperties.getEndpoint())
                .serviceVersion(embeddingModelProperties.getServiceVersion())
                .apiKey(embeddingModelProperties.getApiKey())
                .nonAzureApiKey(embeddingModelProperties.getNonAzureApiKey())
                .tokenCredential(embeddingModelProperties.getTokenCredential())
                .deploymentName(embeddingModelProperties.getDeploymentName())
                .tokenizer(embeddingModelProperties.getTokenizer())
                .timeout(embeddingModelProperties.getTimeout())
                .maxRetries(embeddingModelProperties.getMaxRetries())
                .proxyOptions(embeddingModelProperties.getProxyOptions())
                .logRequestsAndResponses(embeddingModelProperties.getLogRequestsAndResponses())
                .build();
    }

    @Bean
    @ConditionalOnProperty(PREFIX + ".image-model.api-key")
    AzureOpenAiImageModel openAiImageModel(AzureOpenAiProperties properties) {
        ImageModelProperties imageModelProperties = properties.getImageModel();
        return AzureOpenAiImageModel.builder()
                .endpoint(imageModelProperties.getEndpoint())
                .serviceVersion(imageModelProperties.getServiceVersion())
                .apiKey(imageModelProperties.getApiKey())
                .nonAzureApiKey(imageModelProperties.getNonAzureApiKey())
                .tokenCredential(imageModelProperties.getTokenCredential())
                .deploymentName(imageModelProperties.getDeploymentName())
                .quality(imageModelProperties.getQuality())
                .size(imageModelProperties.getSize())
                .user(imageModelProperties.getUser())
                .style(imageModelProperties.getStyle())
                .responseFormat(imageModelProperties.getResponseFormat())
                .timeout(imageModelProperties.getTimeout())
                .maxRetries(imageModelProperties.getMaxRetries())
                .proxyOptions(imageModelProperties.getProxyOptions())
                .logRequestsAndResponses(imageModelProperties.getLogRequestsAndResponses())
                .build();
    }
}
