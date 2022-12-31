package com.example.mapper;

import com.example.domain.Client;
import com.example.domain.ClientStatus;
import com.example.domain.Manager;
import com.example.domain.User;
import com.example.dto.*;
import com.example.repository.RoleRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private RoleRepository roleRepository;

    public UserMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public UserDto userToUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUsername(user.getUsername());
        //userDto.setBithday(user.getBirthday());

        return userDto;
    }

    public ClientDto clientToClientDto(Client client){
        ClientDto clientDto = new ClientDto();
        clientDto.setContact(client.getContact());
        clientDto.setEmail(client.getEmail());
        clientDto.setFirstName(client.getFirstName());
        clientDto.setUsername(client.getUsername());
        clientDto.setLastName(client.getLastName());
        clientDto.setPassportNumber(client.getPassportNumber());
        clientDto.setNumberOfReservations(client.getNumberOfReservations());
        clientDto.setEnabled(client.getEnabled());
        clientDto.setVerificationCode(client.getVerificationCode());
        clientDto.setSendTo("client");
        return clientDto;
    }

    public Client clientCreateDtoToClient(ClientCreateDto clientCreateDto){
        Client client = new Client();
        client.setEmail(clientCreateDto.getEmail());
        client.setFirstName(clientCreateDto.getFirstName());
        client.setLastName(clientCreateDto.getLastName());
        client.setUsername(clientCreateDto.getUsername());
        client.setPassword(clientCreateDto.getPassword());
        client.setContact(clientCreateDto.getContact());
        client.setPassportNumber(clientCreateDto.getPassportNumber());
        client.setRole(roleRepository.findRoleByName("ROLE_CLIENT").get());
        client.setNumberOfReservations(8);
        client.setBanned(false);
        String randomCode = RandomString.make(64);
        client.setVerificationCode(randomCode);
        client.setEnabled(true);
        //client.setBirthday(clientCreateDto.getBirthday());
        return client;
    }

    public ManagerDto managerToManagerDto(Manager manager){
        ManagerDto managerDto = new ManagerDto();
        managerDto.setContact(manager.getContact());
        managerDto.setEmail(manager.getEmail());
        managerDto.setFirstName(manager.getFirstName());
        managerDto.setUsername(manager.getUsername());
        managerDto.setLastName(manager.getLastName());
        managerDto.setCompanyName(manager.getCompanyName());
        managerDto.setVerificationCode(manager.getVerificationCode());
        managerDto.setSendTo("manager");
        return managerDto;
    }

    public Manager managerCreateDtoToManager(ManagerCreateDto managerCreateDto){
        Manager manager = new Manager();
        manager.setEmail(managerCreateDto.getEmail());
        manager.setFirstName(managerCreateDto.getFirstName());
        manager.setLastName(managerCreateDto.getLastName());
        manager.setUsername(managerCreateDto.getUsername());
        manager.setPassword(managerCreateDto.getPassword());
        // manager.setBirthday(managerCreateDto.getBirthday());
        manager.setContact(managerCreateDto.getContact());
        manager.setCompanyName(managerCreateDto.getCompanyName());
        manager.setCity(managerCreateDto.getCity());
        // manager.setHiringDate(managerCreateDto.getHiringDate());
        manager.setRole(roleRepository.findRoleByName("ROLE_MANAGER").get());
        manager.setBanned(false);
        String randomCode = RandomString.make(64);
        manager.setVerificationCode(randomCode);
        manager.setEnabled(true);
        return manager;
    }

    public ClientStatusDto clientStatusToClientStatusDto(ClientStatus clientStatus) {
        ClientStatusDto clientStatusDto = new ClientStatusDto();
        clientStatusDto.setRank(clientStatus.getRank());
        clientStatusDto.setDiscount(clientStatus.getDiscount());
        return clientStatusDto;
    }


}
