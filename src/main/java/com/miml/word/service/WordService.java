package com.miml.word.service;

import org.springframework.stereotype.Service;

import com.miml.common.utils.PrincipalUtil;
import com.miml.music.entity.MusicEntity;
import com.miml.music.repository.MusicRepository;
import com.miml.security.CustomUserDetails;
import com.miml.user.entity.UserEntity;
import com.miml.word.dto.WordDto;
import com.miml.word.entity.WordEntity;
import com.miml.word.repository.WordRepository;

@Service
public class WordService {
	
	private final WordRepository wordRepository;
	private final MusicRepository musicRepository;
	private final PrincipalUtil principalUtil;
	
	
	public WordService(WordRepository wordRepository, MusicRepository musicRepository, PrincipalUtil principalUtil) {
		this.wordRepository = wordRepository;
		this.musicRepository = musicRepository;
		this.principalUtil = principalUtil;
	}

	public void addWord(WordDto wordDto) {
		CustomUserDetails customUserDetails =  (CustomUserDetails) principalUtil.getPrincipal();
		
		UserEntity userEntity = customUserDetails.getUser();
		
		if(userEntity == null ) {
			throw new IllegalStateException("사용자 정보 없음");
		}
		
		MusicEntity musicEntity = MusicEntity.builder()
				.id(wordDto.getMusicId())
				.build();
	
		WordEntity wordEntity = WordEntity.builder()
				.word(wordDto.getWord())
				.transWord(wordDto.getTransWord())
				.description(wordDto.getDescription())
				.userEntity(userEntity)
				.musicEntity(musicEntity)
				.build();
		
		wordRepository.save(wordEntity);
		
	}
	
}
