package pewchatserver;

import java.net.*;
import java.io.*;
import java.util.*;

public class User implements Runnable {

    Scanner scan = new Scanner(System.in);
    String name;
    String status;
    Socket socket;
    DataInputStream inputStream;
    DataOutputStream outputStream;
    boolean isOnline;

    public User(Socket socket, String username, DataInputStream inputStream, DataOutputStream outputStream) {
        this.name = username;
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.isOnline = true;
    }

    @Override
    public void run() {
        String recieved;
        while (true) {
            try {
                // receive the string
                recieved = inputStream.readUTF();
                System.out.println(name + " sent: " + recieved);
                if (!isServerMessage(recieved)) {
                    for (int i = 0; i < PewChatServer.users.size(); i++) {
                        PewChatServer.users.get(i).outputStream.writeUTF(name + ": " + recieved);
                    }
                } else {
                    HandleServerMessage(recieved);
                }

                if (recieved.equals("logout")) {
                    this.isOnline = false;
                    this.socket.close();
                    break;
                }

            } catch (IOException e) {

            }

        }
        try {
            // closing resources
            this.inputStream.close();
            this.outputStream.close();

        } catch (IOException e) {

        }
    }

    public boolean isServerMessage(String message) {
        StringTokenizer tokens = new StringTokenizer(message, " ");
        if (tokens.nextToken().equals("###")) {
            return true;
        }
        return false;
    }

    public void HandleServerMessage(String message) {
        StringTokenizer tokens = new StringTokenizer(message, " ");
        if (tokens.nextToken().equals("###")) {
            String currentToken = tokens.nextToken();
            switch (currentToken) {
                case "myname":
                    this.name = tokens.nextToken();
                    broadcastStatus();
                    break;
                case "mystatus":
                    this.status = tokens.nextToken();
                    broadcastStatus();
                    break;
                case "creategroup":
                    createGroup(tokens.nextToken());
                    break;
                case "joingroup":
                    joinGroup(tokens.nextToken());
                    break;
                case "leavegroup":
                    leaveGroup(tokens.nextToken());
                    break;
                case "p2p":
                    createP2Pchat(message);
                    break;
            }
        }
    }

    public void createGroup(String groupName) {
        Group g = new Group(groupName, this);
        PewChatServer.groups.add(g);
        broadcastGroupNames();
        
    }

    public void joinGroup(String groupName) {
        for (int i = 0; i < PewChatServer.groups.size(); i++) {
            if (PewChatServer.groups.get(i).GroupName.toString().equalsIgnoreCase(groupName)) {
                PewChatServer.groups.get(i).addParticipant(this);
            }
        }

    }

    public void leaveGroup(String groupName) {

        for (int i = 0; i < PewChatServer.groups.size(); i++) {
            if (PewChatServer.groups.get(i).GroupName.toString().equalsIgnoreCase(groupName)) {
                PewChatServer.groups.get(i).removeParticipant(this);
            }
            }
        }

    

    public void createP2Pchat(String p2pConnectionDetails) {
        StringTokenizer tokens = new StringTokenizer(p2pConnectionDetails, " ");
        StringBuffer msgToClient = new StringBuffer(tokens.nextToken() + " "
                + tokens.nextToken() + " " + tokens.nextToken() + " "
                + tokens.nextToken() + " " + this.name);
        String receiver = tokens.nextToken();
        for (User user : PewChatServer.users) {
            if (receiver.equals(user.name)) {
                try {
                    user.outputStream.writeUTF(msgToClient.toString());
                } catch (IOException ex) {

                }
            }

        }
    }

    public void broadcastStatus() {
        StringBuffer message = new StringBuffer("### statusbroadcast\n");

        for (User user : PewChatServer.users) {
            message.append(message + user.name + " " + user.status + " \n");
        }

        for (User user : PewChatServer.users) {
            try {
                user.outputStream.writeUTF(message.toString());
            } catch (IOException ex) {

            }
        }
    }
    
    public void broadcastGroupNames() {
        StringBuffer groupnames = new StringBuffer("### groupnamesbroadcast ");
        groupnames.append(Group.AllGroupsNames);
        System.out.println(groupnames.toString());
        for (User user : PewChatServer.users) {
            try {
                user.outputStream.writeUTF(groupnames.toString());
            } catch (IOException ex) {

            }
        }
    }

}
