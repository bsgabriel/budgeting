package bsg.budgeting.ai.configuration;

import bsg.budgeting.ai.tool.CategoryTool;
import bsg.budgeting.ai.tool.TransactionTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class AiConfig {

    @Value("classpath:/prompts/system.st")
    private Resource systemPrompt;

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, TransactionTool transactionTool, CategoryTool categoryTool) {
        return builder.defaultSystem(systemPrompt)
                .defaultTools(transactionTool, categoryTool)
                .build();
    }
}
