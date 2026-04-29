package br.edu.ufscar.backend.mealsfinder.dtos.authentication;

import br.edu.ufscar.backend.mealsfinder.models.entity.Client;
import br.edu.ufscar.backend.mealsfinder.models.entity.Establishment;
import br.edu.ufscar.backend.mealsfinder.models.entity.User;

import java.time.LocalDateTime;

/** Resposta de login: dados públicos do usuário, sem credenciais. */
public class LoginResponseDTO {

    public enum UserKind {
        CLIENT,
        ESTABLISHMENT
    }

    private String id;
    private UserKind userType;
    private String email;
    private String username;
    private String phoneNumber;
    private String profilePictureUrl;
    private String bio;
    private LocalDateTime accountCreationDate;

    public LoginResponseDTO() {
    }

    public static LoginResponseDTO fromUser(User user) {
        if (user == null) {
            return null;
        }
        LoginResponseDTO dto = new LoginResponseDTO();
        dto.setId(user.getId());
        if (user instanceof Client) {
            dto.setUserType(UserKind.CLIENT);
        } else if (user instanceof Establishment) {
            dto.setUserType(UserKind.ESTABLISHMENT);
        }
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setProfilePictureUrl(user.getProfilePictureUrl());
        dto.setBio(user.getBio());
        dto.setAccountCreationDate(user.getAccountCreationDate());
        return dto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserKind getUserType() {
        return userType;
    }

    public void setUserType(UserKind userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public LocalDateTime getAccountCreationDate() {
        return accountCreationDate;
    }

    public void setAccountCreationDate(LocalDateTime accountCreationDate) {
        this.accountCreationDate = accountCreationDate;
    }
}
