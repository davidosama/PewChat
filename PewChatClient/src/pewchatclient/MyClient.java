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

    public MyClient(String address, int port) {
        // establish a connection
        try {
            System.out.println("Connected");
            socket = new Socket(address, port);
            // takes input from terminal
            input = new DataInputStream(socket.getInputStream());

            // sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());

        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }

    }

    void ReadMessage() {
        System.out.println("ReadMessage called.");
        readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("ReadMessage thread called.");
                try {
                    while (true) {
                        System.out.println("ReadMessage while called.");

                        // read the message sent to this client
                        String msg = input.readUTF();
                        System.out.println("Message received: " + msg);
                        Messages.append("\n").append(msg);
                        System.out.println("Messages STRING: " + Messages);

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
