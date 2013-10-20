/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package cyvis;

import java.io.IOException;
import java.net.InetAddress;
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
public class ScanNetworkForDevices implements Runnable
{
	List networkDevices ;
	
	ScanNetworkForDevices(List networkDevices)
	{
		this.networkDevices = networkDevices ; // networkDevices is initialized in the MainWindowFrame class
	}
	
	
	
	
	
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
		
		@Override
		public void run()
		{
//			for (inti = 0; i < 255; i++)
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
					//System.out.println("Name is......"+address.getHostName()+"\tIP is......."+address.getHostAddress() + " w/ timeout " + timeout);
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
		
	}
	
	
	
	public void run()
	{
		boolean running = true ;
		
		while(running)  {
			int timeout = 100 ;
			List<Thread> threads = new ArrayList<>() ;
			
			InetAddress localhost ;
			localhost = null;
			try
			{
				localhost = InetAddress.getLocalHost() ;
			} catch (UnknownHostException ex)
			{
//				Logger.getLogger(MainWindowFrame.class.getName()).log(Level.SEVERE, null, ex);
			}
			
			
			byte[] ip = localhost.getAddress();
			for (int i = 0; i < 255; i++) {
//				System.out.println("Checking on subaddress: " + i + " with timeout: " + timeout)  ;
				checkAddress ca = new checkAddress(timeout, ip, i, networkDevices);
				Thread t = new Thread(ca) ;
				t.start() ;
				threads.add(t);
				
				try
				{
					Thread.sleep(500);
				} catch (InterruptedException ex)
				{
//					Logger.getLogger(ScanNetworkForDevices.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			
			try
			{
				for (int i = 0; i < threads.size(); i++) {
					Thread t = threads.get(i) ;
					t.join(100);
				}
			} catch (InterruptedException ex)
			{
//				Logger.getLogger(ScanNetworkForDevices.class.getName()).log(Level.SEVERE, null, ex);
			}
			
			timeout += timeout ;
//			if (timeout >= 1000) timeout = 10 ;
			if (timeout >= 1000) running = false;
		}
	}
}
