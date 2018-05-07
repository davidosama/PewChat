package pewchatclient;

import java.io.*;
import java.net.*;

public class PeerNode {

    int portNum;
    String UserName;
    String IPaddress;
    StringBuffer MessageHistory;
    ServerSocket serverSocket;
    Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    boolean newMessage = false;

    //PeerNode acting as a Server constructor
    PeerNode(String peerUserName) {
        this.UserName = peerUserName;
        this.MessageHistory = new StringBuffer();

        try {
            serverSocket = new ServerSocket(0);
            this.portNum = serverSocket.getLocalPort();
            sendInfo();
            socket = serverSocket.accept();
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            ReadMessage();
            System.out.println("P2P connected with: " + this.UserName + " on port " + this.portNum);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("IOException in PeerNode constuctor (server)");
        }

    }

    //PeerNode acting as a Client constructor
    PeerNode(String UserName, String IPaddress, String portNum) {
        this.UserName = UserName;
        this.IPaddress = IPaddress;
        this.portNum = Integer.parseInt(portNum);
        this.MessageHistory = new StringBuffer();
        try {
            socket = new Socket(this.IPaddress, this.portNum);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            ReadMessage();
            System.out.println("P2P connected with: " + this.UserName + " on port " + this.portNum);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("IOException in PeerNode constuctor (client)");
        }

    }

    void sendInfo() {
        String infoMessage = "### p2p " + this.portNum + " " + this.UserName;
        System.out.println("P2P connection message: " + infoMessage);
        PewChatClient.frame.client.SendMessage(infoMessage);
    }

    void SendMessage(String message) {
        try {
            output.writeUTF(message);
            MessageHistory.append(PewChatClient.frame.client.name).append(" (P2P to ").append(this.UserName).append(" ): ").append(message).append("\n");
            System.out.println( "Sending a message P2P to " + this.UserName + " : " + message);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("IOException in PeerNode SendMessage()");
        }

    }

    void ReadMessage() {
        Thread readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.print("");
                try {
                    String message = input.readUTF();
                    MessageHistory.append(UserName).append(" (P2P): ").append(message).append("\n");
                    System.out.println("P2P message from " + UserName + ": " + message );
                    newMessage = true;

                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.out.println("IOException in P2P ReadMessage");
                }

            }
        });
        
        readThread.start();
    }

}
