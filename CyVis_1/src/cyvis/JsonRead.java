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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
 
public class JsonRead {
     public static void main(String[] args) {
 
	JSONParser parser = new JSONParser();
 
	try {
 
		//Object obj = parser.parse(new FileReader("c:\\test.json")); //for windows
                Object obj = parser.parse(new FileReader("/Users/jrs/nodes_links.json")); //for mac
 
		JSONObject jsonObject = (JSONObject) obj;
 
		String address = (String) jsonObject.get("address");
                System.out.print("IP: ");
		System.out.println(address);
                
                String os = (String) jsonObject.get("OS");
                System.out.print("OS: ");
		System.out.println(os);
 
		long Open_Ports = (Long) jsonObject.get("Open_Ports");
                System.out.print("Open Ports: ");
		System.out.println(Open_Ports);
                                
		// loop array
		JSONArray ports = (JSONArray) jsonObject.get("Ports");
		Iterator<String> portiterator = ports.iterator();
		while (portiterator.hasNext()) {
			System.out.println(portiterator.next());
		}
                
                System.out.println("Services: ");
                
                JSONArray services = (JSONArray) jsonObject.get("Services");
		Iterator<String> serviceiterator = services.iterator();
		while (serviceiterator.hasNext()) {
			System.out.println(serviceiterator.next());
		}
 
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ParseException e) {
		e.printStackTrace();
	}
 
     }
 
}
