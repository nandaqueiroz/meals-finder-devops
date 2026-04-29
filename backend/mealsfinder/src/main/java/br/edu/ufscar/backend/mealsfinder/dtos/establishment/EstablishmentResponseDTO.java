package br.edu.ufscar.backend.mealsfinder.dtos.establishment;

import br.edu.ufscar.backend.mealsfinder.dtos.common.AddressDTO;
import br.edu.ufscar.backend.mealsfinder.models.entity.Establishment;
import br.edu.ufscar.backend.mealsfinder.models.enums.EnvironmentTag;
import br.edu.ufscar.backend.mealsfinder.models.enums.EstablishmentType;
import br.edu.ufscar.backend.mealsfinder.models.enums.FoodTag;
import br.edu.ufscar.backend.mealsfinder.models.enums.ServiceTag;
import br.edu.ufscar.backend.mealsfinder.models.enums.StatusEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class EstablishmentResponseDTO {
    private String id;
    private String cnpj;
    private EstablishmentType type;
    private String name;
    private String username;
    private String email;
    private String phoneNumber;
    private String bio;
    private StatusEnum status;
    private String profilePictureUrl;
    private LocalDateTime accountCreationDate;
    private boolean visible;
    private boolean delivery;
    private boolean presencial;
    private int rejectionsCount;
    private LocalDate rejectionDate;
    private AddressDTO address;
    private Set<FoodTag> foodTags = new HashSet<>();
    private Set<ServiceTag> serviceTags = new HashSet<>();
    private Set<EnvironmentTag> environmentTags = new HashSet<>();

    public EstablishmentResponseDTO() {
    }

    public EstablishmentResponseDTO(Establishment establishment) {
        this.id = establishment.getId();
        this.cnpj = establishment.getCnpj();
        this.type = establishment.getEstablishmentType();
        this.name = establishment.getName();
        this.username = establishment.getUsername();
        this.email = establishment.getEmail();
        this.phoneNumber = establishment.getPhoneNumber();
        this.bio = establishment.getBio();
        this.status = establishment.getStatus();
        this.profilePictureUrl = establishment.getProfilePictureUrl();
        this.accountCreationDate = establishment.getAccountCreationDate();
        this.visible = establishment.isVisible();
        this.delivery = establishment.isDelivery();
        this.presencial = establishment.isPresencial();
        this.rejectionsCount = establishment.getRejectionsCount();
        this.rejectionDate = establishment.getRejectionDate();
        if (establishment.getAddress() != null) {
            this.address = new AddressDTO(establishment.getAddress());
        }
        if (establishment.getFoodTags() != null) {
            this.foodTags = new HashSet<>(establishment.getFoodTags());
        }
        if (establishment.getServiceTags() != null) {
            this.serviceTags = new HashSet<>(establishment.getServiceTags());
        }
        if (establishment.getEnvironmentTags() != null) {
            this.environmentTags = new HashSet<>(establishment.getEnvironmentTags());
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public EstablishmentType getType() {
        return type;
    }

    public void setType(EstablishmentType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public LocalDateTime getAccountCreationDate() {
        return accountCreationDate;
    }

    public void setAccountCreationDate(LocalDateTime accountCreationDate) {
        this.accountCreationDate = accountCreationDate;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public boolean isPresencial() {
        return presencial;
    }

    public void setPresencial(boolean presencial) {
        this.presencial = presencial;
    }

    public int getRejectionsCount() {
        return rejectionsCount;
    }

    public void setRejectionsCount(int rejectionsCount) {
        this.rejectionsCount = rejectionsCount;
    }

    public LocalDate getRejectionDate() {
        return rejectionDate;
    }

    public void setRejectionDate(LocalDate rejectionDate) {
        this.rejectionDate = rejectionDate;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public Set<FoodTag> getFoodTags() {
        return foodTags;
    }

    public void setFoodTags(Set<FoodTag> foodTags) {
        this.foodTags = foodTags != null ? foodTags : new HashSet<>();
    }

    public Set<ServiceTag> getServiceTags() {
        return serviceTags;
    }

    public void setServiceTags(Set<ServiceTag> serviceTags) {
        this.serviceTags = serviceTags != null ? serviceTags : new HashSet<>();
    }

    public Set<EnvironmentTag> getEnvironmentTags() {
        return environmentTags;
    }

    public void setEnvironmentTags(Set<EnvironmentTag> environmentTags) {
        this.environmentTags = environmentTags != null ? environmentTags : new HashSet<>();
    }
}
