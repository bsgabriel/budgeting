package bsg.budgeting.ai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.ai.audio.tts.TextToSpeechModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionAiService {

    private final ChatClient chatClient;
    private final TextToSpeechModel textToSpeechModel;
    private final TranscriptionModel transcriptionModel;

    public byte[] createTransaction(MultipartFile file) {
        var userMessage = transcriptionModel.transcribe(file.getResource());
        log.info("Mensagem do usuário: {}", userMessage);

        var result = chatClient.prompt().user(userMessage).call().content();
        log.info("Resposta da IA: {}", result);

        return textToSpeechModel.call(result);
    }

}