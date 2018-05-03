/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pewchatserver;

import java.util.ArrayList;

/**
 *
 * @author minarafla
 */
public class Group {
    
    StringBuffer GroupName;
    StringBuffer Messages;
    ArrayList<User> Participants;
    User Admin;
    static StringBuffer AllGroupsNames=new StringBuffer();
    
    public Group(String GroupName, User Admin){
        
        this.GroupName=new StringBuffer(GroupName);
        Participants=new ArrayList<User>();
        Messages = new StringBuffer();
        this.Admin=Admin;
        AllGroupsNames.append(" "+GroupName);
        
   }
    
    public void addParticipant(User newParticipant){
       Participants.add(newParticipant);
    }
    
    
}
