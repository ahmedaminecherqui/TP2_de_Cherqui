package ma.emsi.cherqui;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;

import java.time.Duration;
import java.util.Map;

public class Test2 {
    public static void main(String[] args) {
        PromptTemplate template = PromptTemplate.from("""
            Vous etes un traducteur qui connait tous les langues,
             vous devez traduire ce que l'utilisateur envoie : {{text}} en langue choisi : {{langue}}
           """);

        Prompt prompt = template.apply(Map.of(
                "text", "HIIIII,how is it going !",
                "langue", "arabe"
        ));

        String cle = System.getenv("GEMINI_API_KEY");
        ChatModel modele =
                GoogleAiGeminiChatModel.builder()
                        .apiKey(cle)
                        .modelName("gemini-2.5-flash")
                        .temperature(0.8)
                        .timeout(Duration.ofSeconds(60))
                        .responseFormat(ResponseFormat.JSON)
                        .build();

        String reponse = modele.chat(prompt.text());
        System.out.println(reponse);
    }
}
