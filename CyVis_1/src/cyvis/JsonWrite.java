
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cyvis;

/**
 *
 * @author jrs
 * Ref: http://www.mkyong.com/java/json-simple-example-read-and-write-json/
 * see https://code.google.com/p/json-simple/
 * json-simple.jar file needs to be imported into the Libraries section of the project.
 */
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
 
public class JsonWrite {
     public static void main(String[] args) {
 
        Integer y = 3; 
         
        JSONArray portlist = new JSONArray();
	portlist.add("p1");
	portlist.add("p2");
	portlist.add("p3");
        
        JSONArray servicelist = new JSONArray();
	servicelist.add("s1");
	servicelist.add("s2");
	servicelist.add("s3"); 
         
        JSONObject node = new JSONObject();
	node.put("IP", "192.168.1.0");
        node.put("OS", "Macintosh"); 
        node.put("Open_Ports:", (y));
        node.put("Port", portlist);
        node.put("Service", servicelist);
        
 
	try {
 		//FileWriter file = new FileWriter("c:\\nodes_links.json"); //for windows
                FileWriter file = new FileWriter("/Users/jrs/nodes_links.json"); //for mac
		file.write(node.toJSONString());
		file.flush();
		file.close();
 
	} catch (IOException e) {
		e.printStackTrace();
	}
 
	System.out.print(node);
 
     }
 
}

