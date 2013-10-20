/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package cyvis;

import java.io.IOException;
import static java.lang.System.out;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HOME
 */
public class Scanning 
{
	NetworkingInfo networkingInfo ; 
	String localhost ; 
	public Scanning(NetworkingInfo ni) { 
		networkingInfo = ni ; 
	}

	public void getNetInterfacesAndScan() {
		try 
		{ 
			Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
			for (NetworkInterface netint : Collections.list(nets))
			addInterfaceInformation(netint);
		} catch (SocketException ex) 
		{ 
			System.err.print("Exception caught: " + ex.getMessage() + ", " + ex.toString()) ; 
		}
	} // Iterates through the network interfaces and calls displayNetworkINformation(...)
	
	public void addInterfaceInformation(NetworkInterface netint) throws SocketException {
		networkingInfo.addNetworkInterface(netint);
	}

	
}
