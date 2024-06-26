package com.miml.music.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.miml.common.api.ApiResponse;
import com.miml.music.dto.MusicDto;
import com.miml.music.service.MusicService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/music")
public class MusicContoller {

	private final MusicService musicService;
	
    public MusicContoller(MusicService musicService) {
		this.musicService = musicService;
	}

    @Operation(summary = "{id}로 노래 정보 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MusicDto>> getMusicById(@PathVariable("id") Long id) throws JsonProcessingException {
    	MusicDto musicDto = musicService.getMusicById(id);
    	return ApiResponse.toOkResponseEntity(musicDto);
    }
    
    @Operation(summary = "모든 노래 정보 리스트 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<List<MusicDto>>> getMusicList() {
    	List<MusicDto> list = musicService.getMusicList();
    	return ApiResponse.toOkResponseEntity(list);
    }
 	
}
