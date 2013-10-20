/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cyvis;
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
	ArrayList<NetworkInterface> interfaces = new ArrayList<>(); 
	public NetworkInterface activeInterface ; 
	List networkDevices = null ; 

	public NetworkingInfo() { 
		networkDevices = new ArrayList<>() ; 
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
	public NetworkInterface setDefaultInterface(String name) 
	{ 
		for (NetworkInterface ni : interfaces) 
		{ 
			if (ni.getDisplayName().equals(name)) { 
				activeInterface = ni; 
				out.print("Setting " + ni.getDisplayName() + " to default connection\n") ; 
				return ni ; 
			}
		}	
	   	return null;
	}
	

	

}
