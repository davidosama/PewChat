/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pewchatclient;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author ToniGeorge
 */
public class MyClient  {
     // initialize socket and input output streams
    private Socket socket            = null;
    private DataInputStream  input   = null;
    private DataOutputStream out     = null;
    Scanner scn = new Scanner(System.in);
    public MyClient(String address, int port)
        {
    // establish a connection
            try
            {
                socket = new Socket(address, port);
                System.out.println("Connected");

                // takes input from terminal
                input  = new DataInputStream(System.in);

                // sends output to the socket
                out    = new DataOutputStream(socket.getOutputStream());
            }
            catch(UnknownHostException u)
            {
                System.out.println(u);
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
        
        }
    void ReadMessage (){
        Thread readMessage = new Thread(new Runnable() 
        {
            @Override
            public void run() {
 
                while (true) {
                    try {
                        // read the message sent to this client
                        String msg = input.readUTF();
                        System.out.println(msg);
                    } catch (IOException e) {
 
                        e.printStackTrace();
                    }
                }
            }
        });
 

    }
    void SendMessage (){
        Thread sendMessage = new Thread(new Runnable() 
        {
            @Override
            public void run() {
                while (true) {
 
                    // read the message to deliver.
                    String msg = scn.nextLine();
                     
                    try {
                        // write on the output stream
                        out.writeUTF(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
       
}
