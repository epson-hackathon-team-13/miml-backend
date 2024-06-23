package com.miml.music.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.miml.common.enums.Gender;
import com.miml.common.utils.JsonUtils;
import com.miml.music.entity.MusicEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicDto {
	
	public MusicDto() {}

    private Gender gender;

    private String artist;

    private String title;

    private String imageUrl;
    
    private String lyricKr;

    private String lyricEn;

    private List<String> startTime;

    private String lyricKrPro;

    @Builder
	public MusicDto(Gender gender, String artist, String title, String imageUrl, String lyricKr,
			String lyricEn, List<String> startTime, String lyricKrPro) {
		this.gender = gender;
		this.artist = artist;
		this.title = title;
		this.imageUrl = imageUrl;
		this.lyricKr = lyricKr;
		this.lyricEn = lyricEn;
		this.startTime = startTime;
		this.lyricKrPro = lyricKrPro;
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
                 .build();
    } 

}