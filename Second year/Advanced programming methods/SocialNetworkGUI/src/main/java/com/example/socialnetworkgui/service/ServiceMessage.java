package com.example.socialnetworkgui.service;

import com.example.socialnetworkgui.domain.Entity;
import com.example.socialnetworkgui.domain.Message;
import com.example.socialnetworkgui.domain.User;
import com.example.socialnetworkgui.domain.validation.ValidationException;
import com.example.socialnetworkgui.repository.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service of Message
 * repoMessage - Message Repository
 * repoUser - User Repository
 */
public class ServiceMessage {

    private final Repository<Long, Message> repoMessage;
    private final Repository<Long, User> repoUser;

    /**
     * Constructor for Message Service
     * @param repoMessage - com.example.social_network_gui_v2.repository for messages
     * @param repoUser - com.example.social_network_gui_v2.repository for users
     */
    public ServiceMessage( Repository<Long, User> repoUser, Repository<Long, Message> repoMessage ) {
        this.repoMessage = repoMessage;
        this.repoUser = repoUser;
    }

    /**
     * Save one message
     * @param fromId - id of the user that sends the message
     * @param toId - list of user's ids
     * @param message -string
     */
    public void save( Long fromId, Long toId, String message ){

        User from = repoUser.findOne(fromId);
        User to = repoUser.findOne(toId);

        Message msg = new Message( from, to, message) ;
        msg.setUser1Firstname(msg.getFrom().getFirstName());
        msg.setUser1Lastname(msg.getFrom().getLastName());
        msg.setUser2Firstname(msg.getTo().getFirstName());
        msg.setUser2Lastname(msg.getTo().getLastName());

        long id = get_size()+1;
        msg.setID(id);

        Message save = repoMessage.save(msg);
        if(save != null)
            throw new ValidationException("Id already used!");
    }


    /**
     * Find one message for a given id
     * @param id - id of the message
     * @return - the message if exists
     * otherwise, throw exception
     */
    public Message findOne( Long id ){

        Message findMsg = repoMessage.findOne(id);
        if(findMsg != null)
            return findMsg;
        else
            throw new ValidationException("Message id invalid!");
    }



//    /**
//     * Function that make a list of messages for two users
//     * @param id1 - id of first user
//     * @param id2 - id fo second user
//     * @return - returns the list of messages from those 2 users
//     */
//    public List<Message> PrivateChat( Long id1, Long id2 ){
//
//        List<Message> conversation = new ArrayList<>();
//        User user1 = repoUser.findOne(id1);
//        User user2 = repoUser.findOne(id2);
//        List<Message> messages = new ArrayList<>();
//        repoMessage.findAll().forEach(messages :: add);
//        List<Message> sortedMessages = messages
//                .stream()
//                .sorted(Comparator.comparing(Entity::getId))
//                .collect(Collectors.toList());
//
//        for(Message msg : sortedMessages){
//            if((Objects.equals(id1,msg.getFrom().getId()) && msg.getTo().contains(user2) && msg.getTo().size() == 1)
//                    || (Objects.equals(id2,msg.getFrom().getId()) && msg.getTo().contains(user1)) && msg.getTo().size() == 1)
//                conversation.add(msg);
//        }
//        return conversation;
//    }

    /**
     * Getter function for biggest id
     * @return the biggest id+1
     */
    private long get_size() {
        long id = 0L;
        for (Message message1 : repoMessage.findAll()) {
            if (message1.getID() > id)
                id = message1.getID();
        }
        id++;
        return id;
    }
}