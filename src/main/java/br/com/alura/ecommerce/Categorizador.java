package br.com.alura.ecommerce;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

import java.time.Duration;
import java.util.Arrays;
import java.util.Scanner;

public class Categorizador {

    public static void main(String[] args) {
        var input = new Scanner(System.in);

           System.out.println("Digite as categorias válidas: ");
           var categorias = input.nextLine();

           while(true){
             System.out.println("\nDigite o nome do produto: ");
           var user = input.nextLine();

           var system = """
                   Você é um categorizador de produtos e deve responder apenas o nome da cateogira do produto informado
                   
                   Escolha uma categoria dentro da lista abaixo:
                   
                   %s
                   
                   ##### exemplo de uso:
                   Pergunta: Bola de futebol
                   Resposta: Esportes
                   
                   ##### regras a serem seguidas:
                   Caso o usuário pergunte algo que não seja de categoriazação de produtos, você deve responder que não pode ajudar, pois seu papel é apenas responder a categoria dos produtos.
                   """.formatted(categorias);

           sendRequest(user, system);
       }

    }

    public static void sendRequest(String user, String system){
        var chave = System.getenv("OPENAI_API_KEY");
        var service = new OpenAiService(chave, Duration.ofSeconds(30));

        var completionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-4")
                .messages(Arrays.asList(
                        new ChatMessage(ChatMessageRole.USER.value(), user),
                        new ChatMessage(ChatMessageRole.SYSTEM.value(), system)
                ))
                .build();

        service.createChatCompletion(completionRequest)
                .getChoices()
                .forEach(c -> System.out.print(c.getMessage().getContent()));
    }

}
