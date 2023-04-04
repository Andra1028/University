package com.example.socialnetworkgui.repository;

import com.example.socialnetworkgui.domain.Friendship;
import com.example.socialnetworkgui.domain.User;
import com.example.socialnetworkgui.domain.validation.Validator;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * FriendshipDbRepository repository class
 */

public class FriendshipDB implements Repository<SortedSet<Long>,Friendship> {
    private final String url;
    private final String username;
    private final String password;
    private final Validator<Friendship> validator;
    private Repository<Long, User> userRepository;

    public FriendshipDB(String url, String username, String password, Validator<Friendship> validator, Repository<Long, User> user) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
        this.userRepository = user;
    }

    private Friendship extractFriendship(ResultSet resultSet) throws SQLException {
        Friendship friend = new Friendship();
        if (resultSet.next()) {
            Long id1 = resultSet.getLong("id_user1");
            Long id2 = resultSet.getLong("id_user2");
            LocalDateTime date = resultSet.getTimestamp("data").toLocalDateTime();
            boolean status = resultSet.getBoolean("status");
            User u1;
            u1 = userRepository.findOne(id1);
            User u2;
            u2 = userRepository.findOne(id2);
            if (u1 != null && u2 != null) {

                friend.setU1(u1);
                friend.setU2(u2);
                friend.setDate(date);
                friend.setPending(status);
                SortedSet<Long> s = new TreeSet<>();
                s.add(u1.getID());
                s.add(u2.getID());
                friend.setID(s);
            }

            return friend;
        }
        return null;
    }

    @Override
    public Friendship findOne(SortedSet<Long> id) {
        if (id == null)
            throw new IllegalArgumentException("Id must not be null");
        Friendship friendship;
        String sql = "SELECT * FROM Friendships WHERE id_user1 = ? AND id_user2 = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, Math.toIntExact(id.first()));
            statement.setInt(2, Math.toIntExact(id.last()));
            ResultSet resultSet = statement.executeQuery();
            friendship = this.extractFriendship(resultSet);
            if (friendship != null)
                return friendship;

            statement.setInt(2, Math.toIntExact(id.first()));
            statement.setInt(1, Math.toIntExact(id.last()));
            resultSet = statement.executeQuery();
            friendship = this.extractFriendship(resultSet);
            if (friendship != null)
                return friendship;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Friendship> findAll() {
        Set<Friendship> friendships = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from Friendships");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id1 = resultSet.getLong("id_user1");
                Long id2 = resultSet.getLong("id_user2");
                LocalDateTime date = resultSet.getTimestamp("data").toLocalDateTime();
                boolean status = resultSet.getBoolean("status");

                Friendship friendship = new Friendship();
                User u1 = userRepository.findOne(id1);
                User u2 = userRepository.findOne(id2);
                SortedSet<Long> s = new TreeSet<>();
                s.add(id1);
                s.add(id2);
                friendship.setID(s);
                friendship.setU1(u1);
                friendship.setU2(u2);
                friendship.setDate(date);
                friendship.setPending(status);
                friendships.add(friendship);
            }
            return friendships;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendships;
    }

    private void executeStatement(Friendship friendship, String sql) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Math.toIntExact(friendship.getID().first()));
            statement.setInt(2, Math.toIntExact(friendship.getID().last()));
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    /*public Friendship save(Friendship friendship) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm");

        if (friendship == null) {
            throw new IllegalArgumentException("Entity must not be null");
        }
        validator.validate(friendship);
        String sql = "INSERT INTO Friendships(id_user1,id_user2,data,status) VALUES (?,?,'" + friendship.getDate().format(formatter) + friendship.getPending()+"')";
        this.executeStatement(friendship, sql);
        return null;
    }*/
    public Friendship save(Friendship friendship)  {
        if (friendship == null) {
            throw new IllegalArgumentException("Friendship must not be null");
        }
        boolean ok = true;
        for (Friendship f : findAll()) {
            if (f.getID().equals(friendship.getID())) {
                ok = false;
                break;
            }
        }
        if (ok) {
            String sql = "insert into friendships(id_user1, id_user2, data,status) values (?, ?, ?, ?)";

            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, Math.toIntExact(friendship.getU1().getID()));
                ps.setInt(2, Math.toIntExact(friendship.getU2().getID()));
                //Date date = Date.from(friendship.getDate().atZone(ZoneId.systemDefault()).toInstant());
                Date sqlDate = Date.valueOf(friendship.getDate().toLocalDate());
                ps.setDate(3, sqlDate);
                ps.setBoolean(4,friendship.getPending());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /*@Override
    public Friendship delete(SortedSet<Long> id) {
        if (id == null || id.first() == null || id.last() == null)
            throw new IllegalArgumentException("Id must not be null");

        Friendship friendship = this.findOne(id);
        if (friendship != null) {
            String sql = "DELETE FROM Friendships WHERE id_user1 = ? and id_user2 = ?";
            this.executeStatement(friendship, sql);
        }
        return friendship;
    }*/
    @Override
    public Friendship delete(SortedSet<Long> id) {
        String sql = "delete from friendships where id_user1 = ? and id_user2 = ?";
        Friendship friendship= findOne(id);
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, Math.toIntExact(friendship.getU1().getID()));
            ps.setInt(2, Math.toIntExact(friendship.getU2().getID()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendship;
    }

    @Override
    public Friendship update(Friendship entity) {
        if(entity == null)
            throw new IllegalArgumentException("Entity must not be null");
        validator.validate(entity);
        String sql = "update friendships set id_user1=?, id_user2=? , data=?, status=? where id_user1 = ? and id_user2 = ?";
        int row_count = 0;

        try(Connection connection = DriverManager.getConnection(url,username,password)) {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1,Math.toIntExact(entity.getU1().getID()));
            ps.setInt(2,Math.toIntExact(entity.getU2().getID()));
            LocalDateTime ldt = LocalDateTime.now();
            java.sql.Date sqlDate = java.sql.Date.valueOf(entity.getDate().toLocalDate());
            ps.setDate(3,sqlDate);
            ps.setBoolean(4,entity.getPending());
            ps.setInt(5,Math.toIntExact(entity.getU1().getID()));
            ps.setInt(6,Math.toIntExact(entity.getU2().getID()));

            row_count = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(row_count > 0)
            return null;
        return entity;
    }

    @Override
    public Friendship findByName(String firstname, String lastname){return null;}

}