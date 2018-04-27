package pewchatserver;

import java.net.*;
import java.io.*;
import java.util.*;


public class User implements Runnable {

    Scanner scan = new Scanner (System.in);
    String name;
    Socket socket;
    DataInputStream inputStream;
    DataOutputStream outputStream;
    boolean isOnline;
    
    public User (Socket socket, String username, DataInputStream inputStream, DataOutputStream outputStream){
        this.name = username;
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.isOnline = true;
    }

    @Override
    public void run() {
        String received;
        while (true) 
        {
            try
            {
                // receive the string
                received = inputStream.readUTF();
                 
                System.out.println(received);
                if(received.equals("logout")){
                    this.isOnline = false;
                    this.socket.close();
                    break;
                }
                 
                // break the string into message and recipient part
//                StringTokenizer st = new StringTokenizer(received, "#");
//                String MsgToSend = st.nextToken();
//                String recipient = st.nextToken();
                System.out.println(name+" is sending: "+received);
                
                for (int i = 0; i<PewChatServer.users.size(); i++) 
                    {
                        PewChatServer.users.get(i).outputStream.writeUTF(name+" : "+received);
                    }
            } catch (IOException e) {
                 
                e.printStackTrace();
            }
             
        }
        try
        {
            // closing resources
            this.inputStream.close();
            this.outputStream.close();
             
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
}
