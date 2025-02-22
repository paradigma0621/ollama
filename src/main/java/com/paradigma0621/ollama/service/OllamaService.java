package com.paradigma0621.ollama.service;

import com.paradigma0621.ollama.dto.CountryCuisines;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

import static java.util.Objects.nonNull;

@Service
public class OllamaService {
	
	private ChatClient chatClient;

	@Autowired
	private EmbeddingModel embeddingModel;

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
		var promptPattern =
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

		var response = chatClient.prompt(prompt).call().chatResponse();
		return nonNull(response) ? response.getResult().getOutput().getText() : "No response";
	}

	public CountryCuisines getCuisines(String country, String numCuisines, String language) {

		String promptModel = """
		You are an expert in traditional cuisines.
		Answer the question: What is the traditional cuisine of {country}?
		Return a list of {numCuisines} in {language}.
		You provide information about a specific dish
		from a specific country.
		Avoid giving information about fictional places.
		If the country is fictional or non-existent
		return the country with out any cuisines.
		""";

		PromptTemplate promptTemplate = new PromptTemplate(promptModel);

		Prompt prompt = promptTemplate
				.create(Map.of("country", country, "numCuisines", numCuisines, "language", language));

		return chatClient.prompt(prompt).call().entity(CountryCuisines.class); // Return the entity CountryCuisines, in
														// wictch field is a Collection
	}

}
