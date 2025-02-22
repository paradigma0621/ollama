package com.paradigma0621.ollama.controller;

import com.paradigma0621.ollama.service.EmbeddingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class EmbeddingController {

	@Autowired
	private EmbeddingService service;

	@GetMapping("/embedding")
	public String embed(@RequestParam String text) { // 'text' can be any string (a single word or a sentence)
		float[] response = service.embed(text);
		return Arrays.toString(response);
	}

	@GetMapping("/similarityFinder")
	public double findSimilarity(@RequestParam String text1, @RequestParam String text2) { // 'text1' and 'text2' can be
																			// any string (a single word or a sentence)
		return service.findSimilarity(text1, text2);
	}
}