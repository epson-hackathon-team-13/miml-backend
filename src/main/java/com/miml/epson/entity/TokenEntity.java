package com.miml.epson.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.miml.user.entity.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "epson_token")
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(length = 256)
    private String username;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private int expiresIn;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("subject_type")
    private String subjectType;

    @JsonProperty("subject_id")
    private String subjectId;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull // FK
    @JoinColumn(name = "user_id") // FK
    @JsonIgnore
    private UserEntity user;

    @Builder
	public TokenEntity(Long id, @NotNull String username, String tokenType, String accessToken, int expiresIn,
			String refreshToken, String subjectType, String subjectId, @NotNull UserEntity user) {
		this.id = id;
		this.username = username;
		this.tokenType = tokenType;
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
		this.refreshToken = refreshToken;
		this.subjectType = subjectType;
		this.subjectId = subjectId;
		this.user = user;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

}
