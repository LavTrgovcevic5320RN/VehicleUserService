package listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MessageHelper {

    private Validator validator;
    private ObjectMapper objectMapper;

    public MessageHelper(Validator validator, ObjectMapper objectMapper) {
        this.validator = validator;
        this.objectMapper = objectMapper;
    }

    public <T> T getMessage(Message message, Class<T> clas){
        try {
            String json =  ((TextMessage)message).getText();
            T data = objectMapper.readValue(json, clas);

            Set<ConstraintViolation<T>> violations = validator.validate(data);

            if(violations.isEmpty()){
                return data;
            }

            printViolationsAndThrowException(violations);
            return null;
        } catch (IOException | JMSException e) {
            throw new RuntimeException("Message parsing fails", e);
        }
    }

    public String createTextMessage(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Problem with creating new message");
        }
    }

    private <T> void printViolationsAndThrowException(Set<ConstraintViolation<T>> violations){
        String concatenatedViolations = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        throw new RuntimeException(concatenatedViolations);
    }
}

