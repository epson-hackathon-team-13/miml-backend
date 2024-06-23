package com.miml.music.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.miml.music.dto.MusicDto;
import com.miml.music.entity.MusicEntity;
import com.miml.music.repository.MusicRepository;

@Service
public class MusicService {

	private final MusicRepository musicRepository;
	
	public MusicService(MusicRepository musicRepository) {
		this.musicRepository = musicRepository;
	}



	public MusicDto getMusicById(Long id) throws JsonProcessingException {
        Optional<MusicEntity> optionalMusicEntity = musicRepository.findById(id);
        if (optionalMusicEntity.isEmpty()) {
            throw new IllegalArgumentException("Music not found with id: " + id);
        }

        MusicEntity musicEntity = optionalMusicEntity.get();
        return musicEntity.toDto();
    }
	
}
