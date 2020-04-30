package com.dialectric.payload.response;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private String usertype;

    public JwtResponse(String jwttoken, String usertype) {
        this.jwttoken = jwttoken;
        this.usertype = usertype;
    }

    public String getToken() {
        return this.jwttoken;
    }
    public String getUsertype() {
        return this.usertype;
    }
}