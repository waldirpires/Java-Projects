package com.wpjr.jersey.demo;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;

//**********************************************************************
// Copyright (c) 2016 Telefonaktiebolaget LM Ericsson, Sweden.
// All rights reserved.
// The Copyright to the computer program(s) herein is the property of
// Telefonaktiebolaget LM Ericsson, Sweden.
// The program(s) may be used and/or copied with the written permission
// from Telefonaktiebolaget LM Ericsson or in accordance with the terms
// and conditions stipulated in the agreement/contract under which the
// program(s) have been supplied.
// **********************************************************************

public class JerseyDemo
{

    private static final String PUBLIC_KEY = "19554a34e7f49a1956e56ce9e2cb99bc";
    private static final String PRIVATE_KEY_HASH = "da060e1e6f6e33c4e4e63c397aa9f3fa";
    private static final String BASE_URL = "http://gateway.marvel.com/v1/public/";
    private static final String PARAMS = "?ts=%s&apikey=%s&hash=%s&limit=%d";
    private static final String TIMESTAMP = "1478785656836";

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        String timeStamp = "1478785656836";
        //Thu Nov 10 11:47:36 BRST 2016
        int limit = 5;
        Client c = Client.create();
        String operation = "characters";
        c.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, true);
        String url = BASE_URL.concat("characters").concat(String.format(PARAMS, TIMESTAMP, PUBLIC_KEY, PRIVATE_KEY_HASH, limit));
        //BASE_URL + operation + "?ts=" + timeStamp + "&apikey=" + PUBLIC_KEY + "&hash=" + PRIVATE_KEY_HASH + "&limit=" + limit;
        //BASE_URL.concat("characters").concat(String.format(PARAMS, timeStamp, PUBLIC_KEY, PRIVATE_KEY_HASH, limit));
        System.out.println(url);
        //url = "http://gateway.marvel.com/v1/public/characters?ts=1478785656836&apikey=19554a34e7f49a1956e56ce9e2cb99bc&hash=da060e1e6f6e33c4e4e63c397aa9f3fa&limit=5";
        System.out.println("http://gateway.marvel.com/v1/public/characters?ts=1478785656836&apikey=19554a34e7f49a1956e56ce9e2cb99bc&hash=da060e1e6f6e33c4e4e63c397aa9f3fa&limit=5");
        //
        WebResource r = c.resource(url);
        String response = r.accept(MediaType.APPLICATION_JSON_TYPE).get(String.class);
        System.out.println(response);
    }

}
