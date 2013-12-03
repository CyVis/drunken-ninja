/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cyvis;
import com.sun.corba.se.impl.orbutil.concurrent.Mutex;
import static java.lang.System.out ; 
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 *
 * @author HOME
 */
public class NetworkingInfo
{
	public String baseIP = "192.168.1" ; 
	public String ipv4_address, ipv6_address ; 
	public String hostname; 
	public boolean use_ipv4 = true ; 
	public boolean use_ipv6 = false ; 
	ArrayList<NetworkInterface> interfaces = new ArrayList<>(); 
	public NetworkInterface activeInterface ; 
	List networkDevices = null ;
	public Mutex mutex ; 
	public String[] scanningRange ; 

	public NetworkingInfo() { 
		this.mutex = new Mutex();
		networkDevices = new ArrayList<>() ; 
	}
	public void setIPV4Active() { 
		use_ipv4 = true ; 
		use_ipv6 = false ; 
	}
	public void setIPV6Active() { 
		use_ipv4 = false ; 
		use_ipv6 = true ; 
	}
	


	public void addNetworkInterface(NetworkInterface ni) { 
		interfaces.add(ni) ; 
	}

	public void printInterfaces(boolean printAddresses, boolean printAll) { 
		for (NetworkInterface ni : interfaces) 
		{ 
		out.printf("Display name: %s\n", ni.getDisplayName());
		out.printf("Name: %s\n", ni.getName());	
		if (printAddresses) printInitAddresses(ni, printAll) ; 
		}
	}
	public void printInitAddresses(NetworkInterface ni, boolean printAll) { 
	
		Enumeration<InetAddress> inetAddresses = ni.getInetAddresses(); 
		for (InetAddress inetAddress : Collections.list(inetAddresses)) { 
			String s = inetAddress.toString() ; 
			String s2 ; 
			s2 = s.replace("/", "") ; 
			out.printf("InetAddress: [%s]\n", s2) ; 

			if (printAll == false) break ;  // In this case only the very fist InetAddress is the one we want. Others are maybe ipv6 or something weird. 
		}
		out.printf("\n") ; 
	}
	public NetworkInterface setDefaultInterface(String name) // Only works for IPv4
	{ 
		for (NetworkInterface ni : interfaces) 
		{ 
			
			if (ni.getDisplayName().equals(name)) { 
				activeInterface = ni; 
				// Need to parse interface to get correct address - since it has many 
				Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();  // For said interface, get all addresses

				int count = 0 ; 
				for (InetAddress inetAddress : Collections.list(inetAddresses)) { 
					String s = inetAddress.getHostAddress(); 
					System.out.println("[" + s.toString() + "]");
					if (count == 0) ipv4_address = s ; 
					if (count == 1) ipv6_address = s ; 
					count++ ; 
				}

				System.out.println("Setting " + ni.getDisplayName() + " to active device.\n" + "\tIPV4: " + ipv4_address + "\n\tIPV6: " + ipv6_address) ; 

				String strip = "" ; 
				int num_dots = 0 ; 
				for (int i = 0 ; i < ipv4_address.length() ; i++) { 
					if (ipv4_address.charAt(i) == '.') { 
						num_dots++ ; 
					}

					if (num_dots < 3) { 
						strip += ipv4_address.charAt(i) ; 
					}
				}
				baseIP = strip ; 
				System.out.println("New baseIP: " + baseIP) ; 
				
				out.print("Setting " + ni.getDisplayName() + " to default connection\n") ; 
				return ni ; 
			}
		}	
	   	return null;
	}
	

	

}
