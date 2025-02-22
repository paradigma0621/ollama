package com.paradigma0621.ollama.controller;

import com.paradigma0621.ollama.service.OllamaService;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
}