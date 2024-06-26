package com.miml.music.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.miml.common.enums.Gender;
import com.miml.common.utils.JsonUtils;
import com.miml.music.entity.MusicEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MusicDto {
	
	@Schema(description = "테이블 pk")
	private Long id;
	
	@Schema(description = "아티스트/그룹 성별")
    private Gender gender;

	@Schema(description = "아티스트 명")
    private String artist;

	@Schema(description = "노래 제목")
    private String title;

	@Schema(description = "이미지 URL")
    private String imageUrl;
    
	@Schema(description = "한글 가사")
    private String lyricKr;

	@Schema(description = "영어 가사")
    private String lyricEn;

	@Schema(description = "가사 시작 초(second) | 소숫점 1자리")
    private List<String> startTime;

	@Schema(description = "가사 한국어 발음")
    private String lyricKrPro;
    
	@Schema(description = "유튜브 Id")
	private String youtubeId;

	@Schema(description = "노래시작 시점")
	private String startAt;

	@Builder
	public MusicDto(Long id, Gender gender, String artist, String title, String imageUrl, String lyricKr, String lyricEn,
			List<String> startTime, String lyricKrPro, String youtubeId, String startAt) {
		this.id = id;
		this.gender = gender;
		this.artist = artist;
		this.title = title;
		this.imageUrl = imageUrl;
		this.lyricKr = lyricKr;
		this.lyricEn = lyricEn;
		this.startTime = startTime;
		this.lyricKrPro = lyricKrPro;
		this.youtubeId = youtubeId;
		this.startAt = startAt;
	} 
    
    public String stringArrToString(List<String> strArr) {
    	return strArr.stream()
                .collect(Collectors.joining("\n"));
    			
    }
    
    public MusicEntity toEnity() throws JsonProcessingException {
    	
    	return MusicEntity.builder()
    			 .gender(this.gender)
                 .artist(this.artist)
                 .title(this.title)
                 .imageUrl(this.imageUrl)
                 .lyricKr(this.lyricKr)
                 .lyricEn(this.lyricEn)
                 .startTime(JsonUtils.longListToJson(this.startTime))
                 .lyricKrPro(this.lyricKrPro)
                 .youtubeId(youtubeId)
                 .startAt(startAt)
                 .build();
    }

}