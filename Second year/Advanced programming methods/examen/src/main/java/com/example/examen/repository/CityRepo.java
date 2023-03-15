package com.example.examen.repository;

import com.example.examen.domain.City;
import com.example.examen.domain.validation.Validator;


import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class CityRepo implements Repo<City>{
    private String url;
    private String username;

    private String password;
    Validator<City> validator;

    public CityRepo(String url, String username,String password, Validator<City> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator=validator;
    }

    @Override
    public Iterable<City> findAll() {
        Set<City> cities = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from cities");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Integer cityId = resultSet.getInt("city_id");
                String cityName = resultSet.getString("city_name");

                City city= new City(cityId,cityName);
                cities.add(city);
            }
            return cities;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }

}
