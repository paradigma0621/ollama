package com.paradigma0621.ollama.controller;

import com.paradigma0621.ollama.service.OllamaService;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AnswerAnyThingController {

    private OllamaService service;

    private static final Logger logger = LoggerFactory.getLogger(AnswerAnyThingController.class);

    @PostMapping("/askAnything")
    public String askAnything(@RequestBody String question) {
    	ChatResponse response = service.generateAnswer(question);
        logger.info("Response:\n{}", response.getResult().getOutput().getContent());
        return response.getResult().getOutput().getContent();
    }
}