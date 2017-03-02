package info.androidhive.expandablelistview;

import java.io.Serializable;

/**
 * Created by ideafoundation on 31/01/17.
 */

public class Need implements Serializable {
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    private boolean selected;

    public Need(String places) {
        this.places = places;
    }

    public String getPlaces() {
        return places;
    }

    public void setPlaces(String places) {
        this.places = places;
    }

    String places;

}
