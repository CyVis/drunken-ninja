/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cyvis;

import java.util.Date;

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
