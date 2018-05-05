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

            } catch (IOException e) {
                for (int i = 0; i < PewChatServer.users.size(); i++) {
                    if (this.name.equals(PewChatServer.users.get(i).name)) {
                        PewChatServer.users.remove(i);
                        break;
                    }
                }

            }
//            try {
//                // closing resources
//                this.inputStream.close();
//                this.outputStream.close();
//                this.socket.close();
//                for (int i = 0; i < PewChatServer.users.size(); i++) {
//                    if (this.name.equals(PewChatServer.users.get(i).name)) {
//                        PewChatServer.users.remove(i);
//                        break;
//                    }
//                }
//
//            } catch (IOException e) {
//
//            }
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
                    updateStatus(message);
                    broadcastStatus();
                    break;
                case "mystatus":
                    this.status = tokens.nextToken();
                    updateStatus(message);
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
                case "close":
                    closeConnection();
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
        System.out.println("Sending broadcast message to " + PewChatServer.users.size() + " client(s)");
        StringBuffer message = new StringBuffer("### statusbroadcast \n");
        
        for (User user : PewChatServer.users) {
            message.append(user.name + " " + user.status + " \n");
        }

        for (User user : PewChatServer.users) {
            try {
                user.outputStream.writeUTF(message.toString());
            } catch (IOException ex) {

            }
        }
    }

    public void closeConnection() {
        this.isOnline = false;
        try {
            this.inputStream.close();
            this.outputStream.close();
            this.socket.close();
        } catch (IOException ex) {

        }
        for (int i = 0; i < PewChatServer.users.size(); i++) {
            if (this.name.equals(PewChatServer.users.get(i).name)) {
                PewChatServer.users.remove(i);
                break;
            }
        }
    }

    public void updateStatus(String message) {
        StringTokenizer tokens = new StringTokenizer(message, " ");
        if(tokens.nextToken().equals("###")){
            if(tokens.nextToken().equals("myname")){
                this.name = tokens.nextToken();
                this.status = tokens.nextToken();
            }
            else if (tokens.nextToken().equals("mystatus")){
                this.status = tokens.nextToken();
            }
        }
    }

}
