package com.example.java_mpp_bun.repository;

import com.example.java_mpp_bun.domain.User;
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

public class UserRepository implements UserRepo{

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public UserRepository(Properties props) {
        logger.info("Initializing UserRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }


    @Override
    public void save (User entity) {
        //to do
        logger.traceEntry("saving task {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into user(username, password) values (?,?)")){
            preStmt.setString(1,entity.getUsername());
            preStmt.setString(2, entity.getPassword());
            int result=preStmt.executeUpdate();
            logger.trace("Saved {} instances",result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB "+ex);
        }
        logger.traceExit();
    }


    @Override
    public List<User> findAll() {
        //to do
        logger.traceEntry("gettig find all");
        Connection con=dbUtils.getConnection();
        List<User> users = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from user"))
        {
            try(ResultSet result = preStmt.executeQuery())
            {
                while(result.next())
                {
                    int id = result.getInt("user_id");
                    String username = result.getString("username");
                    String password = result.getString("password");
                    User user = new User(username, password);
                    user.setID(id);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB"+e);
        }
        logger.traceExit(users);
        return users;
    }

    @Override
    public User findByName(String name) {
        for(User user: findAll())
            if(user.getUsername().equals(name))
                return user;
        return null;
    }

    @Override
    public User findOne(Integer id) {
        for(User user: findAll())
            if(user.getID().equals(id))
                return user;
        return null;
    }

    @Override
    public List<User> findByDestination(String destinatie, LocalDateTime data){return null;}

    @Override
    public void update(User entity){}
}

