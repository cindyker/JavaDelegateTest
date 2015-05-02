package com.cindyk;

import com.cindyk.HandleCommands.Inquiry;
import com.cindyk.HandleCommands.Ping;
import com.cindyk.HandleCommands.Reply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by cindy on 5/2/2015.
 * This was created as an example of how to use
 * Java8 Consumer to put function pointers in a
 * hashmap.
 */
public class Handler {

    Ping ping;
    Reply reply;
    Inquiry inquiry;

    //This MAP will hold the COMMAND and a pointer to the function
    //that knows how to handle that command.
    public Map<String, Consumer<String>> handles;

    public List<String> incomingData;

    Handler(){
        //Java8 Lets us use Function Pointers...
        // This is cool stuff

        //Initialize the Classes that handle our commands.
        ping = new Ping();
        reply = new Reply();
        inquiry = new Inquiry();

        //Create the Command Map
        handles = new HashMap<>();
        //Create an ArrayList with sample incoming data to test with
        incomingData = new ArrayList<String>();

        //Load the Test Data into the Array
        LoadTestData();

        //Load the Command Handler functions in to the Map
        LoadCommandHandlers();

        //Pretend to receive the data. For this test, it means we are flipping thru
        //the array list we filled with test data.
        ReceiveData();

    }

    //This would likely be done in a thread
    // the main thread would be listening on the command line for commands
    // from the console, like stop and reloadconfig
    void ReceiveData(){

        //This is the ForEach statement
        for(String Message:incomingData) {

            //We need to get the Command out of the Message
            //So we can see if we have it to handle
            String command = parseMessage(Message);
            //Check to see if the command is in our map
            if (handles.containsKey(command))
                //Call the Function we had saved, and pass it the Message data for
                // the function to process. This is the magic :)
                handles.get(command).accept(Message);
            else  //or we don't have that command...
                System.out.println("Command Not Found for Data: " + Message);
        }

    }

    //Parse out the Command from the Data
    String parseMessage(String message){

        String command;
        if(message.contains(" ")){
            command = message.substring(0,message.indexOf(" "));
        }
        else return message;

        return command;
    }

    // Load the Message Map with the command handlers
    void LoadCommandHandlers(){
        handles.put("PING", ping::HandlePing);
        handles.put("REPLY", reply::HandleReply);
        handles.put("INQUIRY", inquiry::HandleInquiry);
    }

    //Pretend Incoming Data
    void LoadTestData(){

        incomingData.add("INQUIRY did you get this inquiry?");
        incomingData.add("PING This is a ping1...");
        incomingData.add("REPLY this data contains a reply?");
        incomingData.add("UNKNOWN I am not sure how to handle this...");
        incomingData.add("PING This is a ping2...");
        incomingData.add("PING This is a ping3...");

    }
}
