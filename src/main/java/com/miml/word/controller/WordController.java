package com.miml.word.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miml.common.api.ApiResponse;
import com.miml.common.api.ApiResponseEmptyBody;
import com.miml.word.dto.WordDto;
import com.miml.word.service.WordService;

@RestController
@RequestMapping("/api/word")
public class WordController {
	
	private WordService wordService;
	
	public WordController(WordService wordService) {
		this.wordService = wordService;
	}
	
	// @GetMapping
	// public ResponseEntity<ApiResponse<List<WordDto>>> getWordList () {
		
	// 	List<WordDto> wordDtos = wordService.getWordList();
	// 	return wordDtos;
		
	// }

	@PostMapping
	public ResponseEntity<ApiResponse<ApiResponseEmptyBody>> addWord(WordDto wordDto) {
		
		wordService.addWord(wordDto);
		
		return ApiResponse.toOkResponseEntity();
	}
	
	
	
}
