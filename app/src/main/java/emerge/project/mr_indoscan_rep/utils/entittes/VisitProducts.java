package emerge.project.mr_indoscan_rep.utils.entittes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VisitProducts implements Serializable {


    @SerializedName("id")
    int id;

    @SerializedName("productID")
    int productID;

    @SerializedName("visitID")
    int visitID;

    @SerializedName("productName")
    String productName;

    @SerializedName("imageUrl")
    String imageUrl;




    public VisitProducts(int id, int productID, int visitID, String productName, String imageUrl) {
        this.id = id;
        this.productID = productID;
        this.visitID = visitID;
        this.productName = productName;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getVisitID() {
        return visitID;
    }

    public void setVisitID(int visitID) {
        this.visitID = visitID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /*int visitsID;
    String visitsCode;


    public VisitProducts(int visitsID, String visitsCode) {
        this.visitsID = visitsID;
        this.visitsCode = visitsCode;
    }*/
}
