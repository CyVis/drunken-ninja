/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cyvis;

public class CyVis {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
	    setupMainWindow() ; 
    }

	
    public static void setupMainWindow() {  
	    MainWindowFrame mwf ;
	    mwf = new MainWindowFrame();
	    mwf.setLocationRelativeTo(null) ; // Center window in screen
	    mwf.pack();
	    mwf.setVisible(true) ;
    }  // Main window decor, display window
}
