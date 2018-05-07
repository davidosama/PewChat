package pewchatclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class User implements Runnable{
    Scanner scan = new Scanner(System.in);
    String name;
    String status;
    Socket socket;
    DataInputStream inputStream;
    DataOutputStream outputStream;
    
    public User(Socket socket,DataInputStream inputStream, DataOutputStream outputStream) {
        //this.name = username;
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
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
}
