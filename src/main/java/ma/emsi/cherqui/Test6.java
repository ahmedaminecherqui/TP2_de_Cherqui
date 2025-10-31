package ma.emsi.cherqui;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.service.AiServices;
import ma.emsi.cherqui.tools.meteo.MeteoTool;

import java.time.Duration;
import java.util.Scanner;

public class Test6 {
    public static void main(String[] args) {
        interface AssistantMeteo {
            String chat(String userMessage);
        }
            //  Load Gemini API key
            String apiKey = System.getenv("GEMINI_API_KEY");

            //  Chat model (LLM)
            ChatModel chatModel = GoogleAiGeminiChatModel.builder()
                    .apiKey(apiKey)
                    .modelName("gemini-2.5-flash")  // works with LangChain4j
                    .temperature(0.2)
                    .responseFormat(ResponseFormat.TEXT) // TEXT or JSON
                    .timeout(Duration.ofSeconds(60))
                    .logRequestsAndResponses(true)
                    .build();

            AssistantMeteo assistant =
                    AiServices.builder(AssistantMeteo.class)
                            .chatModel(chatModel)
                            .tools(new MeteoTool())  // Ajout de l'outil
                            .build();

            try (
            Scanner scanner = new Scanner(System.in)) {
                while (true) {
                    System.out.println("==================================================");
                    System.out.println("Posez votre question : ");
                    String question = scanner.nextLine();
                    if (question.isBlank()) {
                        continue;
                    }
                    System.out.println("==================================================");
                    if ("fin".equalsIgnoreCase(question)) {
                        break;
                    }
                    String reponse = assistant.chat(question);
                    System.out.println("Assistant : " + reponse);
                    System.out.println("==================================================");
                }
        }
    }
}
