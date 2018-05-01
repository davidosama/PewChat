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

    public DataInputStream getInput() {
        return input;
    }

    public DataOutputStream getOut() {
        return out;
    }

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
                        StringTokenizer tokens = new StringTokenizer(msg, " ");
                        if (tokens.nextToken().equals("###")) {
                            System.out.println("Server message received: " + msg);
                            String peertopeer = tokens.nextToken();
                            if (peertopeer.equals("p2p")) {
                                String IP = tokens.nextToken();
                                String PortNum = tokens.nextToken();
                                String UserName = tokens.nextToken();
                                PeerNode PN = new PeerNode(UserName, IP, PortNum);
                            }

                        } else {
                            System.out.println("Message received: " + msg);
                            Messages.append("\n").append(msg);
                            newMessage = true;
//                            System.out.println("Messages STRING: " + Messages);
                        }

                    }
                } catch (IOException e) {
                    System.out.println("IO Exception");
                } catch (Exception ex) {
                    System.out.println("Exception");
                }
            }

        });
        System.out.println("HashMap in the end of readMessage size " + OtherUserStatus.size());
        readThread.start();
    }

    void SendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {

        }

    }

    void updateUsersStatuses(String msg) {
        StringTokenizer tokens = new StringTokenizer(msg, "\n");
        tokens.nextToken();
        while (tokens.hasMoreTokens()) {
            StringTokenizer user = new StringTokenizer(tokens.nextToken(), " ");
            String userName = tokens.nextToken();
            String userStatus = tokens.nextToken();
            //check if last \n is a token
            System.out.println("user: " + userName + " - status: " + userStatus);

            OtherUserStatus.add(new User(userName, userStatus));
        }
        UserStatusChanged = true;
    }

    void closeConnection() {
        try {
            this.out.close();
            this.input.close();
            this.socket.close();
        } catch (IOException ex) {

        }
    }

}
