package bsg.budgeting.controller;

import bsg.budgeting.dto.request.CreateTransactionRequestDto;
import bsg.budgeting.dto.response.TransactionResponseDto;
import bsg.budgeting.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<TransactionResponseDto>> findTransactions(@RequestParam(required = false) String category) {
        return ResponseEntity.ok(transactionService.findTransactions(category));
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDto> createTransaction(@RequestBody CreateTransactionRequestDto request) {
        return ResponseEntity.ok(transactionService.saveTransaction(request));
    }

}
