package listener;

import org.springframework.stereotype.Component;
import service.UserService;

import javax.jms.Message;

@Component
public class RentingListener {

    private MessageHelper messageHelper;
    private UserService userService;

    public RentingListener(MessageHelper messageHelper, UserService userService) {
        this.messageHelper = messageHelper;
        this.userService = userService;
    }

    public void incrementNumberOfRenting(Message message){

    }
}
