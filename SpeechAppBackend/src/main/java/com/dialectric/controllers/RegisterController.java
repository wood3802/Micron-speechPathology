package com.dialectric.controllers;

import com.dialectric.Database.DBWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.SQLException;

@RestController
@CrossOrigin
public class RegisterController extends DBWrapper {
    // controls the /register request by parsing user data and returning RegisterResponse data
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody com.dialectric.models.User user) throws Exception {
        String result = "";

        try {
            // connect to db
            DBWrapper db = new DBWrapper();
            //TODO: Remove hard-coding
            Connection connection = db.getConnection("35.247.82.214", "SpeechPathology", "SpeechPathology", "testDB_2");

            // set the account type
            //TODO: needed for DBWrapper CreateAccount() to function but should be changed
            setAccountType(user.getUsertype());

            // hash the password
            String computed_hash = db.hashPassword(user.getPassword());

            // add the account into the database
            db.CreateAccount(connection, user.getUsername(), computed_hash, user.getFname(), user.getLname(), "SpeechPathology", "testDB_2");
            result = "success";
            db.closeConnection(connection);
        } catch( SQLException e) {
            e.printStackTrace();
            result = "error";
        }

        // return the response back to the client
        return ResponseEntity.ok(new com.dialectric.payload.response.RegisterResponse(result));
    }
}
