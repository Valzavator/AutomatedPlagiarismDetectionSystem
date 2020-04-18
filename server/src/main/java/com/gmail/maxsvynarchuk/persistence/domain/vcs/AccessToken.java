package com.gmail.maxsvynarchuk.persistence.domain.vcs;

import com.gmail.maxsvynarchuk.persistence.domain.User;
import com.gmail.maxsvynarchuk.persistence.domain.type.AuthorizationProvider;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken {
    private Long id;

    @SerializedName(value = "access_token")
    private String accessTokenString;

    @SerializedName(value = "scope", alternate = {"scopes"})
    private String scope;

    @SerializedName(value = "token_type")
    private String tokenType;

    @SerializedName(value = "refresh_token")
    private String refreshToken;

    private AuthorizationProvider authorizationProvider;

    private User user;

    public String getAccessToken() {
        return tokenType + " " + accessTokenString;
    }
}
