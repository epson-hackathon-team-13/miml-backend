package com.miml.word.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.miml.common.api.ApiException;
import com.miml.common.api.ErrorCode;
import com.miml.common.utils.PrincipalUtil;
import com.miml.music.entity.MusicEntity;
import com.miml.music.repository.MusicRepository;
import com.miml.security.CustomUserDetails;
import com.miml.user.entity.UserEntity;
import com.miml.word.dto.WordDto;
import com.miml.word.dto.WordPostReqDto;
import com.miml.word.entity.WordEntity;
import com.miml.word.repository.WordRepository;

@Service
public class WordService {
	
	private final WordRepository wordRepository;
	private final MusicRepository musicRepository;
	private final PrincipalUtil principalUtil;
	
	public WordService(
			WordRepository wordRepository, 
			MusicRepository musicRepository, 
			PrincipalUtil principalUtil
			) {
		this.wordRepository = wordRepository;
		this.musicRepository = musicRepository;
		this.principalUtil = principalUtil;
	}
	

	public List<WordDto> getWordList() {
		
		CustomUserDetails customUserDetails =  (CustomUserDetails) principalUtil.getPrincipal();
		
		UserEntity userEntity = customUserDetails.getUser();
		
		if(userEntity == null ) {
			throw new IllegalStateException("사용자 정보 없음");
		}
		
		List<WordEntity> wordEntities =  wordRepository.findByUserEntity(userEntity);
		
		List<WordDto> wordDtos = wordEntities.stream()
                .map(WordEntity::toDto)
                .collect(Collectors.toList());
		
		return wordDtos;
	}

	public void addWord(WordPostReqDto wordPostReqDto) {
		
		CustomUserDetails customUserDetails =  (CustomUserDetails) principalUtil.getPrincipal();
		if(customUserDetails == null) {
			throw new ApiException(ErrorCode.UNAUTHORIZED);
		}
		UserEntity userEntity = customUserDetails.getUser();
		
		if(userEntity == null ) {
			throw new ApiException(ErrorCode.BADCREDENTIAL);
		}
		
		MusicEntity musicEntity = MusicEntity.builder()
				.id(wordPostReqDto.getMusicId())
				.build();
	
		WordEntity wordEntity = WordEntity.builder()
				.word(wordPostReqDto.getWord())
				.transWord(wordPostReqDto.getTransWord())
				.description(wordPostReqDto.getDescription())
				.userEntity(userEntity)
				.musicEntity(musicEntity)
				.build();
		
		wordRepository.save(wordEntity);
	}

	
}
