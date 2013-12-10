/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package cyvis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.Character.isDigit;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
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
	NetworkingInfo networkingInfo ;
	public boolean ipv6_compatability = false ;
	public int[] scanStartRange = new int[4] ;
	public int [] scanEndRange = new int[4] ;
	
	
	ScanNetworkForDevices(NetworkingInfo ni)
	{
		this.networkingInfo = ni ;
		this.networkDevices = networkingInfo.networkDevices ; // networkDevices is initialized in the MainWindowFrame class
		for (String s : networkingInfo.scanningRange) {
		}
		scanStartRange[0] = Integer.parseInt(networkingInfo.scanningRange[0]) ; // 192 . --> a
		scanStartRange[1] = Integer.parseInt(networkingInfo.scanningRange[1]) ; // 168 . --> b
		scanStartRange[2] = Integer.parseInt(networkingInfo.scanningRange[2]) ; // 1 . --> c
		scanStartRange[3] = Integer.parseInt(networkingInfo.scanningRange[3]) ; // 1 . --> d
		scanEndRange[0] = Integer.parseInt(networkingInfo.scanningRange[4]) ; // 192 . --> e
		scanEndRange[1] = Integer.parseInt(networkingInfo.scanningRange[5]) ; // 168 . --> f
		scanEndRange[2] = Integer.parseInt(networkingInfo.scanningRange[6]) ; // 2 . --> g
		scanEndRange[3] = Integer.parseInt(networkingInfo.scanningRange[7]) ; // 255 . --> h
	} // Set up port scanning range
	
	
	@Override
	public void run()
	{
		boolean running = true ;
		int timeout = 100 ;
		
		while(running)
		{
			int delay = 200 ; // Tells the thread how much to wait before calling another address. Lowering this value too much will DoS your network
			List<Thread> threads = new ArrayList<>() ;
			int counter = 0 ;
			
			System.out.println("Starting iteration with timeout: " + timeout) ;
			for (int a = scanStartRange[0]; a <= scanEndRange[0]; a++) { // 192.
				for (int b = scanStartRange[1]; b <= scanEndRange[1]; b++) { // 168.
					for (int c = scanStartRange[2]; c <= scanEndRange[2]; c++) { // 2.

						String range = 
											String.valueOf(a) + "." +
											String.valueOf(b) + "." +
											String.valueOf(c) + "." + "0/24" ; 
						List devices = null ; 
						getDevices(range) ;


						for (int x = 0 ; x < networkingInfo.networkDevices.size(); x++) { 
								NetworkDevice d = (NetworkDevice) networkingInfo.networkDevices.get(x);
								System.out.println(d.address) ;


								List temp = new ArrayList() ;
								ProcessBuilder builder = new ProcessBuilder("nmap", "-sV", d.address) ; 
								builder.redirectErrorStream(true) ;

								Process bp = null ;
								try
								{
									bp = builder.start();
								} catch (IOException ex)
								{
									Logger.getLogger(ScanNetworkForDevices.class.getName()).log(Level.SEVERE, null, ex);
								}

								InputStream stdout = bp.getInputStream() ;
								String line ;
								BufferedReader reader = new BufferedReader(new InputStreamReader(stdout)) ;
								try
								{
									while ((line = reader.readLine()) != null)
									{
										temp.add(line) ;
										System.out.println("Stdout: " + line) ;
										if (line.contains("Host seems down.")) {
											System.out.println(d.address + " is down") ;
										}
										if (line.contains("Host is up")) {
											// Parse line to get latency
											// eg Host is up (0.17s latency).
											int firstOccurance = -1 ;
											int lastOccurance = -1 ;
											firstOccurance = line.indexOf('(') + 1;
											lastOccurance = line.indexOf(')') ;

											for (int i = firstOccurance; i < line.length(); i++) {
												char ch = line.charAt(i);
												if (isDigit(ch) || ch == '.') {
													// continue
												}
												else {
													lastOccurance = i ;
													break ;
												}
											}
											System.err.println("Position: " + firstOccurance + ", " + lastOccurance) ;
											if (firstOccurance != -1 && lastOccurance != -1) {
												String latency ;
												latency = line.substring(firstOccurance, lastOccurance);
												System.out.println("Latency: " + latency) ;
											}
										}
									} // END while
									reader.close();
									stdout.close();
									try
									{
										System.out.println("acquiring") ; 
										d.mutex.acquire();
										d.info = temp ;
										d.mutex.release() ;
										System.out.println("Releasing") ; 
									} catch (InterruptedException ex)
									{
										Logger.getLogger(ScanNetworkForDevices.class.getName()).log(Level.SEVERE, null, ex);
									}


								} catch (IOException ex)
								{
									Logger.getLogger(ScanNetworkForDevices.class.getName()).log(Level.SEVERE, null, ex);
								}
								int r3 = bp.exitValue() ;
								System.out.println(r3) ;
								bp.destroy();
					}
				} // c
			} // b
		} // a
			break ;
	}
	}
	
	
	private void getDevices(String range)
	{
		String address ;
		System.out.println(range) ; 
		NetworkDevice nd = null ; 	
		
		ProcessBuilder builder = new ProcessBuilder("nmap", "-sP", range) ; 
		builder.redirectErrorStream(true) ;
		
		Process bp = null ;
		try
		{
			bp = builder.start();
		} catch (IOException ex)
		{
			Logger.getLogger(ScanNetworkForDevices.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		InputStream stdout = bp.getInputStream() ;
		String line ;
		BufferedReader reader = new BufferedReader(new InputStreamReader(stdout)) ;
		try
		{
			String name = "" ; 
			String ipadr = "" ; 
			String t = "" ; 
			NetworkDevice d = null ; 
			int onset = -1 ; 
			Boolean db = false ; 


			while ((line = reader.readLine()) != null)
			{
				if (line.contains("Nmap scan report for")) { 
						t = line.substring(21) ; 
						try 
						{ 
								int p1 = t.indexOf('(') ; 
								int p2 = t.indexOf(')') ; 
								name = t.substring(0, p1-1) ; 
						    ipadr = t.substring(p1+1, p2) ; 
						} catch (StringIndexOutOfBoundsException ex) { 
								ipadr = t ; 
						}
						d = new NetworkDevice() ; 
						d.hostname = name ; 
						d.address = ipadr ; 
						onset = 1 ; 
						System.out.println("1") ; 
				}
						
				System.out.println("2->3") ; 

				if (line.contains("MAC Address:")) { 
					String device = "" ; 
					String mac = "" ; 
					try 
					{ 
						t = line.substring(13) ; 
						int p1 = t.indexOf('(') ; 
						int p2 = t.indexOf(')') ; 
						mac = t.substring(0, p1-1) ; 
						device = t.substring(p1+1, p2) ; 
						d.manufacturer = device ; 
						d.mac_addr = mac ; 
					} catch (Exception ex) { } 

						System.out.println("3!!") ; 
						onset = 3 ; 
				}


				if (!line.contains("MAC Address:") && onset == 2) { 
					System.out.println("HEY") ; 
					onset = 3 ; 
				}

				
				System.out.println("1->2") ; 
				if (line.contains("Host is up")) { 
					try 
					{ 
						t = line.substring(12) ; 
						int p1 = t.indexOf('s') ; 
						int p2 = t.indexOf(')') ; 
						String latency = "" ; 
						latency = t.substring(0, p1) ; 
						d.latency = latency ; 
					} catch (Exception ex) { 

					}
					onset = 2 ; 
				  System.out.println("2") ; 
				}
	
				if (onset == 3) { 
					networkingInfo.networkDevices.add(d); 
					System.out.println(onset + " adding") ; 
					onset = 0 ; 
				}

				if (line.contains("Nmap done")) { 
					reader.close();
					stdout.close();
					return ; 
				}
			} // End While
		} catch (Exception ex) { } 


			
}
	
}
