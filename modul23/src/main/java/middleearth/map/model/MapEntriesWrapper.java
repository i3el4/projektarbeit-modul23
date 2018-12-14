package middleearth.map.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Helper class to wrap a list of suggestions. This is used for saving the
 * list of suggestions to XML.
 * 
 * @author Bela Ackermann, with instructions from code.makery.ch
 */
@XmlRootElement(name = "vorschlaege")
public class MapEntriesWrapper implements Serializable{

	private static final long serialVersionUID = 9086555353107825605L;
	private List<MapEntry> vorschlaege;

    @XmlElement(name = "vorschlag")
    public List<MapEntry> getVorschlaege() {
        return vorschlaege;
    }

    public void setVorschlaege(List<MapEntry> vorschlaege) {
        this.vorschlaege = vorschlaege;
    }
}