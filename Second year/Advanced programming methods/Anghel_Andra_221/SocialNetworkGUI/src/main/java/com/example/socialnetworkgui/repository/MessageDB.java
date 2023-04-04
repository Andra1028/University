package com.example.socialnetworkgui.repository;

import com.example.socialnetworkgui.domain.Message;
import com.example.socialnetworkgui.domain.User;
import com.example.socialnetworkgui.domain.validation.Validator;
import com.example.socialnetworkgui.repository.Repository;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * DataBase user com.example.social_network_gui_v2.repository made for sql use
 * implements the base interface Repository
 * contains objects of type Long and Message
*/
public class MessageDB implements Repository<Long, Message> {

    private final String url;
    private final String username;
    private final String password;
    private final Validator<Message> validator;

    private Repository<Long, User> userRepository;


    /**
     * Public constructor for the UserDataBase Repository
     *
     * @param url       - String
     * @param username  - String
     * @param password  - String
     * @param validator - Validator
     */
    public MessageDB(String url, String username, String password, Validator<Message> validator, Repository<Long, User> userRepository) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
        this.userRepository = userRepository;
    }


    @Override
    public Message findOne(Long id) {

        if (id == null)
            throw new IllegalArgumentException("Id must not be null!");

        Message msg;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from messages where messages.id = ?")) {
            preparedStatement.setInt(1, Math.toIntExact(id));
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                msg = extractMessage(resultSet);
                msg.setUser1Firstname(msg.getFrom().getFirstName());
                msg.setUser1Lastname(msg.getFrom().getLastName());
                msg.setUser2Firstname(msg.getTo().getFirstName());
                msg.setUser2Lastname(msg.getTo().getLastName());
                return msg;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Message> findAll() {

        Set<Message> messages = new HashSet<>();
        Message msg;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from messages ")) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                msg = extractMessage(resultSet);
                msg.setUser1Firstname(msg.getFrom().getFirstName());
                msg.setUser1Lastname(msg.getFrom().getLastName());
                msg.setUser2Firstname(msg.getTo().getFirstName());
                msg.setUser2Lastname(msg.getTo().getLastName());
                messages.add(msg);
            }
            return messages;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return messages;
    }

    /**
     * Function for extract one entity from Message table
     *
     * @param resultSet - attribute that contains information from the table
     * @return - the message from the table
     */
    private Message extractMessage(ResultSet resultSet) throws SQLException {
        Message msg;
        Long idm = resultSet.getLong("id");
        Long fromId = resultSet.getLong("fromm");
        User from = userRepository.findOne(fromId);

        Long toId = resultSet.getLong("tom");
        User to = userRepository.findOne(toId);
        String message = resultSet.getString("chat");


        msg = new Message(from, to, message);
        msg.setMessage(message);
        msg.setID(idm);
        msg.setUser1Firstname(msg.getFrom().getFirstName());
        msg.setUser1Lastname(msg.getFrom().getLastName());
        msg.setUser2Firstname(msg.getTo().getFirstName());
        msg.setUser2Lastname(msg.getTo().getLastName());

        return msg;
    }

    @Override
    public Message save(Message entity) {
        if(entity == null)
            throw new IllegalArgumentException("Entity must not be null");
        validator.validate(entity);
        String sql = "insert into messages (chat, tom , fromm) values (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, entity.getMessage());
            ps.setInt(2, Math.toIntExact(entity.getTo().getID()));
            ps.setInt(3, Math.toIntExact(entity.getFrom().getID()));

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Message delete(Long id) {
        if (id == null)
            throw new IllegalArgumentException("ID must not be null!");
        Message messageRemoved = null;

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement ps = connection.prepareStatement("delete from messages where id = ?");

            messageRemoved = this.findOne(id);
            if (messageRemoved == null)
                return null;

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return messageRemoved;
    }

    @Override
    public Message update(Message entity) {
        return null;
    }

    @Override
    public Message findByName(String firstname, String lastname) {
        return null;
    }
}