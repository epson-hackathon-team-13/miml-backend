package com.miml.word.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.miml.music.entity.MusicEntity;
import com.miml.user.entity.UserEntity;
import com.miml.word.dto.WordDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "word")
public class WordEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "word_seq")
	private Long id;

	private String word;

	private String transWord;

	private String description;
	
	@ManyToOne
	@NotNull // FK
	@JoinColumn(name = "music_id") // FK
	@JsonIgnore
	private MusicEntity musicEntity;

	@ManyToOne
	@NotNull // FK
	@JoinColumn(name = "user_id") // FK
	@JsonIgnore
	private UserEntity userEntity;
	
	
	@Builder
	public WordEntity(Long id, String word, String transWord, String description, @NotNull MusicEntity musicEntity,
			@NotNull UserEntity userEntity) {
		this.id = id;
		this.word = word;
		this.transWord = transWord;
		this.description = description;
		this.musicEntity = musicEntity;
		this.userEntity = userEntity;
	}
	
	

	public void setWord(String word) {
		this.word = word;
	}

	public void setTransWord(String transWord) {
		this.transWord = transWord;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setMusicEntity(MusicEntity musicEntity) {
		this.musicEntity = musicEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	
	public WordDto toDto() {
        return WordDto.builder()
            .word(this.word)
            .transWord(this.transWord)
            .description(this.description)
            .musicId(this.musicEntity != null ? this.musicEntity.getId() : null)
            .userDto(this.userEntity != null ? UserEntity.toDto(this.userEntity) : null)
            .gender(this.musicEntity != null ? this.musicEntity.getGender() : null)
            .artist(this.musicEntity != null ? this.musicEntity.getArtist() : null)
            .title(this.musicEntity != null ? this.musicEntity.getTitle() : null)
            .imageUrl(this.musicEntity != null ? this.musicEntity.getImageUrl() : null)
            .build();
    }
}
