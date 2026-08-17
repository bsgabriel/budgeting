package bsg.budgeting.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.tool.annotation.ToolParam;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionRequestDto {

    @NotBlank
    @ToolParam(description = "Descrição da transação")
    private String description;

    @NotNull
    @Positive
    @ToolParam(description = "Valor gasto em reais")
    private BigDecimal amount;

    @NotNull
    @ToolParam(description = "Categoria da transação")
    private String category;
}
