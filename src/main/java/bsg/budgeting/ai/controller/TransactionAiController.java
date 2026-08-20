package bsg.budgeting.ai.controller;

import bsg.budgeting.ai.service.TransactionAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/ai/transactions")
@RequiredArgsConstructor
public class TransactionAiController {

    private final TransactionAiService transactionAiService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "audio/mp3")
    public ResponseEntity<ByteArrayResource> transcribe(@RequestBody MultipartFile file) {
        try {
            var audio = new ByteArrayResource(transactionAiService.createTransaction(file));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                            .filename("audio.mp3")
                            .build()
                            .toString())
                    .body(audio);
        } catch (Exception e) {
            log.error("Erro ao processar transação por áudio", e);

            var mensagem = "Erro ao processar sua solicitação. Tente novamente.";
            var errorBody = new ByteArrayResource(mensagem.getBytes());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(errorBody);
        }
    }
}
