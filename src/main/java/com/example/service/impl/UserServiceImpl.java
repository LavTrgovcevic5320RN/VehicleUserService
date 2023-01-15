package com.example.service.impl;

import com.example.domain.Client;
import com.example.domain.ClientStatus;
import com.example.domain.Manager;
import com.example.domain.User;
import com.example.dto.*;
import com.example.exception.NotFoundException;
import com.example.listener.MessageHelper;
import com.example.mapper.UserMapper;
import com.example.repository.ClientRepository;
import com.example.repository.ClientStatusRepository;
import com.example.repository.ManagerRepository;
import com.example.repository.UserRepository;
import com.example.security.service.TokenService;
import com.example.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private TokenService tokenService;
    private ClientRepository clientRepository;
    private ClientStatusRepository clientStatusRepository;
    private ManagerRepository managerRepository;
    private JmsTemplate jmsTemplate;
    private MessageHelper messageHelper;
    private String clientRegisterDestination;
    private String findEmailDestination;
    private String resetPasswordDestination;


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, TokenService tokenService,
                           ClientRepository clientRepository, ManagerRepository managerRepository,
                           ClientStatusRepository clientStatusRepository,
                           JmsTemplate jmsTemplate,@Value("${destination.registerClient}") String clientRegisterDestination,
                           @Value("${destination.findEmail}") String findEmailDestination, MessageHelper messageHelper,
                           @Value("${destination.resetPassword}") String resetPasswordDestination) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
        this.clientRepository = clientRepository;
        this.managerRepository = managerRepository;
        this.jmsTemplate = jmsTemplate;
        this.clientRegisterDestination = clientRegisterDestination;
        this.clientStatusRepository = clientStatusRepository;
        this.findEmailDestination = findEmailDestination;
        this.messageHelper = messageHelper;
        this.resetPasswordDestination = resetPasswordDestination;
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::userToUserDto);
    }

    @Override
    public UserDto addManager(ManagerCreateDto managerCreateDto) {
        Manager manager = userMapper.managerCreateDtoToManager(managerCreateDto);
        userRepository.save(manager);
        return userMapper.userToUserDto(manager);
    }

    @Override
    public UserDto addClient(ClientCreateDto clientCreateDto) {
        Client client = userMapper.clientCreateDtoToClient(clientCreateDto);
        userRepository.save(client);
        return userMapper.userToUserDto(client);
    }

    @Override
    public void registerClient(ClientCreateDto clientCreateDto) {
        Client client = userMapper.clientCreateDtoToClient(clientCreateDto);
        ClientDto clientDto = userMapper.clientToClientDto(client);
        jmsTemplate.convertAndSend(clientRegisterDestination, messageHelper.createTextMessage(clientDto));
    }

    @Override
    public void registerManager(ManagerCreateDto managerCreateDto) {
        Manager manager = userMapper.managerCreateDtoToManager(managerCreateDto);
        ManagerDto managerDto = userMapper.managerToManagerDto(manager);
        jmsTemplate.convertAndSend(clientRegisterDestination, messageHelper.createTextMessage(managerDto));
    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        //Try to find active user for specified credentials
        User user = userRepository
                .findUserByEmailAndPassword(tokenRequestDto.getEmail(), tokenRequestDto.getPassword())
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with email: %s and password: %s not found.", tokenRequestDto.getEmail(),
                                tokenRequestDto.getPassword())));
        //Create token payload
        if (user.getBanned() == false && user.getEnabled()) {
            Claims claims = Jwts.claims();
            claims.put("id", user.getId());
            claims.put("role", user.getRole().getName());

            // Generate token
            return new TokenResponseDto(tokenService.generate(claims));
        } else return new TokenResponseDto();

    }

    @Override
    public ClientDto update(Long id, ClientCreateDto clientCreateDto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Client with id: %d not found.", id)));
        //Set values to product
        client.setUsername(clientCreateDto.getUsername());
        client.setEmail(clientCreateDto.getEmail());
        client.setFirstName(clientCreateDto.getFirstName());
        client.setLastName(clientCreateDto.getLastName());
        client.setPassword(clientCreateDto.getPassword());
        client.setPassportNumber(clientCreateDto.getPassportNumber());
        client.setContact(clientCreateDto.getContact());
        //Map product to DTO and return it
        return userMapper.clientToClientDto(userRepository.save(client));
    }

    public ClientDto updatePassportNumber(Long id, PassportClientDto passportClientDto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Client with id: %d not found.", id)));
        client.setPassportNumber(passportClientDto.getPassportNumber());

        return userMapper.clientToClientDto(userRepository.save(client));
    }

    @Override
    public UserDto updatePassword(Long id, PasswordUserDto passwordUserDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Client with id: %d not found.", id)));
        user.setPassword(passwordUserDto.getPassword());

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        jmsTemplate.convertAndSend(resetPasswordDestination, messageHelper.createTextMessage(userDto));

        return userMapper.userToUserDto(userRepository.save(user));

    }

    @Override
    public ManagerDto updateCompanyName(Long id, CompanyNameManagerDto hotelNameManagerDto) {
        Manager manager = managerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Client with id: %d not found.", id)));

        manager.setCompanyName(hotelNameManagerDto.getCompanyName());
        return userMapper.managerToManagerDto(userRepository.save(manager));
    }


    @Override
    public UserDto banUser(Long id, BanUserDto banUserDto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id: %d not found.", id)));
        //Set values to product

        user.setBanned(true);

        //Map product to DTO and return it
        return userMapper.userToUserDto(userRepository.save(user));

    }

    @Override
    public UserDto unbanUser(Long id, BanUserDto banUserDto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id: %d not found.", id)));
        //Set values to product

        user.setBanned(false);

        //Map product to DTO and return it
        return userMapper.userToUserDto(userRepository.save(user));
    }

    @Override
    public Boolean verify(String token) {
        User user = userRepository.findUserByVerificationCode(token)
                .orElseThrow(() -> new NotFoundException("not found"));
        if (user == null) return false;
        user.setEnabled(true);
        user.setVerificationCode(null);
        userRepository.save(user);
        return true;
    }


    @Override
    public ClientStatusDto findDiscount(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with id: %d not found.", id)));
        List<ClientStatus> clientStatusList = clientStatusRepository.findAll();
        //get discount
        ClientStatus status = clientStatusList.stream()
                .filter(clientStatus -> clientStatus.getMaxNumberOfReservations() >= client.getNumberOfReservations()
                        && clientStatus.getMinNumberOfReservations() <= client.getNumberOfReservations())
                .findAny()
                .get();
        return new ClientStatusDto(status.getDiscount(), status.getRank());

        //return new ClientStatusDto(0, "regular"); // ovo stoji ako hocemo retry da probamo
    }

    @Override
    public ClientStatusDto updateRankingSystem(Long id, ClientStatusCreateDto clientStatusCreateDto) {
        ClientStatus clientStatus = clientStatusRepository.getById(id);
        clientStatus.setRank(clientStatusCreateDto.getRank());
        clientStatus.setDiscount(clientStatusCreateDto.getDiscount());
        clientStatus.setMinNumberOfReservations(clientStatusCreateDto.getMinNumberOfReservations());
        clientStatus.setMaxNumberOfReservations(clientStatusCreateDto.getMaxNumberOfReservations());
        return userMapper.clientStatusToClientStatusDto(clientStatusRepository.save(clientStatus));
    }

    @Override
    public void changeNumberOfReservations(ClientQueueDto clientQueueDto) {
        Client client = clientRepository.findById(clientQueueDto.getUserId())
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with that id is not found")));

        if (clientQueueDto.getIncrement()) client.setNumberOfReservations(client.getNumberOfReservations() + 1);
        else client.setNumberOfReservations(client.getNumberOfReservations() - 1);
        clientRepository.save(client);

        String hotelName = clientQueueDto.getHotelName();
        String city = clientQueueDto.getCity();
        Manager manager = managerRepository.findManagerByCompanyNameAndCity(hotelName, city);

        ClientQueueDto newClientQueueDto = userMapper.clientToClientQueueDto(client);
        newClientQueueDto.setIncrement(clientQueueDto.getIncrement());
        newClientQueueDto.setBookingId(clientQueueDto.getBookingId());
        newClientQueueDto.setHotelName(clientQueueDto.getHotelName());
        newClientQueueDto.setCity(clientQueueDto.getCity());
        newClientQueueDto.setManagerEmail(manager.getEmail());
        newClientQueueDto.setManagerId(manager.getId());
        // messageQueueDto
        jmsTemplate.convertAndSend(findEmailDestination, messageHelper.createTextMessage(newClientQueueDto));
    }


    @Override
    public ClientStatusDto updateDiscount(Long id, DiscountDto discountDto) {
        ClientStatus clientStatus = clientStatusRepository.getById(id);
        clientStatus.setRank(discountDto.getRank());
        clientStatus.setDiscount(discountDto.getDiscount());
        return userMapper.clientStatusToClientStatusDto(clientStatusRepository.save(clientStatus));
    }

}
