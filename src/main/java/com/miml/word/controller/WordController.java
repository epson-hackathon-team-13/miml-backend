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
import com.miml.word.dto.WordPostReqDto;
import com.miml.word.service.WordService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/word")
public class WordController {
	
	private WordService wordService;
	
	public WordController(WordService wordService) {
		this.wordService = wordService;
	}
	
	@Operation(summary = "로그인된 사용자 단어장 조회")
	@GetMapping
	public ResponseEntity<ApiResponse<List<WordDto>>> getWordList () {
		List<WordDto> wordDtos = wordService.getWordList();
		return ApiResponse.toOkResponseEntity(wordDtos);
	}

	@Operation(summary = "로그인된 사용자 단어장에 단어 추가")
	@PostMapping
	public ResponseEntity<ApiResponse<ApiResponseEmptyBody>> addWord(WordPostReqDto wordPostReqDto) {
		
		if(wordPostReqDto != null) wordService.addWord(wordPostReqDto);
		
		return ApiResponse.toOkResponseEntity();
	}
	
	
	
}
