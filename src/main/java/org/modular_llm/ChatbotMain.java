package org.modular_llm;

import java.util.Scanner;

public class ChatbotMain {
    public static void main(String[] args) {
        PluginManager pm = new PluginManager();
        AIModel model = new AIModel();
        ResponseCoordinator coordinator = new DefaultCoordinator(pm, model);

        // Load any plugins you want available
        pm.loadPlugin("WeatherPlugin");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Chatbot is ready. Type your message (type 'exit' to quit):");

        while (true) {
            System.out.print("You: ");
            String input = scanner.nextLine();
            if ("exit".equalsIgnoreCase(input)) break;

            ChatbotResponse response = coordinator.handleInput(input);
            System.out.println(response);
        }

        pm.unloadPlugin("WeatherPlugin");
        scanner.close();
    }
}
