package br.edu.ufscar.backend.mealsfinder.models;

import br.edu.ufscar.backend.mealsfinder.dtos.authentication.ClientRegisterDTO;
import br.edu.ufscar.backend.mealsfinder.dtos.authentication.EstablishmentRegisterDTO;
import br.edu.ufscar.backend.mealsfinder.models.entity.Client;
import br.edu.ufscar.backend.mealsfinder.models.entity.Establishment;
import br.edu.ufscar.backend.mealsfinder.models.states.Pending;
import org.springframework.stereotype.Component;


@Component
public class UserFactory {
    public Client createClient(ClientRegisterDTO dto) {
        Client client = new Client();

        client.setEmail(dto.getEmail());
        client.setUsername(dto.getUsername());
        client.setPhoneNumber(dto.getPhoneNumber());
        client.setPassword(dto.getPassword());
        client.setProfilePictureUrl(dto.getProfilePicUrl());
        client.setBio(dto.getBio());
        client.setLikedFoodTags(dto.getLikes());
        client.setDislikedFoodTags(dto.getDislikes());
        return client;
    }

    public Establishment createEstablishment(EstablishmentRegisterDTO dto) {
        Establishment establishment = new Establishment();

        establishment.setCnpj(dto.getCnpj());
        establishment.setEmail(dto.getEmail());
        establishment.setUsername(dto.getUsername());
        establishment.setPassword(dto.getPassword());
        establishment.setPhoneNumber(dto.getPhoneNumber());
        establishment.setProfilePictureUrl(dto.getProfilePicUrl());
        establishment.setBio(dto.getBio());
        establishment.setEstablishmentType(dto.getType());
        establishment.setName(dto.getName());
        establishment.setState(Pending.getInstance());

        return establishment;
    }
}

