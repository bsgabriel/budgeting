package bsg.budgeting.service;

import bsg.budgeting.dto.request.CreateTransactionRequestDto;
import bsg.budgeting.dto.response.TransactionResponseDto;
import bsg.budgeting.entity.Category;
import bsg.budgeting.entity.Transaction;
import bsg.budgeting.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryService categoryService;

    public List<TransactionResponseDto> findTransactions(String category) {
        var transactions = category != null
                ? transactionRepository.findByCategory(category)
                : transactionRepository.findAll();

        return transactions.stream()
                .map(transaction -> TransactionResponseDto.builder()
                        .transactionId(transaction.getTransactionId())
                        .description(transaction.getDescription())
                        .amount(transaction.getAmount())
                        .category(transaction.getCategory().getDescription())
                        .type(transaction.getType().toString())
                        .build())
                .toList();
    }

    public TransactionResponseDto saveTransaction(CreateTransactionRequestDto transactionDto) {
        var categoryDto = categoryService.findOrCreate(transactionDto.getCategory());

        var saved = transactionRepository.save(Transaction.builder()
                .description(transactionDto.getDescription())
                .amount(transactionDto.getAmount())
                .type(transactionDto.getType())
                .category(Category.builder()
                        .categoryId(categoryDto.getCategoryId())
                        .build())
                .build());

        return TransactionResponseDto.builder()
                .transactionId(saved.getTransactionId())
                .description(saved.getDescription())
                .amount(saved.getAmount())
                .category(saved.getCategory().getDescription())
                .type(saved.getType().toString())
                .build();
    }
}
