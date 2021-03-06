/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cyvis;

/**
 *
 * @author HOME
 */
public class BaseIPAddrSelection extends javax.swing.JFrame
{
	public static String baseIP = "" ; 

	/**
	 * Creates new form BaseIPAddrSelection
	 * @param currentAddress
	 */
	public BaseIPAddrSelection(String currentAddress)
	{
		initComponents();
		baseIP = currentAddress; 
	}

	/**
	 * This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents()
        {

                jScrollPane1 = new javax.swing.JScrollPane();
                infoPane = new javax.swing.JTextPane();
                jLabel1 = new javax.swing.JLabel();
                select_button = new javax.swing.JButton();
                jLabel2 = new javax.swing.JLabel();
                endIP_textField = new javax.swing.JTextField();
                startAddr_textField = new javax.swing.JTextField();
                jScrollPane2 = new javax.swing.JScrollPane();
                notifications = new javax.swing.JTextArea();

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

                infoPane.setText("Please enter the starting and ending range of the scanning procedure. \n\nExample 1: \n[Starting] 192.168.1.1\n[Ending] 192.168.1.255\n\nThis will scan the 192.168.1 range. \n\nExample 2: \n[Starting]: 192.168.1.1\n[Ending]: 192.168.2.255\n\nThis will scan the 192.168.1 and 192.168.2 ranges. \n\n");
                jScrollPane1.setViewportView(infoPane);

                jLabel1.setText("Starting Address:");

                select_button.setText("Select");
                select_button.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                select_buttonActionPerformed(evt);
                        }
                });

                jLabel2.setText("Ending Address:");

                endIP_textField.setText("192.168.2.255");

                startAddr_textField.setText("192.168.2.1");

                notifications.setColumns(20);
                notifications.setRows(5);
                jScrollPane2.setViewportView(notifications);

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane2)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jLabel1))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(endIP_textField, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                                                        .addComponent(startAddr_textField))
                                                .addGap(45, 45, 45)
                                                .addComponent(select_button)))
                                .addContainerGap())
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel1)
                                                        .addComponent(startAddr_textField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel2)
                                                        .addComponent(endIP_textField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(select_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                                .addContainerGap())
                );

                jLabel2.getAccessibleContext().setAccessibleName("Ending Address:");

                pack();
        }// </editor-fold>//GEN-END:initComponents



        private void select_buttonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_select_buttonActionPerformed
        {//GEN-HEADEREND:event_select_buttonActionPerformed
                // TODO add your handling code here:
        }//GEN-LAST:event_select_buttonActionPerformed

	public String[] getBaseIP() { 
		System.out.println("in base ip") ; 
		String regex = "\\." ; 

		String[] retval = new String[8]; 
		String s1 = startAddr_textField.getText() ; 
		String[] parse = s1.split(regex) ; 
		System.arraycopy(parse, 0, retval, 0, parse.length);

		String s2 = endIP_textField.getText() ; 
		parse = s2.split(regex); 
		System.arraycopy(parse, 0, retval, 4, parse.length);
		


		return retval ; 
	}

	private void updateNotifications(String s) { 
		notifications.append(s);
		notifications.append("\n") ; 
	}
	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[])
	{
		/* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		 */
		try
		{
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
			{
				if ("Nimbus".equals(info.getName()))
				{
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex)
		{
			java.util.logging.Logger.getLogger(BaseIPAddrSelection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex)
		{
			java.util.logging.Logger.getLogger(BaseIPAddrSelection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex)
		{
			java.util.logging.Logger.getLogger(BaseIPAddrSelection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex)
		{
			java.util.logging.Logger.getLogger(BaseIPAddrSelection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
        //</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
//				new BaseIPAddrSelection().setVisible(true);
				new BaseIPAddrSelection(baseIP).setVisible(true ); 
			}
		});
	}

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JTextField endIP_textField;
        private javax.swing.JTextPane infoPane;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JScrollPane jScrollPane2;
        private javax.swing.JTextArea notifications;
        public javax.swing.JButton select_button;
        private javax.swing.JTextField startAddr_textField;
        // End of variables declaration//GEN-END:variables
}
