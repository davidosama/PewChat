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
public class MyClient {
    // initialize socket and input output streams

    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;
    Scanner scn = new Scanner(System.in);
    Thread readThread;
    StringBuffer Messages = new StringBuffer();
    //String Messages="";
    String status="";
    Boolean isConnected=false;

    public MyClient(String address, int port, String status) {
        // establish a connection
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");
            
            // takes input from terminal
            input = new DataInputStream(socket.getInputStream());

            // sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());
            this.status=status;
            System.out.println("Client Status is "+status);
            isConnected = true;

        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }

    }

    void ReadMessage() {
        readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        // read the message sent to this client
                        String msg = input.readUTF();
                        Messages.append("\n").append(msg);
                        //Messages=msg;
                    }
                } catch (IOException e) {
                    System.out.println("IO Exception");
                    e.printStackTrace();
                } catch (Exception ex) {
                    System.out.println("Exception");
                    ex.printStackTrace();
                }
            }
        });
        readThread.start();
    }

    void SendMessage(String message) {
        try {
            // write on the output stream
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
