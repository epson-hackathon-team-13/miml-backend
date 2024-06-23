package com.miml.music.entity;


import java.io.Serial;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.miml.common.enums.Gender;
import com.miml.common.utils.JsonUtils;
import com.miml.music.dto.MusicDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table(name = "music")
public class MusicEntity {
	
    @Serial
    private static final long serialVersionUID = 1L;

	public MusicEntity() {}
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "music_seq")
    private Long id;
	
    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    @Size(max = 50)
	private String artist;
	
    @NotNull
    @Size(max = 50)
	private String title;
	
    @NotNull
    @Column(length = 4096)
	private String lylicKr;
	
    @NotNull
    @Column(length = 4096)
	private String lylicEn;
	
    @NotNull
    @Column(length = 1024)
	private String startTime;
	
    @Column(length = 4096)
	private String lyricKrPro;
	
    @Size(max = 50)
	private String imageUrl;

    @Builder
	public MusicEntity(Long id, @NotNull Gender gender, @NotNull @Size(max = 50) String artist,
			@NotNull @Size(max = 50) String title, @NotNull String lylicKr, @NotNull String lylicEn,
			@NotNull String startTime, String lyricKrPro, @Size(max = 50) String imageUrl) {
		this.id = id;
		this.gender = gender;
		this.artist = artist;
		this.title = title;
		this.lylicKr = lylicKr;
		this.lylicEn = lylicEn;
		this.startTime = startTime;
		this.lyricKrPro = lyricKrPro;
		this.imageUrl = imageUrl;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setLylickr(String lylicKr) {
		this.lylicKr = lylicKr;
	}

	public void setLylicEn(String lylicEn) {
		this.lylicEn = lylicEn;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setLyricKrPro(String lyricKrPro) {
		this.lyricKrPro = lyricKrPro;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public List<String> stringToStringArr(String str) {
		return Arrays.asList(str.split("\n"));
	}
	
	public MusicDto toDto() throws JsonProcessingException {
		return MusicDto.builder()
				.gender(this.gender)
                .artist(this.artist)
                .title(this.title)
                .imageUrl(this.imageUrl)
                .lylicKr(stringToStringArr(this.lylicKr))
                .lylicEn(stringToStringArr(this.lylicEn))
                .startTime(JsonUtils.jsonToLongList(this.startTime))
                .lyricKrPro(stringToStringArr(this.lyricKrPro))
				.build();
	}

}

