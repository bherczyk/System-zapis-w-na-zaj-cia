
package edukacja;

import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;


public class LoginPanel extends javax.swing.JPanel {

    private LoginFrame loginFrame;
    private ApplicationFrame applicationFrame;
    
    public LoginPanel() {
        initComponents();
       
    }

    public LoginPanel(LoginFrame frame)
    {
        initComponents();
        loginFrame = frame;
    }

    
    public void confim() {
        User user = new User();
        
        String login = loginField.getText();
        String password = passwordField.getText();
        
        if(user.checked(login, password))
        {
            JOptionPane.showMessageDialog(null,"Dane prawidłowe!");
            loginFrame.setVisible(false);
            new ApplicationFrame().setVisible(true);
        }
        else
            JOptionPane.showMessageDialog(null, "Błędny login lub hasło");
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        loginField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        confimButton = new javax.swing.JButton();
        passwordField = new javax.swing.JPasswordField();

        setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("Login");

        loginField.setBackground(new java.awt.Color(102, 102, 102));
        loginField.setForeground(new java.awt.Color(204, 204, 204));

        jLabel2.setForeground(new java.awt.Color(204, 204, 204));
        jLabel2.setText("Hasło");

        confimButton.setBackground(new java.awt.Color(102, 102, 102));
        confimButton.setForeground(new java.awt.Color(204, 204, 204));
        confimButton.setText("Ok");
        confimButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confimButtonActionPerformed(evt);
            }
        });

        passwordField.setBackground(new java.awt.Color(102, 102, 102));
        passwordField.setForeground(new java.awt.Color(204, 204, 204));
        passwordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwordFieldKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(confimButton)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(loginField)
                            .addComponent(passwordField, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE))))
                .addContainerGap(142, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(loginField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addComponent(confimButton)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void confimButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confimButtonActionPerformed
        confim();
    }//GEN-LAST:event_confimButtonActionPerformed

    private void passwordFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordFieldKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            confim();
        }
    }//GEN-LAST:event_passwordFieldKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton confimButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField loginField;
    private javax.swing.JPasswordField passwordField;
    // End of variables declaration//GEN-END:variables
}
