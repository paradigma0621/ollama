package com.paradigma0621.ollama.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Service
public class OllamaService {
	
	private ChatClient chatClient;
	
	public OllamaService(ChatClient.Builder builder) {
		chatClient = builder.defaultAdvisors(new MessageChatMemoryAdvisor
				(new InMemoryChatMemory())).build();	// Add memory to the chat client
	}
	
	public ChatResponse generateAnswer(String question) {
		return chatClient.prompt(question).call().chatResponse();
	}

	public String getTravelGuidance(@RequestParam String city,
									@RequestParam String month,
									@RequestParam String language,
									@RequestParam String budget) {
		String promptPattern =
				"""
				Welcome to the {city} travel guide!
				If you're visiting in {month}, here's what you can do: 
				1. Must-visit attractions.
				2. Local cuisine you must try. 
				3. Useful phrases in {language}.
				4. Tips for traveling on a {budget} budget." 
				Enjoy your trip!
				""";

		PromptTemplate promptTemplate = new PromptTemplate(promptPattern);

		Prompt prompt = promptTemplate
				.create(Map.of("city", city, "month", month, "language", language, "budget", budget));

		return chatClient.prompt(prompt).call().chatResponse().getResult().getOutput().getText();
	}

	

}
