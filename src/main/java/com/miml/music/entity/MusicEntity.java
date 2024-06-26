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
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "music")
public class MusicEntity {

	@Serial
	private static final long serialVersionUID = 1L;

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
	private String lyricKr;

	@NotNull
	@Column(length = 4096)
	private String lyricEn;

	@NotNull
	@Column(length = 1024)
	private String startTime;

	@Column(length = 4096)
	private String lyricKrPro;

	@Size(max = 50)
	private String imageUrl;

	@Size(max = 50)
	private String youtubeId;

	@Size(max = 50)
	private String startAt;

	@Builder
	public MusicEntity(Long id, @NotNull Gender gender, @NotNull @Size(max = 50) String artist,
			@NotNull @Size(max = 50) String title, @NotNull String lyricKr, @NotNull String lyricEn,
			@NotNull String startTime, String lyricKrPro, @Size(max = 50) String imageUrl,
			@Size(max = 50) String youtubeId, @Size(max = 50) String startAt) {
		this.id = id;
		this.gender = gender;
		this.artist = artist;
		this.title = title;
		this.lyricKr = lyricKr;
		this.lyricEn = lyricEn;
		this.startTime = startTime;
		this.lyricKrPro = lyricKrPro;
		this.imageUrl = imageUrl;
		this.youtubeId = youtubeId;
		this.startAt = startAt;
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

	public void setLyricKr(String lyricKr) {
		this.lyricKr = lyricKr;
	}

	public void setLyricEn(String lyricEn) {
		this.lyricEn = lyricEn;
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

	public void setYoutubeId(String youtubeId) {
		this.youtubeId = youtubeId;
	}

	public void setStartAt(String startAt) {
		this.startAt = startAt;
	}
	
	// string -> List<String> : split("\n")
	public List<String> stringToStringArr(String str) {
		return Arrays.asList(str.split("\n"));
	}
	
	public MusicDto toDto() throws JsonProcessingException {
		return MusicDto.builder()
				.id(this.id)
				.gender(this.gender)
                .artist(this.artist)
                .title(this.title)
                .imageUrl(this.imageUrl)
                .lyricKr(this.lyricKr)
                .lyricEn(this.lyricEn)
                .startTime(JsonUtils.jsonToLongList(this.startTime))
                .lyricKrPro(this.lyricKrPro)
                .youtubeId(youtubeId)
                .startAt(startAt)
				.build();
	}

}
