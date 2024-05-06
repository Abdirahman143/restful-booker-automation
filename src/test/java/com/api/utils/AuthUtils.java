package com.api.utils;

import com.api.authentication.request.AuthRequest;
import com.api.authentication.response.AuthResponse;
import com.api.baseRoute.BaseRoute;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.*;

public class AuthUtils {
    private static final Logger logger = LoggerFactory.getLogger(AuthUtils.class);
    private static final String CONTENT_TYPE="application/json";
    private static ObjectMapper objectMapper=new ObjectMapper();


    public static AuthResponse getAuthToken(String username, String password){
        AuthRequest authRequest = new AuthRequest(username,password);

        Response response=
                given().
                        header("Content-Type",CONTENT_TYPE).
                        body(authRequest).
                        when().
                        post(BaseRoute.BASE_URL+"/auth").
                        thenReturn();

        try{
            AuthResponse authResponse =objectMapper.readValue(response.asString(), AuthResponse.class);
            if(response.statusCode()!=200 &&authResponse.getReason()!=null){
                logger.error("Authentication failed with reason: {}", authResponse.getReason());
            }
            return authResponse;
        }catch (Exception e){
            logger.error("Error processing authentication response: ", e);
        }
        return null;
    }



}
