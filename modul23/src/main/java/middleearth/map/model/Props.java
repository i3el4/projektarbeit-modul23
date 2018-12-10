package middleearth.map.model;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

/**
 *  Superclass extended by different classes reading Properties-classes initial keys 
 *  and values from a txt-file.
 *  
 *  @author Christoph Ackermann
 *   
 */

public class Props {

	/*
	 * ATTRIBUTES 
	 * ==========
	 */
	private Properties props = null;

	public static final Prop_Texts     	TEXTS     = new Prop_Texts( null );
	
	public static final Prop_SuggList  	SUGG_LIST = new Prop_SuggList( null );
	
	public static final Prop_Paths 		PATHS = new Prop_Paths( null );

	/*
	 * CONSTRUCTOR 
	 * ===========
	 */
	public Props( Properties props ) {
		
		this.props = new Properties( props );
	}

	
	
	/*
	 * GETTERS 
	 * =======
	 */
	
	// Fills the property class with values defined in a txt-file.
	public final Properties getProps( String inputFile ) {
		
		// Check if new property and file defined;
		if( this.props.size() == 0 ) {
			
			// Read input from a file.
			Reader txtInputFile = null;
	
			try {
	
				// Reading keys and values from a file.
				txtInputFile = new FileReader( inputFile );
				this.props.load( txtInputFile );
	
			} catch( IOException e ) {
	
				// Throwing an error-message.
				e.printStackTrace();
	
			} finally {

				// Check if the file has to be closed. 
				if ( txtInputFile != null ) {

					try { 

						// Close the file.
						txtInputFile.close();

					} catch ( Exception e ) {}
				}
			}
		}
		
		return this.props;
	}
	
	
	/*
	 * OTHERS 
	 * ======
	 */	
	
	// Deletes all elements of a properties class.
	public void deleteAll() {
		
		// Check if not empty
		if ( this.props.size() > 0 ) {

			// HashMap for all keys.
			Set<String> keys = this.props.stringPropertyNames();
			
			// Iteration over elements.
			for ( String key : keys ) {

				// Delete the element.
				this.props.remove(key);
			}
		}
	}

	
	// Writing the properties-class as a unsorted TXT-file. 
	public void saveProps( String outputFile ) {
		
    	Writer txtOutputFile = null;
		
		try {
			
			// Writing the values of the properties-class.
			txtOutputFile = new FileWriter( outputFile );
			
			this.props.store( txtOutputFile, outputFile ); 
			
		} catch ( IOException e ) {

			e.printStackTrace();
		
		} finally {

			// Check if the file has to be closed. 
			if ( txtOutputFile != null ) {

				try { 

					// Close the file.
					txtOutputFile.close();

				} catch ( Exception e ) {}
			}
		}
	}

	
	// Converting the properties-class in a tree-map and writing 
	// the map as a sorted TXT-file. 
	public void saveSortedProps( String outputFile ) {
		
    	Writer txtOutputFile = null;
		
		try {
			
			// Defining writers and a hash-map.
			FileWriter          fw  = new FileWriter( outputFile );
			BufferedWriter      bw  = new BufferedWriter( fw );
			Map<String, String> map = new HashMap<String, String>();
			
			// Iteration over properties-class's entries.
			for ( final String name : this.props.stringPropertyNames() ) {
			
				// Writing a properties-class's entry in the map.
				map.put( name, this.props.getProperty( name ) );
			}
			
			// Transferring the unsorted map in a tree-map.
			TreeMap<String, String> treeMap = new TreeMap<>( map );
			Iterator< ? >   		it2 	= treeMap.entrySet().iterator();			

			// Iteration over tree-map's entries.
			while( it2.hasNext() ) {
				
				// Defining a tree-map's row as a hash-map's entry.
				HashMap.Entry<?, ?> rowData = ( Entry<?, ?> ) it2.next();
				
				// Writing it as a TXT-file's line.
				bw.write( rowData.toString() );
				bw.newLine();
			}
			
			// Close the writers.
			bw.flush();
			bw.close();
			fw.close();
			
		} catch ( IOException e ) {

			e.printStackTrace();
		
		} finally {

			// Check if the file has to be closed. 
			if ( txtOutputFile != null ) {

				try { 

					// Close the TXT-file.
					txtOutputFile.close();

				} catch ( Exception e ) {}
			}
		}
	}
}
