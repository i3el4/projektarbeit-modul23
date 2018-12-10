package middleearth.map.model;

import java.io.File;
import java.util.Properties;

public class Prop_SuggList extends Props {

		/*
		 * ATTRIBUTES 
		 * ==========
		 */
		private        Properties props;
		private static String     inputFile = "ini" + File.separator + 
	            							  "Texts.txt";
		
		
		/*
		 * CONSTRUCTOR 
		 * ===========
		 */
		public Prop_SuggList( Properties props ) {
			
			super( props );
			this.props = this.getProps( inputFile );
		}
		
		
		/*
		 * GETTERS 
		 * =======
		 */

		// German (0), English (1). 
		private String getPreKey() {
			
			return this.props.getProperty( "preKey" ).split( ";" )[0];
		}
		
		
		// German (0), English (1). 
		public String getText( String id,
							   int    langu ) {
			
			// Call ...
			String preKey = getPreKey();
			
			return this.props.getProperty( preKey + id ).split( ";" )[langu];
		}	
	}