package emerge.project.mr_indoscan_rep.utils.entittes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Products implements Serializable {


    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("code")
    String code;

    @SerializedName("imageUrl")
    String imageUrl;

    @SerializedName("principle")
    String principle;


    @SerializedName("isApproved")
    Boolean isSelect = false;

    public Products(int id, String name, Boolean isSelect) {
        this.id = id;
        this.name = name;
        this.isSelect = isSelect;
    }


    public Products(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Products(int id, String name, String principle) {
        this.id = id;
        this.name = name;
        this.principle = principle;
    }

    public Products(int id, String name, String code, String imageUrl) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.imageUrl = imageUrl;
    }

    public Products(int id, String name, String code, String imageUrl, Boolean isSelect) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.imageUrl = imageUrl;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean isSelect() {
        return isSelect;
    }

    public void setSelect(Boolean select) {
        isSelect = select;
    }


    public String getPrinciple() {
        return principle;
    }

    public void setPrinciple(String principle) {
        this.principle = principle;
    }

    public Boolean getSelect() {
        return isSelect;
    }
}
