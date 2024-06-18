package com.miml.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.miml.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "authorities")
public class AuthorityEntity extends BaseTimeEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authority_seq")
    private Long id;

    @NotNull
    @Size(max = 20)
    private String authority;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull // FK
    @JoinColumn(name = "user_id") // FK
    @JsonIgnore
    private UserEntity user;

    @Builder
    public AuthorityEntity(Long id, String authority, UserEntity user) {
        this.id = id;
        this.authority = authority;
        this.user = user;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
