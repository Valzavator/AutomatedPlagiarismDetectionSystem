package com.autoplag.persistence.domain;

import com.autoplag.persistence.domain.type.AuthorizationProvider;
import com.autoplag.persistence.domain.vcs.AccessToken;
import com.autoplag.persistence.exception.oauth.InvalidVcsUrlException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
@NamedEntityGraph(name = "User.detail",
        attributeNodes = @NamedAttributeNode("role"))
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotBlank
    @Size(max = 32)
    private String firstName;

    @NotBlank
    @Size(max = 32)
    private String lastName;

    @NaturalId
    @NotBlank
    @Size(max = 255)
    @Email(regexp = "^(.+@.+\\..+)$")
    @Column(unique = true)
    private String email;

    @NotBlank
    @Size(min = 6, max = 255)
    private String password;

//    @NotNull
//    @Enumerated(EnumType.STRING)
//    private Gender gender;
//
//    @NotNull
//    private Date dateOfBirth;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "user",
            orphanRemoval = true)
//    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<AccessToken> tokens;


    public boolean addAccessToken(AccessToken accessToken) {
        if (tokens.add(accessToken)) {
            accessToken.setUser(this);
            return true;
        }
        return false;
    }

    public boolean removeAccessToken(AccessToken accessToken) {
        if (tokens.remove(accessToken)) {
            accessToken.setUser(null);
            return true;
        }
        return false;
    }

    public AccessToken getAccessToken(String vcsRepositoryUrl) {
        try {
            AuthorizationProvider authorizationProvider =
                    AuthorizationProvider.recognizeFromUrl(vcsRepositoryUrl);
            return getAccessToken(authorizationProvider);
        } catch (InvalidVcsUrlException ex) {
            log.error(ex.toString());
            return null;
        }
    }

    public AccessToken getAccessToken(AuthorizationProvider authorizationProvider) {
        Set<AccessToken> tokens = getTokens();
        for (AccessToken token : tokens) {
            if (authorizationProvider == token.getAuthorizationProvider()) {
                return token;
            }
        }
        return null;
    }

    public boolean isAccessTokenPresented(AuthorizationProvider provider) {
        return getTokens().stream()
                .anyMatch(token -> provider == token.getAuthorizationProvider());
    }

}
