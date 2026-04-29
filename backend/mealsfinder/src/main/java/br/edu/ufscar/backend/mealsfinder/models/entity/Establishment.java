package br.edu.ufscar.backend.mealsfinder.models.entity;

import br.edu.ufscar.backend.mealsfinder.models.enums.AnalysisResult;
import br.edu.ufscar.backend.mealsfinder.models.enums.EnvironmentTag;
import br.edu.ufscar.backend.mealsfinder.models.enums.EstablishmentType;
import br.edu.ufscar.backend.mealsfinder.models.enums.FoodTag;
import br.edu.ufscar.backend.mealsfinder.models.enums.ServiceTag;
import br.edu.ufscar.backend.mealsfinder.models.enums.StatusEnum;
import br.edu.ufscar.backend.mealsfinder.models.states.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "establishments")
@DiscriminatorValue("establishment")
@PrimaryKeyJoinColumn(name = "user_id")
public class Establishment extends User {

    @Column(name = "cnpj", unique = true, nullable = false)
    private String cnpj;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "establishment_type", nullable = false)
    private EstablishmentType establishmentType;

    @Column(name = "is_delivery", nullable = false)
    private boolean delivery;

    @Column(name = "is_presencial", nullable = false)
    private boolean presencial;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusEnum status;

    @Transient
    private EstablishmentState state;

    @Column(name = "rejections", nullable = false)
    private int rejectionsCount;

    @Column(name = "rejection_date")
    private LocalDate rejectionDate;

    @Column(name = "is_visible")
    private boolean isVisible;

    @Embedded
    private Address address;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "establishment_food_tags",
            joinColumns = @JoinColumn(name = "establishment_id", nullable = false)
    )
    @Column(name = "food_tag", nullable = false, length = 64)
    @Enumerated(EnumType.STRING)
    private Set<FoodTag> foodTags = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "establishment_service_tags",
            joinColumns = @JoinColumn(name = "establishment_id", nullable = false)
    )
    @Column(name = "service_tag", nullable = false, length = 64)
    @Enumerated(EnumType.STRING)
    private Set<ServiceTag> serviceTags = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "establishment_environment_tags",
            joinColumns = @JoinColumn(name = "establishment_id", nullable = false)
    )
    @Column(name = "environment_tag", nullable = false, length = 64)
    @Enumerated(EnumType.STRING)
    private Set<EnvironmentTag> environmentTags = new HashSet<>();

    public void handleAnalysis(AnalysisResult result) {
        this.state.handleAnalysis(this, result);
    }

    public Establishment() {
        super();
    }

    public EstablishmentState getState() {
        return state;
    }

    public void setState(EstablishmentState state) {
        this.state = state;

        if (state instanceof Pending) this.status = StatusEnum.PENDING;
        else if (state instanceof Accepted) this.status = StatusEnum.ACCEPTED;
        else if (state instanceof Rejected) this.status = StatusEnum.REJECTED;
        else if (state instanceof Banned) this.status = StatusEnum.BANNED;
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

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EstablishmentType getEstablishmentType() {
        return establishmentType;
    }

    public void setEstablishmentType(EstablishmentType establishmentType) {
        this.establishmentType = establishmentType;
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

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
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

    @Override
    public String toString() {
        return "Establishment{" +
                "id='" + getId() + '\'' +
                ", name='" + name + '\'' +
                ", cnpj='" + cnpj + '\'' +
                '}';
    }

    @PostLoad
    public void initializeState() {
        if (this.status != null) {
            switch (this.status) {
                case PENDING: this.state = Pending.getInstance(); break;
                case ACCEPTED: this.state = Accepted.getInstance(); break;
                case REJECTED: this.state = Rejected.getInstance(); break;
                case BANNED: this.state = Banned.getInstance(); break;
            }
        }
    }
}
