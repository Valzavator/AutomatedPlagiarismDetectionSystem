package com.gmail.maxsvynarchuk.persistence.domain.vcs;

import com.gmail.maxsvynarchuk.persistence.domain.User;
import com.gmail.maxsvynarchuk.persistence.domain.type.AuthorizationProvider;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "access_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;

    @SerializedName(value = "access_token")
    @NotBlank
    private String accessTokenString;

    @SerializedName(value = "scope", alternate = {"scopes"})
    @NotBlank
    private String scope;

    @SerializedName(value = "token_type")
    @NotBlank
    private String tokenType;

    @SerializedName(value = "refresh_token")
    private String refreshToken;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthorizationProvider authorizationProvider;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

    public String getAccessToken() {
        return tokenType + " " + accessTokenString;
    }
}
