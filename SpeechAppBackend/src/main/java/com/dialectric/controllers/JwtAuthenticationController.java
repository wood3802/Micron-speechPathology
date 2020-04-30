package com.dialectric.controllers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

import com.dialectric.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.dialectric.Database.DBWrapper;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService jwtInMemoryUserDetailsService;

    // controls the /authenticate request by parsing JwtRequest data and returning JwtResponse data
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> generateAuthenticationToken(@RequestBody com.dialectric.payload.request.JwtRequest authenticationRequest) throws Exception {
        String type = "";

        // authenticate based on the JwtRequest username and password data
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        // get the usertype
        try {
            // connect to db
            DBWrapper db = new DBWrapper();
            //TODO: Remove hard-coding
            Connection connection = db.getConnection("35.247.82.214", "SpeechPathology", "SpeechPathology", "testDB_2");

            // check for the account in the database from username and password and return the usertype if found
            type = db.FindAccount(connection, authenticationRequest.getUsername(), authenticationRequest.getPassword(), "SpeechPathology", "testDB_2");

        } catch( SQLException e) {
            e.printStackTrace();
        }

        // create a userDetails object based on the passed username
        final UserDetails userDetails = jwtInMemoryUserDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        // generate the token based on userDetails from com.dialectric.security.JwtTokenUtil.java
        final String token = jwtTokenUtil.generateToken(userDetails);

        // return the token back to the client
        return ResponseEntity.ok(new com.dialectric.payload.response.JwtResponse(token, type));
    }

    // authenticates based on the username and password using Spring's AuthenticationManager
    private void authenticate(String username, String password) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}