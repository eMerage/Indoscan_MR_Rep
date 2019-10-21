package emerge.project.mr_indoscan_rep.utils.entittes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LocationType implements Serializable {

    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;


    boolean isSelect;

    public LocationType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public LocationType(int id, String name, boolean isSelect) {
        this.id = id;
        this.name = name;
        this.isSelect = isSelect;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}