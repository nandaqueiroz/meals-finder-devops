package br.edu.ufscar.backend.mealsfinder.dtos.common;

import br.edu.ufscar.backend.mealsfinder.models.entity.Address;

public class AddressDTO {
    private String cep;
    private String city;
    private String state;
    private String neighbourhood;
    private String street;
    private String number;
    private String complement;

    public AddressDTO() {
    }

    public AddressDTO(Address address) {
        if (address == null) {
            return;
        }
        this.cep = address.getCep();
        this.city = address.getCity();
        this.state = address.getState();
        this.neighbourhood = address.getNeighbourhood();
        this.street = address.getStreet();
        this.number = address.getNumber();
        this.complement = address.getComplement();
    }

    public static Address toEntity(AddressDTO dto) {
        if (dto == null) {
            return null;
        }
        Address address = new Address();
        address.setCep(dto.getCep());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setNeighbourhood(dto.getNeighbourhood());
        address.setStreet(dto.getStreet());
        address.setNumber(dto.getNumber());
        address.setComplement(dto.getComplement());
        return address;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }
}
