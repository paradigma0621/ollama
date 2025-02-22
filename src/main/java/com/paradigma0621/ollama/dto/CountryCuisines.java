package com.paradigma0621.ollama.dto;

import java.util.List;

public record CountryCuisines(String country, String numCuisines, List<String> cuisines) {

}
