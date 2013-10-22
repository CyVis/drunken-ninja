/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cyvis;
//test
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

	public NetworkDevice() { 
		openPorts = new ArrayList<>() ; 
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
