package middleearth.map.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Decision.
 *
 * @author 	Bela Ackermann 
 * @version 	2018-10-18
 */
public class MapEntry {

    private final StringProperty mapEntry;
    private final IntegerProperty number;

    /**
     * Constructor with some initial data.
     * 
     * @param number
     * @param mapEntry
     */
    public MapEntry(int number, String mapEntry) {
        this.number = new SimpleIntegerProperty(number);
        this.mapEntry = new SimpleStringProperty(mapEntry);
    }
    
    /**
     * Default constructor.
     */
    public MapEntry() {
    	// invoke constructor that does assign the properties
        this(0, null);
    }
    
    public String getMapEntry() {
        return mapEntry.get();
    }

    public void setMapEntry(String mapEntry) {
        this.mapEntry.set(mapEntry);
    }
    
    public StringProperty mapEntryProperty() {
        return mapEntry;
    }
    

    public int getNumber() {
        return number.get();
    }

    public void setNumber(int number) {
        this.number.set(number);
    }
    
    public IntegerProperty nummerProperty() {
        return number;
    }
}
