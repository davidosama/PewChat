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
                }
                HandleServerMessage(recieved);

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

    }

    public void joinGroup(String groupName) {

    }

    public void leaveGroup(String groupName) {

    }

    public void createP2Pchat(String p2pConnectionDetails) {

    }

    public void broadcastStatus() {
        StringBuffer message = new StringBuffer("### statusbroadcast\n");
        
        for(User user : PewChatServer.users){
            message.append(message + user.name + " "+ user.status + " \n");
        }
        
        for(User user : PewChatServer.users){
            try {
                user.outputStream.writeUTF(message.toString());
            } catch (IOException ex) {
            
            }
        }
    }

}

