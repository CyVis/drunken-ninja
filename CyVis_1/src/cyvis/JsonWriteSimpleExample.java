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
 * notice there is a new json-simple.jar file in the Libraries section of the project.
 */
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
 
public class JsonWriteSimpleExample {
     public static void main(String[] args) {
 
	JSONObject obj = new JSONObject();
	obj.put("address", "192.168.1.0");
        obj.put("Open_Ports", new Integer(3));
        obj.put("Ports", "p1 | p2 | p3");
        obj.put("Services", "s1 | s2 | s3");
        obj.put("OS", "Macintosh");
        
        JSONArray portlist = new JSONArray();
	portlist.add("p1");
	portlist.add("p2");
	portlist.add("p3");
        
        JSONArray servicelist = new JSONArray();
	servicelist.add("s1");
	servicelist.add("s2");
	servicelist.add("s3");
        

 
        obj.put("Ports", portlist);
	obj.put("Services", servicelist);
        
 
	try {
 
		//FileWriter file = new FileWriter("c:\\nodes_links.json"); //for windows
                FileWriter file = new FileWriter("/Users/jrs/nodes_links.json"); //for mac
		file.write(obj.toJSONString());
		file.flush();
		file.close();
 
	} catch (IOException e) {
		e.printStackTrace();
	}
 
	System.out.print(obj);
 
     }
 
}


/**
 * {
  "nodes":[
	{ "Address":"192.168.1.0",
	  "Open_Ports":3,
	  "Ports":"1 | 2 | 3",
	  "Services":"s1 | s2 | s3",
	  "OS":"macintosh"
	},
	{ "Address":"192.168.1.1",
	  "Open_Ports":3,
	  "Ports":"1 | 2 | 3",
	  "Services":"s1 | s2 | s3",
	  "OS":"macintosh"
	},
	{ "Address":"192.168.1.2",
	  "Open_Ports":3,
	  "Ports":"1 | 2 | 3",
	  "Services":"s1 | s2 | s3",
	  "OS":"linux"
	},
	{ "Address":"192.168.1.3",
	  "Open_Ports":3,
	  "Ports":"1 | 2 | 3",
	  "Services":"s1 | s2 | s3",
	  "OS":"linux"
	},
	{ "Address":"192.168.1.4",
	  "Open_Ports":3,
	  "Ports":"1 | 2 | 3",
	  "Services":"s1 | s2 | s3",
	  "OS":"linux"
	},
	{ "Address":"192.168.1.5",
	  "Open_Ports":3,
	  "Ports":"1 | 2 | 3",
	  "Services":"s1 | s2 | s3",
	  "OS":"windows"
	},
	{ "Address":"192.168.1.6",
	  "Open_Ports":3,
	  "Ports":"1 | 2 | 3",
	  "Services":"s1 | s2 | s3",
	  "OS":"macintosh"
	},
	{ "Address":"192.168.1.7",
	  "Open_Ports":3,
	  "Ports":"1 | 2 | 3",
	  "Services":"s1 | s2 | s3",
	  "OS":"linux"
	},
	{ "Address":"192.168.1.8",
	  "Open_Ports":3,
	  "Ports":"1 | 2 | 3",
	  "Services":"s1 | s2 | s3",
	  "OS":"windows"
	},
	{ "Address":"192.168.1.9",
	  "Open_Ports":3,
	  "Ports":"1 | 2 | 3",
	  "Services":"s1 | s2 | s3",
	  "OS":"windows"
	},
	{ "Address":"192.168.1.10",
	  "Open_Ports":3,
	  "Ports":"1 | 2 | 3",
	  "Services":"s1 | s2 | s3",
	  "OS":"windows"
	},
	{ "Address":"192.168.1.11",
	  "Open_Ports":3,
	  "Ports":"1 | 2 | 3",
	  "Services":"s1 | s2 | s3",
	  "OS":"windows"
	},
	{ "Address":"192.168.1.12",
	  "Open_Ports":3,
	  "Ports":"1 | 2 | 3",
	  "Services":"s1 | s2 | s3",
	  "OS":"windows"
	},
	{ "Address":"192.168.1.13",
	  "Open_Ports":3,
	  "Ports":"1 | 2 | 3",
	  "Services":"s1 | s2 | s3",
	  "OS":"windows"
	},
	{ "Address":"192.168.1.14",
	  "Open_Ports":3,
	  "Ports":"1 | 2 | 3",
	  "Services":"s1 | s2 | s3",
	  "OS":"linux"
	},
	{ "Address":"192.168.1.15",
	  "Open_Ports":3,
	  "Ports":"1 | 2 | 3",
	  "Services":"s1 | s2 | s3",
	  "OS":"macintosh"
	},
	{ "Address":"192.168.1.16",
	  "Open_Ports":3,
	  "Ports":"1 | 2 | 3",
	  "Services":"s1 | s2 | s3",
	  "OS":"linux"
	},
	{ "Address":"192.168.1.17",
	  "Open_Ports":3,
	  "Ports":"1 | 2 | 3",
	  "Services":"s1 | s2 | s3",
	  "OS":"linux"
	},
	{ "Address":"192.168.1.18",
	  "Open_Ports":3,
	  "Ports":"1 | 2 | 3",
	  "Services":"s1 | s2 | s3",
	  "OS":"macintosh"
	}	

  ],
  "links":[
    {"source":1,"target":0,"vulnerability":30},
	{"source":2,"target":0,"vulnerability":30},
	{"source":3,"target":1,"vulnerability":30},
	{"source":4,"target":1,"vulnerability":30},
	{"source":5,"target":3,"vulnerability":30},
	{"source":6,"target":0,"vulnerability":30},
	{"source":7,"target":2,"vulnerability":30},
	{"source":8,"target":7,"vulnerability":30},
	{"source":9,"target":7,"vulnerability":30},
	{"source":10,"target":7,"vulnerability":30},
	{"source":11,"target":4,"vulnerability":30},
	{"source":12,"target":3,"vulnerability":30},
	{"source":13,"target":0,"vulnerability":30},
	{"source":14,"target":13,"vulnerability":30},
	{"source":15,"target":8,"vulnerability":30},
	{"source":16,"target":0,"vulnerability":30},
	{"source":17,"target":1,"vulnerability":30},
	{"source":18,"target":1,"vulnerability":30}
  ]
}

 */