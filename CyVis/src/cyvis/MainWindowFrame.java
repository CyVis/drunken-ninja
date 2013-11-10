/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cyvis;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBoxMenuItem;


/**
 *
 * @author HOME
 */
public final class MainWindowFrame extends javax.swing.JFrame {
	BaseIPAddrSelection selectionWindow ; 
	NetworkingInfo networkingInfo = new NetworkingInfo() ; 
	Scanning scan = new Scanning(networkingInfo) ; 
	ScanNetworkForDevices snd = null ; 


	private final ActionListener BaseIPAction ; 

	private final ActionListener DefaultConnectionActionListener;

	public MainWindowFrame() 
	{
		this.BaseIPAction = new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				networkingInfo.baseIP = selectionWindow.getBaseIP();
				selectionWindow.dispose();
				updateNotifications("New IP: " + networkingInfo.baseIP) ; 
				
				System.out.println(networkingInfo.baseIP) ;
				
			}
		};
		
		this.DefaultConnectionActionListener = new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int count = defaultConnectionJMenu.getItemCount();
				for (int i = 0; i < count; i++) {
					
					JCheckBoxMenuItem j = (JCheckBoxMenuItem) defaultConnectionJMenu.getItem(i) ; 
					String name = j.getName() ; 
					JCheckBoxMenuItem occurance = (JCheckBoxMenuItem) e.getSource(); 
					if (j.getName().equals(occurance.getName())) { 
						networkingInfo.setDefaultInterface(j.getName()) ; 
						j.setSelected(true);
					}
					else j.setSelected(false);
				}
				updateNotifications("Current activeInterface: " + networkingInfo.activeInterface.getDisplayName()) ; 
			}
			
		};
	
		initComponents();
		scan.getNetInterfacesAndScan();
		addItemsToConnectionMenu() ; 
		networkingInfo.printInterfaces(true, false) ; 
		updateList() ; 


	}



	
	private void startScanningThread() { 
		snd = new ScanNetworkForDevices(networkingInfo); 
		Thread t = new Thread(snd) ; 
		t.start(); 
	}
	private void updateList() { 
		Runnable run ; 
		run = new Runnable() {
		public boolean running = true ; 
			
			@Override
			public void run()
			{
				DefaultListModel model = new DefaultListModel() ; 
				deviceList.setModel(model);
				while (running) { 
					try
					{
						Thread.sleep(1000);
					} catch (InterruptedException ex)
					{
						Logger.getLogger(MainWindowFrame.class.getName()).log(Level.SEVERE, null, ex);
					}
				try { 

					int count = networkingInfo.networkDevices.size() ; 
					String[] devices = new String[count] ; 
					
					for (int i = 0 ; i < networkingInfo.networkDevices.size(); i++) { 
						NetworkDevice d = (NetworkDevice) networkingInfo.networkDevices.get(i); 
						devices[i] = d.hostname + ", " + d.address; 
//						model.add(i, d);
						if (!model.contains(devices[i])) 
						{
							System.out.println("Adding hostname: " + d.hostname + ", address: " + d.address) ; 
							model.addElement(devices[i]) ;
						} 
					}
				}
				catch (Exception e) { 
					System.err.println("Error occured adding element to list") ; 
				}
				}
			}

			
				
		};
		new Thread(run).start();
	}









	
	@SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents()
        {

                jMenuItem1 = new javax.swing.JMenuItem();
                jLabel1 = new javax.swing.JLabel();
                addr_label = new javax.swing.JLabel();
                jScrollPane1 = new javax.swing.JScrollPane();
                notifications = new javax.swing.JTextArea();
                jScrollPane2 = new javax.swing.JScrollPane();
                deviceList = new javax.swing.JList();
                scanButton = new javax.swing.JButton();
                jMenuBar1 = new javax.swing.JMenuBar();
                menuBar = new javax.swing.JMenu();
                jMenu2 = new javax.swing.JMenu();
                defaultIPBaseAddr = new javax.swing.JMenuItem();
                defaultConnectionJMenu = new javax.swing.JMenu();
                jMenu1 = new javax.swing.JMenu();
                ipv4_menuItem = new javax.swing.JMenuItem();
                ipv6_menuItem = new javax.swing.JMenuItem();

                jMenuItem1.setText("jMenuItem1");

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

                jLabel1.setText("Local Addr:");

                addr_label.setText("!");
                addr_label.setName("addr_label"); // NOI18N

                notifications.setColumns(20);
                notifications.setRows(5);
                jScrollPane1.setViewportView(notifications);

                deviceList.setBorder(new javax.swing.border.MatteBorder(null));
                deviceList.addMouseListener(new java.awt.event.MouseAdapter()
                {
                        public void mouseClicked(java.awt.event.MouseEvent evt)
                        {
                                deviceListMouseClicked(evt);
                        }
                });
                jScrollPane2.setViewportView(deviceList);

                scanButton.setText("Start Scan");
                scanButton.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                scanButtonActionPerformed(evt);
                        }
                });

                menuBar.setText("File");
                jMenuBar1.add(menuBar);

                jMenu2.setText("Edit");

                defaultIPBaseAddr.setLabel("Select Default Base IP");
                defaultIPBaseAddr.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                defaultIPBaseAddrActionPerformed(evt);
                        }
                });
                jMenu2.add(defaultIPBaseAddr);

                jMenuBar1.add(jMenu2);

                defaultConnectionJMenu.setLabel("Default Connections");
                jMenuBar1.add(defaultConnectionJMenu);

                jMenu1.setText("IP Version");

                ipv4_menuItem.setText("IPv4");
                ipv4_menuItem.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                ipv4_menuItemActionPerformed(evt);
                        }
                });
                jMenu1.add(ipv4_menuItem);

                ipv6_menuItem.setText("IPv6");
                ipv6_menuItem.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                ipv6_menuItemActionPerformed(evt);
                        }
                });
                jMenu1.add(ipv6_menuItem);

                jMenuBar1.add(jMenu1);

                setJMenuBar(jMenuBar1);

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jScrollPane1)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel1)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(addr_label, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 242, Short.MAX_VALUE)
                                                                .addComponent(scanButton))))
                                        .addComponent(jScrollPane2))
                                .addContainerGap())
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(addr_label)
                                        .addComponent(scanButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                );

                pack();
        }// </editor-fold>//GEN-END:initComponents

	// Opens the class that allows the user to change the base IP. This is to easily accomidate for different gateway addresses
        private void defaultIPBaseAddrActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_defaultIPBaseAddrActionPerformed
        {//GEN-HEADEREND:event_defaultIPBaseAddrActionPerformed

		updateNotifications(networkingInfo.baseIP) ; 
		selectionWindow = new BaseIPAddrSelection(networkingInfo.baseIP) ; 

		selectionWindow.select_button.addActionListener(BaseIPAction);
		selectionWindow.setLocationRelativeTo(null);
		selectionWindow.setVisible(true); 
        }//GEN-LAST:event_defaultIPBaseAddrActionPerformed

        private void deviceListMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_deviceListMouseClicked
        {//GEN-HEADEREND:event_deviceListMouseClicked
                // TODO add your handling code here:
		if (evt.getClickCount() == 2) { 
			int index = deviceList.locationToIndex(evt.getPoint()) ; 
			DefaultListModel dlm = (DefaultListModel) deviceList.getModel(); 
			String s = (String) dlm.get(index) ; 
			

			updateNotifications(s);
		
			for (int i = 0 ; i < networkingInfo.networkDevices.size(); i++) { 
				NetworkDevice d = (NetworkDevice) networkingInfo.networkDevices.get(i);
				String identifier = d.hostname + ", " + d.address; 
				if (s.equals(identifier)) { // Then this is the device we just clicked on
					for (int j = 0 ; j < d.openPorts.size(); j++) { 
						updateNotifications(d.openPorts.get(i).toString()); 
					}
				}
			}

		}
        }//GEN-LAST:event_deviceListMouseClicked

        private void scanButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_scanButtonActionPerformed
        {//GEN-HEADEREND:event_scanButtonActionPerformed
                // TODO add your handling code here:
		startScanningThread();
        }//GEN-LAST:event_scanButtonActionPerformed

        private void ipv4_menuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_ipv4_menuItemActionPerformed
        {//GEN-HEADEREND:event_ipv4_menuItemActionPerformed
                // TODO add your handling code here:
		updateNotifications("Clicked on IPV4 setter") ; 
		networkingInfo.setIPV4Active();
		ipv4_menuItem.setSelected(true);
		ipv6_menuItem.setSelected(false) ; 
        }//GEN-LAST:event_ipv4_menuItemActionPerformed

        private void ipv6_menuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_ipv6_menuItemActionPerformed
        {//GEN-HEADEREND:event_ipv6_menuItemActionPerformed
                // TODO add your handling code here:
		updateNotifications("Clicked on IPV6 setter") ; 
		networkingInfo.setIPV6Active();
		ipv4_menuItem.setSelected(false);
		ipv6_menuItem.setSelected(true) ; 
        }//GEN-LAST:event_ipv6_menuItemActionPerformed


	// Nothing to see here, really...
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
		} catch (	ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MainWindowFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
        //</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainWindowFrame().setVisible(true);
			}
		});
	}

	// Scans through the devices saved in the NetworkingInfo class and puts them in a menu. Makes it where you can select an item and change the active connection
	public void addItemsToConnectionMenu() { 
		for (NetworkInterface ni : networkingInfo.interfaces) {  // For each networking device, loop through it and get the different addresses

			JCheckBoxMenuItem jcbi = new JCheckBoxMenuItem() ; 		
			String text = "" ; 
			text += ni.getDisplayName() + ", " ; 

			Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();  // For said interface, get all addresses

			for (InetAddress inetAddress : Collections.list(inetAddresses)) { 
				String s = inetAddress.toString() ; 
				String s2 ; 
				s2 = s.replace("/", "") ;  // Ip address starts with a '/' - not sure why
				
				text += s2 ; 
				jcbi.setText(text);
				jcbi.setName(ni.getDisplayName());

				if(s2.contains(networkingInfo.baseIP)) { 
					jcbi.setSelected(true); // Mark it as selected in the list. Just a time saving activity. 
					networkingInfo.activeInterface = ni ;  // Define the active network interface - what we will be scanning with 
					addr_label.setText(s2);
				}
				
				jcbi.addActionListener(DefaultConnectionActionListener);
				defaultConnectionJMenu.add(jcbi) ; 
				break ; 
			}

		}
	} 

	// Function to make updating the notifications text box a bit easier. 
	public void updateNotifications(String s) { 
		notifications.setText(notifications.getText() + "\n" + s);
	} 



        // Variables declaration - do not modify//GEN-BEGIN:variables
        public javax.swing.JLabel addr_label;
        public javax.swing.JMenu defaultConnectionJMenu;
        private javax.swing.JMenuItem defaultIPBaseAddr;
        private javax.swing.JList deviceList;
        private javax.swing.JMenuItem ipv4_menuItem;
        private javax.swing.JMenuItem ipv6_menuItem;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JMenu jMenu1;
        private javax.swing.JMenu jMenu2;
        private javax.swing.JMenuBar jMenuBar1;
        private javax.swing.JMenuItem jMenuItem1;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JScrollPane jScrollPane2;
        public javax.swing.JMenu menuBar;
        public javax.swing.JTextArea notifications;
        private javax.swing.JButton scanButton;
        // End of variables declaration//GEN-END:variables
}
