package bsg.budgeting.ai.configuration;

import bsg.budgeting.ai.tool.CategoryTool;
import bsg.budgeting.ai.tool.TransactionTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, TransactionTool transactionTool, CategoryTool categoryTool) {
        return builder
                .defaultSystem("""
                        Você é um assistente financeiro.
                        Sua tarefa é extrair dados de transações e usar as ferramentas disponíveis para manipular transações.
                        Ao registrar uma transação, busque as categorias disponíveis e escolha a que melhor se enquadrar.
                        Caso a transação não se encaixe em nenhuma categoria, crie uma nova.
                        Ao cadastrar uma categoria, certifique-se de não usar algo muito extenso. A ideia é ser simples e objetivo.
                        Informe ao usuário qual categoria foi utilizada e se ela foi criada agora.
                        
                        Sua resposta será utilizada para gerar um áudio, portanto nada de "decorações" de texto (negrito, itálico, etc).
                        """)
                .defaultTools(transactionTool, categoryTool)
                .build();
    }
}
