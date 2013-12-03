/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cyvis;

import com.sun.corba.se.impl.orbutil.concurrent.Mutex;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author HOME
 */
public class NetworkDevice
{
	public String hostname ; 
	public String address ; 
	public Date dateResolved ;
	public Date lastSeen ; 
	public String ip ; 
	List openPorts = null ; 
	public boolean reachable ; 
	public List info ; 
	public Mutex mutex ; 
	public String mac_addr = "" ;  
	public String manufacturer = "" ;  ; 
	public String latency  = "" ; 

	public NetworkDevice() { 
		openPorts = new ArrayList<>() ; 
		info = new ArrayList(); 
		mutex = new Mutex() ; 
	}

	public void initMembers(String hostname, String address, Date date, String ip) { 
		this.hostname = hostname ; 
		this.address = address ; 
		this.dateResolved = date ; 
		this.ip = ip ; 
	}
	public void setHostname(String hostname) { 
		this.hostname = hostname ; 
	}
	public void setAddress(String address) { 
		this.address = address ; 
	}
	public void setDateResolved(Date dateResolved) { 
		this.dateResolved = dateResolved ; 
	}
	public void setByteIp(String ip) { 
		this.ip = ip ; 
	}
}
