package com.example.socialnetworkgui.domain.validation;

import com.example.socialnetworkgui.domain.Message;

public class MessageValidator implements Validator<Message> {

    @Override
    public void validate(Message entity) throws ValidationException {

        if( entity.getTo().getID() == null )
            throw new ValidationException(
                    " You cannot send a message with no destination!");
        if( entity.getTo().equals(entity.getFrom()))
            throw new ValidationException(
                    " You cannot send a message to yourself!");
    }
}