package br.edu.ufscar.backend.mealsfinder.dtos.establishment;

import br.edu.ufscar.backend.mealsfinder.dtos.common.AddressDTO;
import br.edu.ufscar.backend.mealsfinder.models.entity.Establishment;
import br.edu.ufscar.backend.mealsfinder.models.enums.EnvironmentTag;
import br.edu.ufscar.backend.mealsfinder.models.enums.EstablishmentType;
import br.edu.ufscar.backend.mealsfinder.models.enums.FoodTag;
import br.edu.ufscar.backend.mealsfinder.models.enums.ServiceTag;

import java.util.HashSet;
import java.util.Set;

/** Partial update: only non-null fields are applied. */
public class EstablishmentUpdateDTO {
    private String bio;
    private String name;
    private String profilePictureUrl;
    private String phoneNumber;
    private Boolean delivery;
    private Boolean presencial;
    private Boolean visible;
    private EstablishmentType establishmentType;
    private AddressDTO address;
    private Set<FoodTag> foodTags;
    private Set<ServiceTag> serviceTags;
    private Set<EnvironmentTag> environmentTags;

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getDelivery() {
        return delivery;
    }

    public void setDelivery(Boolean delivery) {
        this.delivery = delivery;
    }

    public Boolean getPresencial() {
        return presencial;
    }

    public void setPresencial(Boolean presencial) {
        this.presencial = presencial;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public EstablishmentType getEstablishmentType() {
        return establishmentType;
    }

    public void setEstablishmentType(EstablishmentType establishmentType) {
        this.establishmentType = establishmentType;
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
        this.foodTags = foodTags;
    }

    public Set<ServiceTag> getServiceTags() {
        return serviceTags;
    }

    public void setServiceTags(Set<ServiceTag> serviceTags) {
        this.serviceTags = serviceTags;
    }

    public Set<EnvironmentTag> getEnvironmentTags() {
        return environmentTags;
    }

    public void setEnvironmentTags(Set<EnvironmentTag> environmentTags) {
        this.environmentTags = environmentTags;
    }

    public void applyTo(Establishment establishment) {
        if (bio != null) {
            establishment.setBio(bio);
        }
        if (name != null) {
            establishment.setName(name);
        }
        if (profilePictureUrl != null) {
            establishment.setProfilePictureUrl(profilePictureUrl);
        }
        if (phoneNumber != null) {
            establishment.setPhoneNumber(phoneNumber);
        }
        if (delivery != null) {
            establishment.setDelivery(delivery);
        }
        if (presencial != null) {
            establishment.setPresencial(presencial);
        }
        if (visible != null) {
            establishment.setVisible(visible);
        }
        if (establishmentType != null) {
            establishment.setEstablishmentType(establishmentType);
        }
        if (address != null) {
            establishment.setAddress(AddressDTO.toEntity(address));
        }
        if (foodTags != null) {
            establishment.setFoodTags(new HashSet<>(foodTags));
        }
        if (serviceTags != null) {
            establishment.setServiceTags(new HashSet<>(serviceTags));
        }
        if (environmentTags != null) {
            establishment.setEnvironmentTags(new HashSet<>(environmentTags));
        }
    }
}
