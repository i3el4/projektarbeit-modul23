package middleearth.map.model;

import java.io.File;
import java.util.Properties;

public class Prop_Paths extends Props {

	/*
	 * ATTRIBUTES 
	 * ==========
	 */
	private        Properties props;
	private static String     inputFile = "ini" + File.separator + 
            							  "Paths.txt";
	
	
	/*
	 * CONSTRUCTOR 
	 * ===========
	 */
	public Prop_Paths( Properties props ) {
		
		super( props );
		this.props = this.getProps( inputFile );
	}
	
	
	/*
	 * SETTERS 
	 * =======
	 */
	
	
	// Writing name of path representing localhost. 
	public void setLocalhost( String namePathLocalhost ) {
		
		// Setting path's name. 
		this.props.setProperty( "pathLocalhost", 
								namePathLocalhost );
	}	
	
	// Writing name of path representing dbCtrl. 
	public void setDbCtrl( String namePathDbCtrl ) {
		
		// Setting path's name. 
		this.props.setProperty( "pathDbCtrl", 
								namePathDbCtrl );
	}

	
	/*
	 * GETTERS 
	 * =======
	 */

	// Reading name of path representing localhost. 
	public String getLocalhost() {

		return this.props.getProperty( "pathLocalhost" );
	}	

	// Reading name of path representing dbCtrl. 
	public String getDbCtrl() {

		return this.props.getProperty( "pathDbCtrl" );
	}	

		
	/*
	 * OTHERS 
	 * ======
	 */	
	
	// Writing the properties-class in a txt-file. 
	public void save() {

		// Call from super...
		this.saveProps( inputFile );
	}	
}
