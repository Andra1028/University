package com.example.java_mpp_bun.repository;


import com.example.java_mpp_bun.domain.Bilet;
import com.example.java_mpp_bun.utils.JdbcUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BiletRepository implements BiletRepo {

    private final JdbcUtils dbutils;
    private static final Logger logger = LogManager.getLogger();

    public BiletRepository(Properties prop) {
        logger.info("cdbc", prop);
        dbutils = new JdbcUtils(prop);
    }
    @Override
    public Bilet findOne(Integer id) {

        return null;
    }

    @Override
    public Bilet findByName(String userName) {
        return null;
    }

    @Override
    public List<Bilet> findAll() {
        List<Bilet> bilete = new ArrayList<>();
        try (Connection connection = dbutils.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * from bilet");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("bilet_id");
                Integer angajat_id = resultSet.getInt("angajat_id");
                Integer zbor_id= resultSet.getInt("zbor_id");
                String client_nume = resultSet.getString("client_nume");
                String client_adresa = resultSet.getString("client_adresa");
                Integer locuri = resultSet.getInt("nr_locuri");
                Bilet bilet = new Bilet(zbor_id,angajat_id,client_nume,null,client_adresa,locuri);
                bilet.setID(id);
                bilete.add(bilet);
            }
            return bilete;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bilete;
    }

    @Override
    public void save(Bilet entity) {
        if(entity == null)
            throw new IllegalArgumentException("Entity must not be null");
        String sql = "insert into bilet (angajat_id, zbor_id, client_nume, client_adresa, nr_locuri) values (?, ?,?,?,?)";
        String sqll ="insert into bilet_turisti (id_bilet, turist_nume) values (?, ?)";
        try (Connection connection = dbutils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1,entity.getAngajat_id());
            ps.setInt(2,entity.getZbor_id());
            ps.setString(3, entity.getClient_nume());
            ps.setString(4, entity.getClient_adresa());
            ps.setInt(5,entity.getNr_locuri());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection connection = dbutils.getConnection();
             PreparedStatement pss = connection.prepareStatement(sqll)) {
            for(String turist : entity.getTuristi()) {
                pss.setInt(1, entity.getID());
                pss.setString(2,turist);
                pss.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void update(Bilet entity) {
    }
    @Override
    public List<Bilet> findByDestination(String destination, LocalDateTime data){
        return null;
    }

}
