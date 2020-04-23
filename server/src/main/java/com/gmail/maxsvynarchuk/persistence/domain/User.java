package com.gmail.maxsvynarchuk.persistence.domain;

import com.gmail.maxsvynarchuk.persistence.domain.type.AuthorizationProvider;
import com.gmail.maxsvynarchuk.persistence.domain.type.Gender;
import com.gmail.maxsvynarchuk.persistence.domain.vcs.AccessToken;
import com.gmail.maxsvynarchuk.persistence.exception.oauth.InvalidVcsUrlException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
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

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    private Date dateOfBirth;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "user_role",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private Set<Role> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "user")
    private Set<AccessToken> tokens;

    public AccessToken getAccessTokenForVcs(String vcsRepositoryUrl) {
        Set<AccessToken> tokens = getTokens();
        AuthorizationProvider authorizationProvider;

        try {
            authorizationProvider = AuthorizationProvider.recognizeFromUrl(vcsRepositoryUrl);
        } catch (InvalidVcsUrlException ex) {
            log.error(ex.toString());
            return null;
        }

        for (AccessToken token : tokens) {
            if (authorizationProvider == token.getAuthorizationProvider()) {
                return token;
            }
        }

       return null;
    }
}
