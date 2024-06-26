package com.miml.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

import com.miml.security.entity.AuthorityEntity;
import com.miml.user.dto.UserDto;

@Entity
@Getter
@Table(name = "user")
public class UserEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long id;

    @NotNull
    @Size(max = 256)
    private String email;

    @NotNull
    @Size(max = 256)
    private String password;

    @NotNull
    @Size(max = 256)
    private String username;

    @NotNull
    @Size(max = 256)
    private String nickname;
    @NotNull
    @Size(max = 256)
    private String language;
    @NotNull
    private Long level;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL , orphanRemoval = true)
    private Set<AuthorityEntity> authorities;

    @NotNull
    private boolean enabled;
    @NotNull
    private boolean accountNonExpired;
    @NotNull
    private boolean accountNonLocked;
    @NotNull
    private boolean credentialsNonExpired;

    @Builder
    public UserEntity(Long id, String email, String password, String username, String nickname, String language, Long level, Set<AuthorityEntity> authorities, boolean enabled, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
        this.language = language;
        this.level = level;
        this.authorities = authorities;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public void setAuthorities(Set<AuthorityEntity> authorities) {
        this.authorities = authorities;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }
    
	public static UserDto toDto(UserEntity userEntity) {
		UserDto userDto = UserDto.builder()
				.email(userEntity.getEmail())
				.username(userEntity.getUsername())
				.nickname(userEntity.getNickname())
				.language(userEntity.getLanguage())
				.level(userEntity.getLevel())
				.build();
		
		return userDto;
	}

}
