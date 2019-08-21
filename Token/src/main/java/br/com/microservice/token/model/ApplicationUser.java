package br.com.microservice.token.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationUser implements AbstractEntity {

    public ApplicationUser(@NotNull ApplicationUser applicationUser) {
        this.username = applicationUser.getUsername();
        this.password = applicationUser.getPassword();
        this.role = applicationUser.getRole();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull(message = "the field username is mandatory")
    @Column(nullable = false)
    private String username;

    @NotNull(message = "the field password is mandatory")
    @Column(nullable = false)
    @ToString.Exclude
    private String password;

    @NotNull(message = "the field role is mandatory")
    @Column(nullable = false)
    @Builder.Default
    private String role = "USER";

}