package pewchatserver;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                    updateStatus(message);
                    broadcastStatus();
                    broadcastGroupNames();
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
                case "groupmsg":
                    String GN = tokens.nextToken();
                    String msg = Extract(tokens).toString();
                    sendMessageToGroup(GN, msg);
                    break;
                case "givemehist":
                    sendMsgHistBack(tokens.nextToken());
                    break;
                case "kickout":
                    String UserN = tokens.nextToken();
                    String GroupN = tokens.nextToken();
                    kickOutUser(UserN, GroupN);
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
        
        StringBuffer IPaddress = new StringBuffer(this.socket.getInetAddress().toString());
        IPaddress.deleteCharAt(0);
        
        StringBuffer msgToClient = new StringBuffer(tokens.nextToken() + " "
                + tokens.nextToken() + " " + IPaddress + " "
                + tokens.nextToken() + " " + this.name);
        String p2pClientName = tokens.nextToken();
        for (User user : PewChatServer.users) {
            if (p2pClientName.equals(user.name)) {
                try {
                    user.outputStream.writeUTF(msgToClient.toString());
                } catch (IOException ex) {

                }
            }

        }
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

    public void broadcastGroupNames() {
        StringBuffer groupnames = new StringBuffer("### groupnamesbroadcast ");
        groupnames.append(Group.AllGroupsNames);
        System.out.println("GroupNames with toString() " + groupnames.toString());

        for (User user : PewChatServer.users) {
            try {
                user.outputStream.writeUTF(groupnames.toString());
            } catch (IOException ex) {

            }
        }
    }

    public void sendMessageToGroup(String GN, String msg) {
        for (int i = 0; i < PewChatServer.groups.size(); i++) {
            if (PewChatServer.groups.get(i).GroupName.toString().equalsIgnoreCase(GN)) {
                PewChatServer.groups.get(i).Messages.append(msg + "\n");
                for (int j = 0; j < PewChatServer.groups.get(i).Participants.size(); j++) {
                    try {
                        //                   try {
                        PewChatServer.groups.get(i).Participants.get(j).outputStream.writeUTF("### appendgroupmsg " + msg);
                        //                   }
//                    catch (IOException ex) {
//                        Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
//                    }
                    } catch (IOException ex) {
                        Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }
    }

    public void kickOutUser(String Username, String GroupName) {
        for (int i = 0; i < PewChatServer.groups.size(); i++) {
            if (PewChatServer.groups.get(i).GroupName.toString().equalsIgnoreCase(GroupName)) {
                for (int j = 0; j < PewChatServer.groups.get(i).Participants.size(); j++) {
                    PewChatServer.groups.get(i).Participants.remove(j);

                }

            }
        }
    }

    private void sendMsgHistBack(String GroupName) {
        for (int i = 0; i < PewChatServer.groups.size(); i++) {
            if (PewChatServer.groups.get(i).GroupName.toString().equalsIgnoreCase(GroupName)) {
                StringBuffer history = PewChatServer.groups.get(i).Messages;
                try {
                    this.outputStream.writeUTF("### history " + history.toString());
                } catch (IOException ex) {
                    Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    private StringBuffer Extract(StringTokenizer tokens) {
        StringBuffer msg = new StringBuffer();
        while (tokens.hasMoreTokens()) {
            msg.append(tokens.nextToken());
        }
        return msg;
    }

    public void updateStatus(String message) {
        StringTokenizer tokens = new StringTokenizer(message, " ");
        if (tokens.nextToken().equals("###")) {
            if (tokens.nextToken().equals("myname")) {
                this.name = tokens.nextToken();
                this.status = tokens.nextToken();
            } else if (tokens.nextToken().equals("mystatus")) {
                this.status = tokens.nextToken();
            }
        }
    }
}
