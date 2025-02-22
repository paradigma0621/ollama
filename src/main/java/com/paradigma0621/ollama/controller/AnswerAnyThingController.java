package com.paradigma0621.ollama.controller;

import com.paradigma0621.ollama.dto.CountryCuisines;
import com.paradigma0621.ollama.service.OllamaService;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;


@RestController
@RequestMapping("/api")
public class AnswerAnyThingController {

	@Autowired
    private OllamaService service;

    Logger logger = Logger.getLogger(getClass().getName());

    @PostMapping("/askAnything")
    public String askAnything(@RequestBody String question) {
    	ChatResponse response = service.generateAnswer(question);
        logger.info("Response: \n" + response.getResult().getOutput().getText());
        return response.getResult().getOutput().getText();
    }

    @GetMapping("/getTravelGuidance")
    public String askAnything(@RequestParam String city,
                              @RequestParam String month,
                              @RequestParam String language,
                              @RequestParam String budget) {
        String response = service.getTravelGuidance(city, month, language, budget);
        return response;
    }

    @GetMapping("/cuisineHelper")
    public CountryCuisines getChatResponse(@RequestParam("country") String country,
                                  @RequestParam("numCuisines") String numCuisines,
                                  @RequestParam("language") String language) {
        CountryCuisines countryCuisines = service.getCuisines(country,numCuisines,language);
        return countryCuisines;
    }

}