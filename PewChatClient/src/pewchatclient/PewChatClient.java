package pewchatclient;

import java.net.*;
import java.io.*;
import java.util.*;

public class PewChatClient {

    /**
     * @param args the command line arguments
     */
    static ClientFrame frame;
    
    public static void main(String[] args) {
        frame = new ClientFrame();
        PeerNode PN1 = new PeerNode("Toni");
        PeerNode PN2 = new PeerNode("Yaso","127.0.0.1","9999");
        

        
    }
    
}
