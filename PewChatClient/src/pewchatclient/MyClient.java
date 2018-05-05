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
    String name = "";
    boolean admin = false;
    Boolean isConnected = false;
    ArrayList<User> OtherUserStatus = new ArrayList<User>();
    boolean UserStatusChanged = false;
    boolean GroupListChanged;
    ArrayList <String> groupNames;
    ArrayList<String> joinedGroups;
    String GroupSelected="";

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
            GroupListChanged=false;
            groupNames=new ArrayList<String>();
            joinedGroups=new ArrayList<String>();

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
                            String settingMsg = tokens.nextToken();
                            if (tokens.nextToken().equals("statusbroadcast")) {
                                updateUsersStatuses(msg);
                            }
                            else if (settingMsg.equals("p2p")) {
                                String IP = tokens.nextToken();
                                String PortNum = tokens.nextToken();
                                String UserName = tokens.nextToken();
                                //PeerNode PN = new PeerNode(UserName, IP, PortNum);
                            }
                            else if(settingMsg.equals("groupnamesbroadcast"))
                            {
                                setGroupNames(tokens);
                                GroupListChanged=true;
                                System.out.println("GrouListChanged is set to true");
                            }
                            else if(settingMsg.equals("history")){
                                Messages=Extract(tokens);
                                System.out.println("asd");
                                System.out.println("asd");
                                System.out.println("asd");
                                System.out.println("asd");
                                System.out.println("asd");
                                System.out.println("asd");
                                System.out.println("asd");
                                System.out.println("asd");
                                System.out.println("asd");
                                System.out.println("asd");
                                newMessage = true;
                            }
                            else if(settingMsg.equals("appendgroupmsg")){
                                Messages.append("\n"+Extract(tokens));
                                System.out.println("asd2");
                                System.out.println("asd");
                                System.out.println("asd");
                                System.out.println("asd");
                                System.out.println("asd");
                                System.out.println("asd");
                                System.out.println("asd");
                                System.out.println("asd");
                                System.out.println("asd");
                                System.out.println("asd");
                                
                                System.out.println("Extract Tokens"+Extract(tokens));
                                
                                newMessage = true;
                                
                                

                            }
                            //else if(settingMsg.equals())
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
        System.out.println("HashMap in the end of readMessage size " + OtherUserStatus.size());

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
    
    public void setGroupNames(StringTokenizer tokens){
        System.out.println("SetGroupnames");
        ArrayList<String> names=new ArrayList<String>();
        while(tokens.hasMoreTokens()){
            String gn = tokens.nextToken();
            System.out.println("Next Token is "+gn);
            names.add(gn);
        }
        System.out.println("Out of for loop in setGroupNamec");
        groupNames=names;
        GroupListChanged=true;
    }
    
    private StringBuffer Extract(StringTokenizer tokens) {
        StringBuffer msg = new StringBuffer();
        while (tokens.hasMoreTokens()) {
            msg.append(tokens.nextToken());
        }
        return msg;
    }

}
