package com.paradigma0621.ollama.service;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmbeddingService {

    @Autowired
    private EmbeddingModel embeddingModel;

    public float[] embed(String text) {
        return embeddingModel.embed(text);
    }


    public double findSimilarity(String text1, String text2) {
        List<float[]> response = embeddingModel.embed(List.of(text1, text2));
        return cosineSimilarity(response.get(0), response.get(1));
    }

    private double cosineSimilarity(float[] vectorA, float[] vectorB) {
        if (vectorA.length != vectorB.length) {
            throw new IllegalArgumentException("Vectors must be of the same length");
        }

        // Initialize variables for dot product and magnitudes
        double dotProduct = 0.0;
        double magnitudeA = 0.0;
        double magnitudeB = 0.0;

        // Calculate dot product and magnitudes
        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            magnitudeA += vectorA[i] * vectorA[i];
            magnitudeB += vectorB[i] * vectorB[i];
        }

        // Calculate and return cosine similarity
        return dotProduct / (Math.sqrt(magnitudeA) * Math.sqrt(magnitudeB));
    }
}
