package com.example.socialnetworkgui.repository;

import com.example.socialnetworkgui.domain.User;
import com.example.socialnetworkgui.domain.validation.Validator;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDB implements Repository<Long, User> {
    private String url;
    private String username;

    private String password;
    private Validator<User> validator;

    public UserDB(String url, String username,String password, Validator<User> validator) {
        this.url = url;
        this.username = username;
        this.password=password;
        this.validator = validator;
    }
    @Override
    public User findOne(Long id) {
        if(id == null)
            throw new IllegalArgumentException("Id must not be null");

        String sql = "SELECT * FROM Users where users.id = ?";
        User user;

        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, Math.toIntExact(id));
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String email = resultSet.getString("email");
                String parola= resultSet.getString("password");

                user = new User(firstName,lastName,null, email, parola,null);
                user.setID(id);
                return user;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public User findByName(String firstname, String lastname) {
        if(firstname == null || lastname == null)
            throw new IllegalArgumentException("Name must not be null");

        String sql = "SELECT * FROM Users where users.firstname = ? and users.lastname=?";
        User user;

        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, firstname);
            statement.setString(2, lastname);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                int idi = resultSet.getInt("id");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String email = resultSet.getString("email");
                String parola= resultSet.getString("password");
                long id = Long.valueOf(idi);
                user = new User(firstName,lastName,null, email, parola,null);
                user.setID(id);
                return user;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<User> findAll() {
        Set<User> users = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from users");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String email = resultSet.getString("email");
                String parola = resultSet.getString("password");

                User utilizator = new User(firstName, lastName,null, email, parola,null);
                utilizator.setID(id);
                users.add(utilizator);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User save(User entity) {
        if(entity == null)
            throw new IllegalArgumentException("Entity must not be null");
        validator.validate(entity);
        String sql = "insert into users (firstname, lastname , email, password) values (?, ?, ?,?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getEmail());
            ps.setString(4, entity.getPassword());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User delete(Long id) {
        User user = null;
        String sql = "delete from users where users.id = ?";

        try(Connection connection = DriverManager.getConnection(url,username,password)) {
            PreparedStatement ps = connection.prepareStatement(sql);
            user = this.findOne(id);
            if(user == null)
                return null;

            ps.setLong(1,id);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    @Override
    public User update(User entity) {
        if(entity == null)
            throw new IllegalArgumentException("Entity must not be null");
        validator.validate(entity);
        String sql = "update users set firstname = ?, lastname = ?, email=?,password=? where users.id = ?";
        int row_count = 0;

        try(Connection connection = DriverManager.getConnection(url,username,password)) {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1,entity.getFirstName());
            ps.setString(2,entity.getLastName());
            ps.setLong(3,entity.getID());
            ps.setString(4,entity.getEmail());
            ps.setString(5,entity.getPassword());

            row_count = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(row_count > 0)
            return null;
        return entity;
    }


}
