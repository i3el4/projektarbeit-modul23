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

    private final StringProperty entscheidung;
    private final IntegerProperty nummer;

    /**
     * Constructor with some initial data.
     * 
     * @param nummer
     * @param entscheidung
     */
    public MapEntry(int nummer, String entscheidung) {
        this.nummer = new SimpleIntegerProperty(nummer);
        this.entscheidung = new SimpleStringProperty(entscheidung);
    }
    
    /**
     * Default constructor.
     */
    public MapEntry() {
    	// invoke constructor that does assign the properties
        this(0, null);
    }
    
    public String getEntscheidung() {
        return entscheidung.get();
    }

    public void setEntscheidung(String entscheidung) {
        this.entscheidung.set(entscheidung);
    }
    
    public StringProperty entscheidungProperty() {
        return entscheidung;
    }
    

    public int getNumber() {
        return nummer.get();
    }

    public void setNumber(int number) {
        this.nummer.set(number);
    }
    
    public IntegerProperty nummerProperty() {
        return nummer;
    }
}
