package com.dialectric.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.dialectric.Database.DBWrapper;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String password = null;

        // grab the associated password if it exists
        try {
            // connect to db
            DBWrapper db = new DBWrapper();
            // TODO: remove hard-coding
            Connection connection = db.getConnection("35.247.82.214", "SpeechPathology", "SpeechPathology", "testDB_2");

            // grab the password from the db
            password = db.ReturnUserPass(connection, username);
            db.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if ( password != null) {
            return new User(username, password, new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

}
