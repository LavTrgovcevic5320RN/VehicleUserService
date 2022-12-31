package com.example.service;

import com.example.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserDto> findAll(Pageable pageable);
    UserDto addManager(ManagerCreateDto managerCreateDto);
    UserDto addClient(ClientCreateDto clientCreateDto);
    void registerClient(ClientCreateDto clientCreateDto);
    void registerManager(ManagerCreateDto managerCreateDto);
    TokenResponseDto login(TokenRequestDto tokenRequestDto);
    ClientDto update(Long id, ClientCreateDto clientCreateDto);
    ClientDto updatePassportNumber(Long id, PassportClientDto passportClientDto);
    UserDto updatePassword(Long id, PasswordUserDto passwordClientDto);
    ManagerDto updateCompanyName(Long id, CompanyNameManagerDto companyNameManagerDto);
    UserDto banUser(Long id, BanUserDto banUserDto);
    UserDto unbanUser(Long id, BanUserDto banUserDto);
    Boolean verify(String token);
    ClientStatusDto findDiscount(Long id);
    ClientStatusDto updateDiscount(Long id, DiscountDto discountDto);
    ClientStatusDto updateRankingSystem(Long id, ClientStatusCreateDto clientStatusCreateDto);
//    void changeNumberOfReservations();

}
