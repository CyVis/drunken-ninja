/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package cyvis;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HOME
 */
public class ScanNetworkForDevices_Backup implements Runnable
{
	List networkDevices ;
	NetworkingInfo networkingInfo ; 
	public boolean ipv6_compatability = false ; 
	public int[] scanStartRange = new int[4] ; 
	public int [] scanEndRange = new int[4] ; 
	
	
//	ScanNetworkForDevices(List networkDevices)
	//ScanNetworkForDevices(NetworkingInfo ni, int[] ssR, int[] seR) 
	ScanNetworkForDevices_Backup(NetworkingInfo ni)
	{
		this.networkingInfo = ni ; 
		this.networkDevices = networkingInfo.networkDevices ; // networkDevices is initialized in the MainWindowFrame class

		for (String s : networkingInfo.scanningRange) { 
			System.out.println(s) ; 
		}

		scanStartRange[0] = Integer.parseInt(networkingInfo.scanningRange[0]) ; 
		scanStartRange[1] = Integer.parseInt(networkingInfo.scanningRange[1]) ; 
		scanStartRange[2] = Integer.parseInt(networkingInfo.scanningRange[2]) ; 
		scanStartRange[3] = Integer.parseInt(networkingInfo.scanningRange[3]) ; 
		scanEndRange[0] = Integer.parseInt(networkingInfo.scanningRange[4]) ; 
		scanEndRange[1] = Integer.parseInt(networkingInfo.scanningRange[5]) ; 
		scanEndRange[2] = Integer.parseInt(networkingInfo.scanningRange[6]) ; 
		scanEndRange[3] = Integer.parseInt(networkingInfo.scanningRange[7]) ; 

		
		
		
	}


	
	 // Iterates through the range of IP addresses and creates another thread to test the connection
	// This is the runnable method for the ScanNetworkDevices class, i.e.
	// THIS IS THE MAIN METHOD OF THIS CLASS
	@Override
	public void run()
	{
		boolean running = true ;
		int timeout = 100 ;
		
		while(running)  {
			int delay = 30 ; // Tells the thread how much to wait before calling another address. Lowering this value too much will DoS your network
			List<Thread> threads = new ArrayList<>() ;
			
			InetAddress localhost ;
			localhost = null;
			try
			{
				localhost = InetAddress.getByName(networkingInfo.ipv4_address) ; 
				System.out.println(networkingInfo.ipv4_address) ; 
				System.out.println("localhost: " + localhost); 
				try
				{
					Thread.sleep(1000);
				} catch (InterruptedException ex)
				{
					Logger.getLogger(ScanNetworkForDevices.class.getName()).log(Level.SEVERE, null, ex);
				}
			} catch (UnknownHostException ex)
			{
//				Logger.getLogger(MainWindowFrame.class.getName()).log(Level.SEVERE, null, ex);
			}

			
			
			
			byte[] ip = localhost.getAddress();
			
			for (int i = 0; i < 255; i++) {
				//System.out.println("Checking on subaddress: " + i + " with timeout: " + timeout)  ;
				checkAddress ca = new checkAddress(timeout, ip, i, networkDevices);
				Thread t = new Thread(ca) ;
				t.start() ;
				threads.add(t);
				
				try
				{
					Thread.sleep(delay);
				} catch (InterruptedException ex)
				{
//					Logger.getLogger(ScanNetworkForDevices.class.getName()).log(Level.SEVERE, null, ex);
				}
			}

			timeout += timeout ;
			System.out.println("Done iterating through addresses. Attempting Thread joins") ; 
			
			try
			{
				for (int i = 0; i < threads.size(); i++) { //TODO
					Thread t = threads.get(i) ;
					t.join(100); // There is a bug here. All threads should be stopped at this point, yet the following line sometimes displays true
					//System.out.println("t.alive = " + t.isAlive()) ; 
				}
			} catch (InterruptedException ex)
			{
//				Logger.getLogger(ScanNetworkForDevices.class.getName()).log(Level.SEVERE, null, ex);
			}
			
			if (timeout >= 1000) timeout += timeout ;
			if (timeout >= 1000) { 
				running = false;
				System.out.println("Ran through full sweep. Stopping for dev. purposes") ; 
			}
		}
	}
	
	// Class that gets created as a thread to check to see if the address is reachable. 
	// Caller: Parent class
	// Calls: Port Scanner
	private class checkAddress implements Runnable  {
		
		public NetworkDevice addressExists(String address) {
			NetworkDevice d = null ;
			for (int i = 0 ; i < networkDevices.size(); i++) {
				d = (NetworkDevice) networkDevices.get(i);
				if (d.address.equals(address)){
					return d;
				}
			}
			return null;
		}
		public NetworkDevice hostnameExists(String hostname) {
			NetworkDevice d = null ;
			for (int i = 0 ; i < networkDevices.size() ; i++) {
				d = (NetworkDevice) networkDevices.get(i) ;
				if (d.hostname.equals(hostname)){
					return d ;
				}
			}
			return null;
		}
		
		int timeout = 0 ;
		byte[] ip ;
		int i ;
		List networkDevices ;
		
		
		public checkAddress(int timeout, byte[] ip, int i, List networkDevices) {
			this.timeout = timeout ;
			this.ip = ip ;
			this.i = i ;
			this.networkDevices = networkDevices ;
		}


		// Description: Checks the address to see if it can be reached
		@Override
		public void run() 
		{
			{
			ip[3] = (byte)i;
			InetAddress address = null;
			try
			{
				address = InetAddress.getByAddress(ip);
//				System.out.println("Address: " + address) ;
			} catch (UnknownHostException ex)
			{
//				Logger.getLogger(MainWindowFrame.class.getName()).log(Level.SEVERE, null, ex);
			}
			try
			{
				if (address.isReachable(timeout))
				{
					System.out.println("can b pinged");
				}
				else if (!address.getHostAddress().equals(address.getHostName()))
				{
					NetworkDevice hostname = hostnameExists(address.getHostName());
					NetworkDevice hostAddress = addressExists(address.getHostAddress());

					if (hostname == hostAddress && hostname != null && hostAddress != null) {  // They will be null if they do not exist in the list
						hostname.lastSeen = new Date();
					}
					else if (hostname == null && hostAddress == null) {
						NetworkDevice d = new NetworkDevice() ;
						d.hostname = address.getHostName() ;
						d.dateResolved = new Date() ;
						d.lastSeen = new Date() ;
						d.ip = address.getHostAddress() ;
						d.address = address.getHostAddress() ;
						networkDevices.add(d);

						PortScanner p = new PortScanner(d.address,timeout,d); 
						Thread t = new Thread(p) ; 
						t.start();

						System.out.println("Device " + d.hostname + " @ " + d.address + " added to array") ;
						
					}
				}
				else {
//					System.out.println("Host name for " + address + " could not be resolved") ;
				}
			} catch (IOException ex)
			{
//				Logger.getLogger(MainWindowFrame.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		}
		
		// Basic port scanning. Implement more advanced version in next iteration. http://nmap.org/nmap_doc.html
		private class PortScanner implements Runnable{  
			String address = "" ; 
			int timeout = 0 ; 
			NetworkDevice d ; 
			public PortScanner(String address, int timeout, NetworkDevice d){ 
				this.address = address; 
				this.timeout = timeout ; 
				this.d = d ; 
			}

			@Override
			public void run()
			{
				timeout = 10 ; 
			//	System.out.println("Port scan initiated on address: " + address) ; 
					for (int i = 0 ; i < 65536; i++) { 
						//System.out.println("Trying Port Scan on " + address + ":" + i) ; 
						try (Socket socket = new Socket())
						{
							socket.connect(new InetSocketAddress(address, i), timeout); 
							System.out.println("Adding port " + i + " to address " + address) ; 
							boolean exists = false ; 

							for (int j = 0 ;j < d.openPorts.size(); j++) {  // For some reason "contains(...)" function not working
								int val = (int) d.openPorts.get(j); 
								if (val == i) { 
									exists = true ;
									break ; 
								} 
							}
							if (exists == false) { 
								d.openPorts.add(i); // Cross-threading error happening here. 
								System.out.println("Found open port on " + address + "adding port: " + i) ; 
							} 
						}
						catch (IOException ex) { } 
					}
			}



			
		}




	}
	
	
}
