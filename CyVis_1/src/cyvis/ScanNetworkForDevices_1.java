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
import org.omg.SendingContext.RunTime;

/**
 *
 * @author HOME
 */
public class ScanNetworkForDevices_1 implements Runnable
{
	List networkDevices ;
	NetworkingInfo networkingInfo ; 
	public boolean ipv6_compatability = false ; 
	public int[] scanStartRange = new int[4] ; 
	public int [] scanEndRange = new int[4] ; 
	
	
//	ScanNetworkForDevices(List networkDevices)
	//ScanNetworkForDevices(NetworkingInfo ni, int[] ssR, int[] seR) 
	ScanNetworkForDevices_1(NetworkingInfo ni)
	{
		this.networkingInfo = ni ; 
		this.networkDevices = networkingInfo.networkDevices ; // networkDevices is initialized in the MainWindowFrame class
		for (String s : networkingInfo.scanningRange) { 
			System.out.println(s) ; 
		}
		scanStartRange[0] = Integer.parseInt(networkingInfo.scanningRange[0]) ; // 192 . --> a
		scanStartRange[1] = Integer.parseInt(networkingInfo.scanningRange[1]) ; // 168 . --> b
		scanStartRange[2] = Integer.parseInt(networkingInfo.scanningRange[2]) ; // 1 . --> c
		scanStartRange[3] = Integer.parseInt(networkingInfo.scanningRange[3]) ; // 1 . --> d
		scanEndRange[0] = Integer.parseInt(networkingInfo.scanningRange[4]) ; // 192 . --> e 
		scanEndRange[1] = Integer.parseInt(networkingInfo.scanningRange[5]) ; // 168 . --> f
		scanEndRange[2] = Integer.parseInt(networkingInfo.scanningRange[6]) ; // 2 . --> g
		scanEndRange[3] = Integer.parseInt(networkingInfo.scanningRange[7]) ; // 255 . --> h
	}
	
	// Iterates through the range of IP addresses and creates another thread to test the connection
	// This is the runnable method for the ScanNetworkDevices class, i.e.
	// THIS IS THE MAIN METHOD OF THIS CLASS
	@Override
	public void run()
	{
		boolean running = true ;
		int timeout = 100 ;
		
		while(running)  
		{
			int delay = 30 ; // Tells the thread how much to wait before calling another address. Lowering this value too much will DoS your network
			List<Thread> threads = new ArrayList<>() ;

			for (int a = scanStartRange[0]; a <= scanEndRange[0]; a++) { 
				for (int b = scanStartRange[1]; b <= scanEndRange[1]; b++) {
					for (int c = scanStartRange[2]; c <= scanEndRange[2]; c++) { 
						for (int d = scanStartRange[3]; d <= scanEndRange[3]; d++) { 
							String address = 
								String.valueOf(a) + "." + 
								String.valueOf(b) + "." + 
								String.valueOf(c) + "." + 
								String.valueOf(d) ; 
//							System.out.println("Checking address: " + address) ; 
							checkAddress ca = new checkAddress(timeout, address, networkDevices) ; 
							Thread t = new Thread(ca) ; 
							t.start(); 
							threads.add(t) ; 
							try 
							{
								Thread.sleep(2000) ;
							} catch (InterruptedException ex)
							{
								Logger.getLogger(ScanNetworkForDevices.class.getName()).log(Level.SEVERE, null, ex);
							}
							
						}
					}
				}		
			}
			timeout += timeout ;
			System.out.println("Done iterating through addresses. Attempting Thread joins") ; 
			try
			{
				for (int i = 0; i < threads.size(); i++) 
				{ 
					Thread t = threads.get(i) ;
					t.join(100);
				}
			} catch (InterruptedException ex) { }

			if (timeout >= 1000) timeout += timeout ;
			if (timeout >= 1000) 
			{ 
				running = false;
				System.out.println("Ran through full sweep. Stopping for dev. purposes") ; 
			}
			break ; 
		}
	}
	
	// Class that gets created as a thread to check to see if the address is reachable. 
	// Caller: Parent class
	// Calls: Port Scanner
	private class checkAddress implements Runnable  {
	
		String address = "" ; 
		int timeout = 0 ; 
		List networkDevices ; 
		String command = "" ; 

		public checkAddress(int timeout, String address, List networkDevices) { 
			this.timeout = timeout ;
			this.networkDevices = networkDevices ;
			this.address = address ; 
			command = "ping " + address + " -r 1" ; 
		}
	
//		Process p1 = java.lang.Runtime.getRuntime().exec(command) ; 

	 public boolean testAddress() throws InterruptedException 
	 { 
		 Process p ; 
		 Runtime r ; 
		 r = Runtime.getRuntime() ; 

		 String co2 ; 
		 co2 = "nmap -w -sP \"" + address + "\""; 

		 System.out.println(co2) ; 

		 int retval = -100 ; 

				try { 
					Runtime runtime = Runtime.getRuntime() ; 
					
					Process process = runtime.exec("nmap") ; 
				//	int exitValue = process.exitValue();
			//		System.out.println("Address: " + exitValue) ; 
				}
				catch (Exception ex) { 
					System.err.println("Exception during process creation" + ex.getMessage()) ; 
				}
			return retval == 0; 
	 } 


		// Description: Checks the address to see if it can be reached
		@Override
		public void run() 
		{
			try 
			{
				boolean b = testAddress() ;
//				if (b) System.out.println("Address: " + address) ; 
			} catch (InterruptedException ex)
			{
				Logger.getLogger(ScanNetworkForDevices.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		
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
