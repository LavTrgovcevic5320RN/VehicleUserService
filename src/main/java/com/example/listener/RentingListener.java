package com.example.listener;

import com.example.dto.ClientQueueDto;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import com.example.service.UserService;
import javax.jms.Message;

@Component
public class RentingListener {

    private MessageHelper messageHelper;
    private UserService userService;

    public RentingListener(MessageHelper messageHelper, UserService userService) {
        this.messageHelper = messageHelper;
        this.userService = userService;
    }

//    @JmsListener(destination = "${destination.rentingNumber}", concurrency = "5-10")
    public void incrementNumberOfRenting(Message message){
        ClientQueueDto clientQueueDto = messageHelper.getMessage(message, ClientQueueDto.class);
        System.out.println(clientQueueDto.toString());
        userService.changeNumberOfReservations(clientQueueDto);
    }
}
