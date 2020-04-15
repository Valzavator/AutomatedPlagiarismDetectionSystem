package com.gmail.maxsvynarchuk.persistence.domain;

import com.gmail.maxsvynarchuk.persistence.domain.type.AuthorizationProvider;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken {
    @SerializedName(value = "access_token")
    private String accessTokenString;

    @SerializedName(value = "scope", alternate = {"scopes"})
    private String scope;

    @SerializedName(value = "token_type")
    private String tokenType;

    @SerializedName(value = "refresh_token")
    private String refreshType;

    private AuthorizationProvider authorizationProvider;

    public String getAccessToken() {
        return tokenType + " " + accessTokenString;
    }
}
