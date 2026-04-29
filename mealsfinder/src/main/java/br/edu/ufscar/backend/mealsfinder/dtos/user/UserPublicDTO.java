package br.edu.ufscar.backend.mealsfinder.dtos.user;

import br.edu.ufscar.backend.mealsfinder.models.entity.Client;
import br.edu.ufscar.backend.mealsfinder.models.entity.Establishment;
import br.edu.ufscar.backend.mealsfinder.models.entity.User;

import java.time.LocalDateTime;

/**
 * View pública/segura de um usuário. Evita expor a entidade JPA inteira
 * (que carrega coleções lazy, o State pattern do Establishment e a senha).
 */
public class UserPublicDTO {

    private String id;
    private String type;
    private String email;
    private String username;
    private String phoneNumber;
    private String profilePictureUrl;
    private String bio;
    private boolean accountConfirmed;
    private LocalDateTime accountCreationDate;
    private String name;

    public UserPublicDTO() {}

    public static UserPublicDTO fromUser(User user) {
        UserPublicDTO dto = new UserPublicDTO();
        dto.id = user.getId();
        dto.email = user.getEmail();
        dto.username = user.getUsername();
        dto.phoneNumber = user.getPhoneNumber();
        dto.profilePictureUrl = user.getProfilePictureUrl();
        dto.bio = user.getBio();
        dto.accountConfirmed = user.isAccountConfirmed();
        dto.accountCreationDate = user.getAccountCreationDate();

        if (user instanceof Establishment) {
            dto.type = "ESTABLISHMENT";
            dto.name = ((Establishment) user).getName();
        } else if (user instanceof Client) {
            dto.type = "CLIENT";
        } else {
            dto.type = "USER";
        }
        return dto;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getProfilePictureUrl() { return profilePictureUrl; }
    public void setProfilePictureUrl(String profilePictureUrl) { this.profilePictureUrl = profilePictureUrl; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public boolean isAccountConfirmed() { return accountConfirmed; }
    public void setAccountConfirmed(boolean accountConfirmed) { this.accountConfirmed = accountConfirmed; }

    public LocalDateTime getAccountCreationDate() { return accountCreationDate; }
    public void setAccountCreationDate(LocalDateTime accountCreationDate) { this.accountCreationDate = accountCreationDate; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
