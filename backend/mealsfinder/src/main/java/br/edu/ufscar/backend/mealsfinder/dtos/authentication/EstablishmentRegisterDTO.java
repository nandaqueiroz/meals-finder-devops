package br.edu.ufscar.backend.mealsfinder.dtos.authentication;

import com.fasterxml.jackson.annotation.JsonAlias;
import br.edu.ufscar.backend.mealsfinder.dtos.common.AddressDTO;
import br.edu.ufscar.backend.mealsfinder.models.enums.EnvironmentTag;
import br.edu.ufscar.backend.mealsfinder.models.enums.EstablishmentType;
import br.edu.ufscar.backend.mealsfinder.models.enums.FoodTag;
import br.edu.ufscar.backend.mealsfinder.models.enums.ServiceTag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EstablishmentRegisterDTO {
    private String cnpj;
    private String email;
    private String phoneNumber;
    private String username;
    private String name;
    private String password;
    private String profilePicUrl;
    private String bio;
    private EstablishmentType type;
    private boolean delivery;
    private boolean inPerson;
    private AddressDTO address;
    private Set<FoodTag> foodTags = new HashSet<>();
    private Set<ServiceTag> serviceTags = new HashSet<>();
    private Set<EnvironmentTag> environmentTags = new HashSet<>();
    private List<String> menuUrls = new ArrayList<>();
    private List<String> establishmentPicturesUrls = new ArrayList<>();

    public EstablishmentRegisterDTO() {
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public EstablishmentType getType() {
        return type;
    }

    public void setType(EstablishmentType type) {
        this.type = type;
    }

    public boolean isDelivery() {
        return delivery;
    }

    @JsonAlias("isDelivery")
    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public boolean isInPerson() {
        return inPerson;
    }

    @JsonAlias({"isInPerson", "presencial"})
    public void setInPerson(boolean inPerson) {
        this.inPerson = inPerson;
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

    public List<String> getMenuUrls() {
        return menuUrls;
    }

    public void setMenuUrls(List<String> menuUrls) {
        this.menuUrls = menuUrls != null ? menuUrls : new ArrayList<>();
    }

    public List<String> getEstablishmentPicturesUrls() {
        return establishmentPicturesUrls;
    }

    public void setEstablishmentPicturesUrls(List<String> establishmentPicturesUrls) {
        this.establishmentPicturesUrls = establishmentPicturesUrls != null ? establishmentPicturesUrls : new ArrayList<>();
    }
}
