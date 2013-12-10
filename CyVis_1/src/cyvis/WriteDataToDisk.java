/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cyvis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
 
/**
 *
 * @author HOME
 */
public class WriteDataToDisk
{

	private void doTestPrint() {
		String bigfile = "" ; 
		File f = new File("C:\\Users\\shop.NAVUSINC\\Downloads\\drunken-ninja22\\drunken-ninja22\\CyVis_1\\src\\cyvis\\html\\nodes_links.json") ; 
	//	File f = new File("C:\\Users\\HOME\\Documents\\NetBeansProjects\\drunken-ninja22\\CyVis_1\\src\\cyvis\\html\\nodes_links.json") ; 
		BufferedWriter bw = null ; 
		
		if (!f.exists()) { 
			try { 
				f.createNewFile() ;
			} catch (IOException ex) {
				Logger.getLogger(WriteDataToDisk.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		FileWriter file = null ; 
			try {
					file = new FileWriter(f.getAbsoluteFile()) ; 
//			FileWriter file = new FileWriter("c:\\nodes_links.json"); 
				} catch (IOException ex) {
					Logger.getLogger(WriteDataToDisk.class.getName()).log(Level.SEVERE, null, ex);
				}
			bw = new BufferedWriter(file) ; 

  	String header = "" ; 
		header += "{" + "\n" + 
					"\t\"nodes\":[" + "\n" ; 
//		bw.write(header);
		bigfile += header ; 
			System.err.println("oops") ; 
			for (int i= 0 ; i < nodes.size() ; i++) { 
				node n = (node) nodes.get(i);
				
				JSONObject wt = new JSONObject() ;
				String s = "" ;
				int num_ports = 0 ; 
				for (int j = 0  ; j < n.portlist.size() ; j++) { 
					s += n.portlist.get(i) + "\n" ;
					System.out.println(s) ; 
				}

				num_ports = n.portlist.size() ; 
				System.out.println("For " + n.address + " we have " + num_ports + " ports open." ) ; 

				s = "\n" ; 
				for (int j = 0 ; j < n.sp.size() ; j++) { 
					s += n.sp.get(i) ; 
				}
				

				wt.put("OS", n.service_info) ;
//				wt.put("Services", "") ; 
				wt.put("Ports", s) ;
				wt.put("Open_Ports" , String.valueOf(num_ports)) ; 
				wt.put("Address", n.address) ;
					
//			try {
//				bw.write(wt.toJSONString());
				bigfile += wt.toJSONString(); 
//				bw.write("\n") ; 
				bigfile += "\n" ; 
				if (i < nodes.size() - 1) { 
//					bw.write(",") ; 
					bigfile += "," ; 
//				bw.write(tt);
				}
//			} catch (IOException ex) {
//				Logger.getLogger(WriteDataToDisk.class.getName()).log(Level.SEVERE, null, ex);
//			}
				
			}
//			try { 
//			bw.write("],\"links\":[\n") ; 
			bigfile += "],\"links\":[\n" ; 

//			} catch (IOException ex) { 
//				System.err.println("oops2") ; 
//			}

			for (int i = 0 ; i < nodes.size() ; i++)  { 
				node n = (node) nodes.get(i); 
					if (i != 0) { 
//					bw.write("\t{\"source\":0, \"target\":" + (i) + ", \"vulnerability\":30}");
					bigfile += "\t{\"source\":0, \"target\":" + (i) + ", \"vulnerability\":30}" ; 
					if (i != nodes.size() -1) { 
					//	bw.write(",\n"); 
						bigfile += ",\n"; 
					}
					}
			}

//				bw.write("\n  ]\n}");
				bigfile += "\n  ]\n}" ; 



			
			try {
				bw.write(bigfile) ; 
				bw.flush();
   			bw.close();
		} catch (IOException ex) {
			Logger.getLogger(WriteDataToDisk.class.getName()).log(Level.SEVERE, null, ex);
		}
	}


	private class node { 
		JSONArray portlist = null ;  
		public String address = "" ;  
		public String service_info = "" ;  
		public List sp = null ; 
		public node() { 
			portlist = new JSONArray() ; 
			sp = new ArrayList() ; 
		}
		
	}

	NetworkingInfo network ; 
	List nodes = new ArrayList(); 
  JSONObject node = new JSONObject();

	public WriteDataToDisk(NetworkingInfo ni) { 
		this.network = ni ; 
		node n = null ;  
		
		for (int i = 0 ; i < network.networkDevices.size() ; i++) { 
			NetworkDevice d = (NetworkDevice) network.networkDevices.get(i); 
			n = new node() ; 
			n.address = d.address ; 

			System.out.println("Printing info for : " + d.address) ; 
			int can_continue = 0 ; 
			System.out.println("Size: " + d.info.size()) ; 

			for (int j = 0 ; j < d.info.size() ; j++) { 
				String line = (String) d.info.get(j); 

				if (line.contains("Service Info:")) { 
					n.service_info = line ; 
				}

				if (can_continue == 1) {  // Add the line if we're in the right section
					if (line.contains("MAC Address") || line.contains("MAC")) { 
						System.out.println("Line contains MAC"); 
						can_continue = 0 ; 
					}
					n.sp.add(line) ; 
					n.portlist.add(line) ; 
				}
				
				if (line.contains("STATE")) { 
					System.out.println("Line contains STATE") ; 
					can_continue = 1 ; 
				}


			} // End iterating through each of the devices info List<>
			nodes.add(n) ; 
		} // End for iterating through network devices

		doTestPrint() ; 
	} // End Constructor function

	void doAction()
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.


		// Given the information contained in NetworkingInfo, i.e.
			// Each device in networkingInfo.networkDevices, create a JSON file that represents said network
			// JSON file format has been specified by front-end UI team. 
	}

	
	
}