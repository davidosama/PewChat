package pewchatclient;

import java.net.*;
import java.io.*;
import java.util.*;

public class MyClient {
    // initialize socket and input output streams

    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;
    Scanner scn = new Scanner(System.in);
    Thread readThread;
    StringBuffer Messages = new StringBuffer();
    boolean newMessage = false;
    String status = "";
    Boolean isConnected = false;
    ArrayList<User> OtherUserStatus = new ArrayList<User>();
    boolean UserStatusChanged = false;

    public MyClient(String address, int port) {
        // establish a connection
        try {
            System.out.println("Connected");
            socket = new Socket(address, port);
            // takes input from terminal
            input = new DataInputStream(socket.getInputStream());

            // sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());
            isConnected = true;

        } catch (UnknownHostException u) {
            u.printStackTrace();
            System.out.println("UnknowHostException inside MyClient constructor");
        } catch (IOException i) {
            i.printStackTrace();
            System.out.println("IOException inside MyClient constructor");
        }

    }

    void ReadMessage() {
        readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        System.out.println("ReadMessage while called.");

                        // read the message sent to this client
                        String msg = input.readUTF();

                        StringTokenizer tokens = new StringTokenizer(msg, " ");
                        if (tokens.nextToken().equals("###")) {
                            System.out.println("Server message received: " + msg);
                            if (tokens.nextToken().equals("statusbroadcast")) {
                                updateUsersStatuses(msg);
                            }
                        } else {
                            System.out.println("Message received: " + msg);
                            Messages.append("\n").append(msg);
                            newMessage = true;
//                            System.out.println("Messages STRING: " + Messages);
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("IOException inside ReadMessage thread");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("Exception inside ReadMessage thread");
                }
            }

        });
        readThread.start();
    }

    void SendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException inside sendMessage");
        }

    }

    void updateUsersStatuses(String msg) {
        System.out.println("Broadcast message: " + msg);
        StringTokenizer tokens = new StringTokenizer(msg, "\n");
        tokens.nextToken();
        while (tokens.hasMoreTokens()) {
            StringTokenizer user = new StringTokenizer(tokens.nextToken(), " ");
            String userName = user.nextToken();
            String userStatus = user.nextToken();
            //check if last \n is a token
            System.out.println("user: " + userName + " - status: " + userStatus);

            boolean userAlreadyExist = false;

            for (User u : OtherUserStatus) {
                if (userName.equals(u.name)) {
                    u.status = userStatus;
                    userAlreadyExist = true;
                    break;
                }
            }
            if (!userAlreadyExist) {
                OtherUserStatus.add(new User(userName, userStatus));
            }
        }
        UserStatusChanged = true;
    }

    void closeConnection() {
        this.isConnected = false;
        //add closing all P2P connections
        try {
            this.out.close();
            this.input.close();
            this.socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("IOException inside closeConnection()");
        }
    }

}
