package com.cindyk.handlecommands;

/**
 * Created by cindy on 5/2/2015.
 */
public class Ping {

    public String handlePing(String data){

        System.out.println("Handling Ping: " + data);
        return "GotPing";
    }
}
