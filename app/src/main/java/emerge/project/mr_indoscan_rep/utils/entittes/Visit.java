package emerge.project.mr_indoscan_rep.utils.entittes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Visit implements Serializable {


    @SerializedName("id")
    int id;

    @SerializedName("code")
    String code;

    @SerializedName("visitNumber")
    String visitNumber;

    @SerializedName("locationID")
    int locationID ;

    @SerializedName("location")
    String location;

    @SerializedName("doctorID")
    int doctorID ;

    @SerializedName("doctorName")
    String doctorName;

    @SerializedName("repID")
    int repID ;

    @SerializedName("repName")
    String repName;

    @SerializedName("comment")
    String comment;

    @SerializedName("visitDate")
    String visitDate;

    @SerializedName("latitude")
    Double latitude;

    @SerializedName("longitude")
    Double longitude;

    @SerializedName("visitsProduct")
    ArrayList<VisitProducts> visitsProduct;


    @SerializedName("imageUrl")
    String imageUrl;

    boolean isProductExpaind;

    public Visit(int id) {
        this.id = id;
    }

    public Visit() {
    }

    public Visit(int id, String code, String visitNumber, int locationID, String location, int doctorID, String doctorName, int repID, String repName, String comment, String visitDate,
                 Double latitude, Double longitude, ArrayList<VisitProducts> visitsProduct, boolean isProductExpaind, String imageUrl) {
        this.id = id;
        this.code = code;
        this.visitNumber = visitNumber;
        this.locationID = locationID;
        this.location = location;
        this.doctorID = doctorID;
        this.doctorName = doctorName;
        this.repID = repID;
        this.repName = repName;
        this.comment = comment;
        this.visitDate = visitDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.visitsProduct = visitsProduct;
        this.isProductExpaind = isProductExpaind;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVisitNumber() {
        return visitNumber;
    }

    public void setVisitNumber(String visitNumber) {
        this.visitNumber = visitNumber;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public int getRepID() {
        return repID;
    }

    public void setRepID(int repID) {
        this.repID = repID;
    }

    public String getRepName() {
        return repName;
    }

    public void setRepName(String repName) {
        this.repName = repName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public ArrayList<VisitProducts> getVisitsProduct() {
        return visitsProduct;
    }

    public void setVisitsProduct(ArrayList<VisitProducts> visitsProduct) {
        this.visitsProduct = visitsProduct;
    }

    public boolean isProductExpaind() {
        return isProductExpaind;
    }

    public void setProductExpaind(boolean productExpaind) {
        isProductExpaind = productExpaind;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    /* int visitsID;
    String visitsCode;
    ArrayList<VisitProducts> visitProducts;
    boolean isProductExpaind;


    public Visit(int visitsID, String visitsCode, ArrayList<VisitProducts> visitProducts,boolean expaind) {
        this.visitsID = visitsID;
        this.visitsCode = visitsCode;
        this.visitProducts = visitProducts;
        this.isProductExpaind = expaind;
    }


    public int getVisitsID() {
        return visitsID;
    }

    public void setVisitsID(int visitsID) {
        this.visitsID = visitsID;
    }

    public String getVisitsCode() {
        return visitsCode;
    }

    public void setVisitsCode(String visitsCode) {
        this.visitsCode = visitsCode;
    }

    public ArrayList<VisitProducts> getVisitProducts() {
        return visitProducts;
    }

    public void setVisitProducts(ArrayList<VisitProducts> visitProducts) {
        this.visitProducts = visitProducts;
    }

    public boolean isProductExpaind() {
        return isProductExpaind;
    }

    public void setProductExpaind(boolean productExpaind) {
        isProductExpaind = productExpaind;
    }*/
}
