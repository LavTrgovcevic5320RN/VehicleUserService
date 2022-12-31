package com.example.service.impl;

import com.example.dto.*;
import com.example.mapper.UserMapper;
import com.example.repository.ClientRepository;
import com.example.repository.ClientStatusRepository;
import com.example.repository.ManagerRepository;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImplementation implements UserService {
    private UserRepository userRepository;
    private ClientRepository clientRepository;
    private ClientStatusRepository clientStatusRepository;
    private ManagerRepository managerRepository;
    private UserMapper userMapper;
    private JmsTemplate jmsTemplate;
    private String clientRegisterDestination;
    private String findEmailDestination;
    private String resetPasswordDestination;


    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public UserDto addManager(ManagerCreateDto managerCreateDto) {
        return null;
    }

    @Override
    public UserDto addClient(ClientCreateDto clientCreateDto) {
        return null;
    }

    @Override
    public void registerClient(ClientCreateDto clientCreateDto) {

    }

    @Override
    public void registerManager(ManagerCreateDto managerCreateDto) {

    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        return null;
    }

    @Override
    public ClientDto update(Long id, ClientCreateDto clientCreateDto) {
        return null;
    }

    @Override
    public ClientDto updatePassportNumber(Long id, PassportClientDto passportClientDto) {
        return null;
    }

    @Override
    public UserDto updatePassword(Long id, PasswordUserDto passwordClientDto) {
        return null;
    }

    @Override
    public ManagerDto updateCompanyName(Long id, CompanyNameManagerDto companyNameManagerDto) {
        return null;
    }

    @Override
    public UserDto banUser(Long id, BanUserDto banUserDto) {
        return null;
    }

    @Override
    public UserDto unbanUser(Long id, BanUserDto banUserDto) {
        return null;
    }

    @Override
    public Boolean verify(String token) {
        return null;
    }

    @Override
    public ClientStatusDto findDiscount(Long id) {
        return null;
    }

    @Override
    public ClientStatusDto updateDiscount(Long id, DiscountDto discountDto) {
        return null;
    }

    @Override
    public ClientStatusDto updateRankingSystem(Long id, ClientStatusCreateDto clientStatusCreateDto) {
        return null;
    }
}
