package pewchatclient;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ClientFrame extends javax.swing.JFrame {
    
    
    MyClient client ;
    static String msgRecieved;
    boolean userslistLastClick=false;
    String LastGroupSelected="";
    //boolean changedGroupSelection=false;
    

    /**
     * Creates new form ClientFrame
     */
    public ClientFrame() {
        initComponents();
        ListSelectionListener GroupListListener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                try{
                //changedGroupSelection=true;
                userslistLastClick = false;
                String selectedGroup = GroupsjList.getSelectedValue().toString();
                client.GroupSelected=selectedGroup;
                LastGroupSelected=selectedGroup;
                boolean disabled = false;
                for (int i = 0; i < client.joinedGroups.size(); i++) {
                    if (client.joinedGroups.get(i).equals(selectedGroup)) {
                        disabled = true;
                        client.SendMessage("### givemehist "+selectedGroup);
                    }
                }
                if (!disabled) {
                    JoinBtn.setEnabled(true);
                } else {
                    JoinBtn.setEnabled(false);
                }
                }catch(Exception e){ 
                    System.out.println("EXCEPTION!!!!!!!!"+e.getMessage()+"   "+"   "+e.toString());
                }
                
            }
            };
        
        
        ListSelectionListener userslistListener = new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent listSelectionEvent) {
                    userslistLastClick = true;
                }
            };

        
        UsersjList.addListSelectionListener(GroupListListener);
        GroupsjList.addListSelectionListener(GroupListListener);

    }
    public String getEncodedStatus(){
        if(StatusComboBox.getSelectedItem()=="Online"){
            return "### mystatus Online";
        }
        else if(StatusComboBox.getSelectedItem()=="Busy"){
            return "### mystatus Busy";
        }
        else if(StatusComboBox.getSelectedItem()=="Away"){
            return "### mystatus Away";
        }
        else if(StatusComboBox.getSelectedItem()=="Offline"){
            return "### mystatus Offline";
        }
        else return "Error";
   }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PortNumberLabel = new javax.swing.JLabel();
        UsernameTextField = new javax.swing.JTextField();
        UsernameLabel = new javax.swing.JLabel();
        PortNumTextField = new javax.swing.JTextField();
        AddressLabel = new javax.swing.JLabel();
        AddressTextField = new javax.swing.JTextField();
        DisconnectBtn = new javax.swing.JButton();
        ConnectBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ChatTextArea = new javax.swing.JTextArea();
        SendBtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        MsgTextArea = new javax.swing.JTextArea();
        UserStatusLabel = new javax.swing.JLabel();
        StatusLabel = new javax.swing.JLabel();
        StatusComboBox = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        GroupsjList = new javax.swing.JList<>();
        GroupsLabel = new javax.swing.JLabel();
        JoinBtn = new javax.swing.JButton();
        LeaveBtn = new javax.swing.JButton();
        CreateGroupBtn = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        UsersjList = new javax.swing.JList<>();
        KickOutBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PewChat");
        setResizable(false);

        PortNumberLabel.setText("Port #");

        UsernameLabel.setText("Username");

        PortNumTextField.setText("9999");

        AddressLabel.setText("Address");

        AddressTextField.setText("localhost");

        DisconnectBtn.setText("Disconnect");
        DisconnectBtn.setEnabled(false);
        DisconnectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DisconnectBtnActionPerformed(evt);
            }
        });

        ConnectBtn.setText("Connect");
        ConnectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConnectBtnActionPerformed(evt);
            }
        });

        ChatTextArea.setEditable(false);
        ChatTextArea.setColumns(20);
        ChatTextArea.setRows(5);
        jScrollPane1.setViewportView(ChatTextArea);

        SendBtn.setText("Send Message");
        SendBtn.setEnabled(false);
        SendBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendBtnActionPerformed(evt);
            }
        });

        MsgTextArea.setColumns(20);
        MsgTextArea.setRows(5);
        jScrollPane2.setViewportView(MsgTextArea);

        UserStatusLabel.setText("Users Status");

        StatusLabel.setText("status");

        StatusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Online", "Busy", "Away", "Offline" }));
        StatusComboBox.setEnabled(false);
        StatusComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StatusComboBoxActionPerformed(evt);
            }
        });

        jScrollPane4.setViewportView(GroupsjList);

        GroupsLabel.setText("Groups");

        JoinBtn.setText("Join");
        JoinBtn.setEnabled(false);
        JoinBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JoinBtnActionPerformed(evt);
            }
        });

        LeaveBtn.setText("Leave");
        LeaveBtn.setEnabled(false);
        LeaveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LeaveBtnActionPerformed(evt);
            }
        });

        CreateGroupBtn.setText("Create Group");
        CreateGroupBtn.setEnabled(false);
        CreateGroupBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateGroupBtnActionPerformed(evt);
            }
        });

        UsersjList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "1" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane5.setViewportView(UsersjList);

        KickOutBtn.setText("Kick out");
        KickOutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KickOutBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(UsernameLabel)
                                .addGap(18, 18, 18)
                                .addComponent(UsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(AddressLabel)
                                .addGap(18, 18, 18)
                                .addComponent(AddressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(PortNumberLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ConnectBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(72, 72, 72)
                                .addComponent(DisconnectBtn)))
                        .addGap(18, 18, 18)
                        .addComponent(PortNumTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                        .addComponent(SendBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(StatusLabel)
                .addGap(18, 18, 18)
                .addComponent(StatusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(174, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(KickOutBtn)
                .addGap(125, 125, 125))
            .addGroup(layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(GroupsLabel)
                        .addGap(18, 18, 18)
                        .addComponent(CreateGroupBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(UserStatusLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(JoinBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(LeaveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(0, 51, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AddressLabel)
                    .addComponent(AddressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UsernameLabel)
                    .addComponent(UsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PortNumberLabel)
                    .addComponent(PortNumTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(StatusLabel)
                    .addComponent(StatusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DisconnectBtn)
                            .addComponent(ConnectBtn)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(UserStatusLabel)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(GroupsLabel)
                            .addComponent(CreateGroupBtn))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SendBtn)
                    .addComponent(JoinBtn)
                    .addComponent(LeaveBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(KickOutBtn)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DisconnectBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DisconnectBtnActionPerformed
        client.closeConnection();
        DisconnectBtn.setEnabled(false);
        ConnectBtn.setEnabled(true);
    }//GEN-LAST:event_DisconnectBtnActionPerformed

    private void ConnectBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConnectBtnActionPerformed
JoinBtn.setEnabled(true);
LeaveBtn.setEnabled(true);
KickOutBtn.setEnabled(false);
//System.out.println("HashMap in the beginning of Connect size "+ client.OtherUserStatus.size());
        client = new MyClient(AddressTextField.getText(),Integer.parseInt(PortNumTextField.getText()));
        client.name = UsernameTextField.getText();
        client.SendMessage("### myname "+UsernameTextField.getText());
        DisconnectBtn.setEnabled(true);
        ConnectBtn.setEnabled(false);
        SendBtn.setEnabled(true);
        CreateGroupBtn.setEnabled(true);
        StatusComboBox.setEnabled(true);
        client.ReadMessage();
        client.SendMessage(getEncodedStatus());
        JoinBtn.setEnabled(true);
        client.GroupListChanged = true;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println();
                while(true){
                    System.out.println("Check ChatTextArea is running");
                    if(client.newMessage == true){
                        ChatTextArea.setText(client.Messages.toString());
                        client.newMessage = false;
                    }  
                      
                }
            }
        });
        t.start();
        
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("Check UsersStatusArea is running");
                    if (client.UserStatusChanged == true) {
                        for ( int i = 0; i < client.OtherUserStatus.size(); i++){
                            User user = client.OtherUserStatus.get(i);
                            //show all users with their statuses in the JList
//                            UsersjList.add(i, user.name+" - "+user.status);
                        }
                        client.UserStatusChanged=false;
                    }
                }
            }
        });
        t2.start();
        
        
        Thread groupThreads = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("Check groupnames is running");
                    if (client.GroupListChanged == true) {
                        System.out.println("GrouListChanged = true");
                        DefaultListModel listModel=new DefaultListModel();
                        for (int i =0; i<client.groupNames.size();i++){
                            listModel.addElement(client.groupNames.get(i));
                            System.out.println(client.groupNames.get(i));
                        }
                        System.out.println("asdkjashkdajshdkajshdakjsdhakjshdaksjdhaksjhdkajhdsas");
                        GroupsjList.setModel(listModel);
                        client.GroupListChanged=false;
                    }
                }
            }
        });
        groupThreads.start();
        
//        Thread groupMsgs = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    System.out.println("group selections");
//                    if(changedGroupSelection){
//                          String selectedGroup=GroupsjList.getSelectedValue().toString();
//                          changedGroupSelection=false;
//                          while(selectedGroup==GroupsjList.getSelectedValue().toString()){
//                              client.SendMessage("### givemehist "+selectedGroup);
//                          }
//                      }
//                }
//            }
//        });
//        groupMsgs.start();
    }//GEN-LAST:event_ConnectBtnActionPerformed

    private void SendBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendBtnActionPerformed
//        if(GroupsjList.getSel){
//        }
        if(userslistLastClick){
            //if send to a p2p
            client.SendMessage("### p2p "+"..el Ip w el adress....."+ UsersjList.getSelectedValue().toString() +MsgTextArea.getText());
        }
        else{
            //if send to a group
            client.SendMessage("### groupmsg "+GroupsjList.getSelectedValue().toString()+" "+client.name+":"+MsgTextArea.getText());
            
        }
        client.newMessage=true;
        ChatTextArea.append("\n"+MsgTextArea.getText());
        MsgTextArea.setText(""); //to clear the chat text area after sending
        
    }//GEN-LAST:event_SendBtnActionPerformed

    private void StatusComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StatusComboBoxActionPerformed
        try {
            if (client.isConnected == true) {
                client.status = (String) StatusComboBox.getSelectedItem();
                client.UserStatusChanged=true;
                client.SendMessage(getEncodedStatus());
            } else {

            }
        } catch (Exception ex) {
            System.out.println("Connected Not Pressed, client is still not instantiated");
        }
    }//GEN-LAST:event_StatusComboBoxActionPerformed

    private void CreateGroupBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateGroupBtnActionPerformed
        JoinBtn.setEnabled(true);
        String GroupName = JOptionPane.showInputDialog(this, "Enter Group Name");
        client.SendMessage("### creategroup "+GroupName);
        client.admin = true;
        KickOutBtn.setEnabled(true);
        
    }//GEN-LAST:event_CreateGroupBtnActionPerformed

    private void JoinBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JoinBtnActionPerformed
        // TODO add your handling code here:
        String GroupName=GroupsjList.getSelectedValue().toString();
        client.joinedGroups.add(GroupName);
        JoinBtn.setEnabled(false);
        client.SendMessage("### joingroup "+GroupName);
        System.out.println("JOINED GROUPS");
        for(int i =0;i<client.joinedGroups.size();i++){
            System.out.println(client.joinedGroups.get(i));
        }
        
    }//GEN-LAST:event_JoinBtnActionPerformed

    private void LeaveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LeaveBtnActionPerformed
        // TODO add your handling code here:
        String GroupName=GroupsjList.getSelectedValue().toString();
        client.SendMessage("### leavegroup "+GroupName);
        LeaveBtn.setEnabled(false);
        JoinBtn.setEnabled(true);
        
        
    }//GEN-LAST:event_LeaveBtnActionPerformed

    private void KickOutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KickOutBtnActionPerformed
        // TODO add your handling code here:
        String GroupNameSelected = GroupsjList.getSelectedValue().toString();
        String UserSelected = UsersjList.getSelectedValue().toString();
        client.SendMessage("### kickout "+UserSelected+" "+GroupNameSelected);
        
    }//GEN-LAST:event_KickOutBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AddressLabel;
    private javax.swing.JTextField AddressTextField;
    private javax.swing.JTextArea ChatTextArea;
    private javax.swing.JButton ConnectBtn;
    private javax.swing.JButton CreateGroupBtn;
    private javax.swing.JButton DisconnectBtn;
    private javax.swing.JLabel GroupsLabel;
    private javax.swing.JList<String> GroupsjList;
    private javax.swing.JButton JoinBtn;
    private javax.swing.JButton KickOutBtn;
    private javax.swing.JButton LeaveBtn;
    private javax.swing.JTextArea MsgTextArea;
    private javax.swing.JTextField PortNumTextField;
    private javax.swing.JLabel PortNumberLabel;
    private javax.swing.JButton SendBtn;
    private javax.swing.JComboBox<String> StatusComboBox;
    private javax.swing.JLabel StatusLabel;
    private javax.swing.JLabel UserStatusLabel;
    private javax.swing.JLabel UsernameLabel;
    private javax.swing.JTextField UsernameTextField;
    private javax.swing.JList<String> UsersjList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    // End of variables declaration//GEN-END:variables
}
