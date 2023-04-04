package com.example.java_mpp_bun.repository;


import com.example.java_mpp_bun.domain.Zbor;
import com.example.java_mpp_bun.utils.JdbcUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ZborRepository implements ZborRepo {
    private final JdbcUtils dbutils;
    private static final Logger logger = LogManager.getLogger();

    public ZborRepository(Properties prop) {
       logger.info("bvbdfv", prop);
       dbutils= new JdbcUtils(prop);
    }
    @Override
    public Zbor findOne(Integer id) {
        if(id == null)
            throw new IllegalArgumentException("Id must not be null");

        String sql = "SELECT * FROM zbor where zbor_id = ?";
        Zbor zbor;

        try(Connection connection = dbutils.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, Math.toIntExact(id));
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                String destinatie = resultSet.getString("destinatie");
                LocalDateTime data_ora= resultSet.getTimestamp("data_ora").toLocalDateTime();
                String aeroport = resultSet.getString("aeroport");
                Integer locuri = resultSet.getInt("locuri");

                zbor = new Zbor(destinatie,data_ora,aeroport,locuri);
                zbor.setID(id);
                return zbor;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Zbor findByName(String userName) {
        return null;
    }

    @Override
    public List<Zbor> findAll() {
        List<Zbor> zboruri = new ArrayList<>();
        try (Connection connection = dbutils.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * from zbor");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("zbor_id");
                String destinatie = resultSet.getString("destinatie");
                LocalDateTime data_ora= resultSet.getTimestamp("data_ora").toLocalDateTime();
                String aeroport = resultSet.getString("aeroport");
                Integer locuri = resultSet.getInt("locuri");

                Zbor zbor = new Zbor(destinatie,data_ora,aeroport,locuri);
                zbor.setID(id);
                zboruri.add(zbor);
            }
            return zboruri;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zboruri;
    }

    @Override
    public void save(Zbor entity) {
    }


    @Override
    public void update(Zbor entity) {
        if(entity == null)
            throw new IllegalArgumentException("Entity must not be null");
        String sql = "update zbor set locuri=? where zbor_id = ?";
        int row_count = 0;

        try(Connection connection = dbutils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1,entity.getLocuri());
            ps.setInt(2,entity.getID());

            row_count = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    @Override
    public List<Zbor> findByDestination(String destination, LocalDateTime data){
        List<Zbor> zboruri = new ArrayList<>();
        try (Connection connection = dbutils.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM zbor where zbor.destinatie = ? and zbor.data_ora=?");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("zbor_id");
                String destinatie = resultSet.getString("destinatie");
                LocalDateTime data_ora= resultSet.getTimestamp("data_ora").toLocalDateTime();
                String aeroport = resultSet.getString("aeroport");
                Integer locuri = resultSet.getInt("locuri");

                Zbor zbor = new Zbor(destinatie,data_ora,aeroport,locuri);
                zbor.setID(id);
                zboruri.add(zbor);
            }
            return zboruri;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zboruri;
    }

}
