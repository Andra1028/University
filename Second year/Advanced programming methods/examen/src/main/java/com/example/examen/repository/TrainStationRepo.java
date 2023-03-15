package com.example.examen.repository;

import com.example.examen.domain.City;
import com.example.examen.domain.TrainStation;
import com.example.examen.domain.validation.Validator;


import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class TrainStationRepo implements Repo<TrainStation>{
    private String url;
    private String username;

    private String password;

    Validator<TrainStation> validator;

    public TrainStationRepo(String url, String username,String password, Validator<TrainStation> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator=validator;
    }

    @Override
    public Iterable<TrainStation> findAll() {
        Set<TrainStation> cities = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from train_station");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Integer trainStationId = resultSet.getInt("station_id");
                Integer departureId = resultSet.getInt("departure_id");
                Integer destinationIdId = resultSet.getInt("destination_id");

                TrainStation ts= new TrainStation(trainStationId,departureId,destinationIdId);
                cities.add(ts);
            }
            return cities;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }

}
