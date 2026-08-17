package bsg.budgeting.ai.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.ai.audio.tts.TextToSpeechModel;
import org.springframework.ai.chat.client.ChatClient;
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

    private final TranscriptionModel transcriptionModel;
    private final ChatClient chatClient;
    private final TextToSpeechModel textToSpeechModel;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "audio/mp3")
    public ResponseEntity<ByteArrayResource> transcribe(@RequestBody MultipartFile file) {
        try {
            var userMessage = transcriptionModel.transcribe(file.getResource());
            log.info("Mensagem do usuário: {}", userMessage);

            var result = chatClient.prompt().user(userMessage).call().content();
            log.info("Resposta da IA: {}", result);

            var audio = new ByteArrayResource(textToSpeechModel.call(result));

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
