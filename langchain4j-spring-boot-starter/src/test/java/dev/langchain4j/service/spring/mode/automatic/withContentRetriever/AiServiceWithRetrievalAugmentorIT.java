package dev.langchain4j.service.spring.mode.automatic.withContentRetriever;

import dev.langchain4j.service.spring.AiServicesAutoConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static dev.langchain4j.service.spring.mode.ApiKeys.OPENAI_API_KEY;
import static org.assertj.core.api.Assertions.assertThat;

class AiServiceWithRetrievalAugmentorIT {

    ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(AiServicesAutoConfig.class));

    @Test
    void should_create_AI_service_with_content_retriever() {
        contextRunner
                .withPropertyValues(
                        "langchain4j.open-ai.chat-model.api-key=" + OPENAI_API_KEY,
                        "langchain4j.open-ai.chat-model.max-tokens=20",
                        "langchain4j.open-ai.chat-model.temperature=0.0"
                )
                .withUserConfiguration(AiServiceWithContentRetrieverApplication.class)
                .run(context -> {

                    // given
                    AiServiceWithContentRetriever aiService = context.getBean(AiServiceWithContentRetriever.class);

                    // when
                    String answer = aiService.chat("What is my name?");

                    // then
                    assertThat(answer).containsIgnoringCase("Klaus");
                });
    }
}