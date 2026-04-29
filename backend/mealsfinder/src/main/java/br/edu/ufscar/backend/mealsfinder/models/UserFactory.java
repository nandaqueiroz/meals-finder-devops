package br.edu.ufscar.backend.mealsfinder.models;

import br.edu.ufscar.backend.mealsfinder.dtos.authentication.ClientRegisterDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.authentication.EstablishmentRegisterDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.common.AddressDTO;
import br.edu.ufscar.backend.mealsfinder.models.entity.Client;
import br.edu.ufscar.backend.mealsfinder.models.entity.Establishment;
import br.edu.ufscar.backend.mealsfinder.models.states.Pending;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.UUID;

@Component
public class UserFactory {
    public Client createClient(ClientRegisterDTO dto) {
        Client client = new Client();

        client.setId(UUID.randomUUID().toString());
        client.setEmail(dto.getEmail());
        client.setUsername(dto.getUsername());
        client.setPhoneNumber(dto.getPhoneNumber());
        client.setPassword(dto.getPassword());
        client.setProfilePictureUrl(dto.getProfilePicUrl());
        client.setBio(dto.getBio());
        client.setLikedFoodTags(new HashSet<>(dto.getLikedFoodTags()));
        client.setDislikedFoodTags(new HashSet<>(dto.getDislikedFoodTags()));
        return client;
    }

    public Establishment createEstablishment(EstablishmentRegisterDTO dto) {
        Establishment establishment = new Establishment();

        establishment.setId(UUID.randomUUID().toString());
        establishment.setCnpj(dto.getCnpj());
        establishment.setEmail(dto.getEmail());
        establishment.setUsername(dto.getUsername());
        establishment.setPassword(dto.getPassword());
        establishment.setPhoneNumber(dto.getPhoneNumber());
        establishment.setProfilePictureUrl(dto.getProfilePicUrl());
        establishment.setBio(dto.getBio());
        establishment.setEstablishmentType(dto.getType());
        establishment.setName(dto.getName());
        establishment.setDelivery(dto.isDelivery());
        establishment.setPresencial(dto.isInPerson());
        establishment.setAddress(AddressDTO.toEntity(dto.getAddress()));
        establishment.setFoodTags(new HashSet<>(dto.getFoodTags()));
        establishment.setServiceTags(new HashSet<>(dto.getServiceTags()));
        establishment.setEnvironmentTags(new HashSet<>(dto.getEnvironmentTags()));
        establishment.setState(Pending.getInstance());

        return establishment;
    }
}
