package com.dialectric.controllers;

import com.dialectric.Database.DBWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.Connection;
import java.sql.SQLException;


public class RecordsController extends DBWrapper {
    // controls the /records request by parsing user request information and returning a list of patients
    @RequestMapping(value = "/records", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody com.dialectric.payload.request.RecordsRequest request) throws Exception {

        try {
            // connect to db
            DBWrapper db = new DBWrapper();
            //TODO: Remove hard-coding
            Connection connection = db.getConnection("35.247.82.214", "SpeechPathology", "SpeechPathology", "testDB_2");

            db.closeConnection(connection);
        } catch( SQLException e) {
            e.printStackTrace();
        }

        // return the response back to the client
        return ResponseEntity.ok(new com.dialectric.payload.response.RecordsResponse());
    }
}