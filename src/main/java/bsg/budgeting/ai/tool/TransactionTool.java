package bsg.budgeting.ai.tool;

import bsg.budgeting.dto.request.CreateTransactionRequestDto;
import bsg.budgeting.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionTool {

    private final TransactionService transactionService;

    @Tool(description = "Salva uma nova transação financeira quando o usuário informa um gasto ou ganho.")
    public String salvarTransacao(CreateTransactionRequestDto request) {
        try {
            transactionService.saveTransaction(request);
            return "Transação salva com sucesso.";
        } catch (Exception e) {
            log.error("Erro ao salvar transação", e);
            return "Erro ao salvar transação. Peça para o usuário confirmar os dados.";
        }
    }

}
