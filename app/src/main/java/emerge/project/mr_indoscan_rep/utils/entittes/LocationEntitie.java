package emerge.project.mr_indoscan_rep.utils.entittes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LocationEntitie implements Serializable {


    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("address")
    String address;

    @SerializedName("area")
    String area;

    @SerializedName("town")
    String town;

    @SerializedName("districtID")
    int districtID;

    @SerializedName("district")
    String district;

    @SerializedName("latitude")
    Double latitude;

    @SerializedName("longitude")
    Double longitude;

    @SerializedName("createdByID")
    int createdByID;

    @SerializedName("createdByName")
    String createdByName;


    @SerializedName("locationTypeID")
    int locationTypeID;

        @SerializedName("locationType")
    String locationType;


    @SerializedName("error")
    Error error =  new Error();



    @SerializedName("isApproved")
    Boolean isApproved;

    @SerializedName("isRejected")
    Boolean isRejected;

    @SerializedName("status")
    String status;


    Boolean isSelect;

    public LocationEntitie() {
    }

    public LocationEntitie(int id, String name, Boolean isSelect) {
        this.id = id;
        this.name = name;
        this.isSelect = isSelect;
    }

    public LocationEntitie(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public LocationEntitie(int id, String name, String address, Boolean isSelect) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.isSelect = isSelect;
    }

    public LocationEntitie(int id, String name, String address, String area, String town, int districtID, String district, Double latitude, Double longitude,
                           int createdByID, String createdByName, int locationTypeID, String locationType,boolean isApproved,boolean isRejected,String st) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.area = area;
        this.town = town;
        this.districtID = districtID;
        this.district = district;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdByID = createdByID;
        this.createdByName = createdByName;
        this.locationTypeID = locationTypeID;
        this.locationType = locationType;
        this.isApproved = isApproved;
        this.isRejected = isRejected;
        this.status = st;
    }


    public LocationEntitie(int id, String name, String address, Double latitude, Double longitude, String createdByName) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdByName = createdByName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public int getDistrictID() {
        return districtID;
    }

    public void setDistrictID(int districtID) {
        this.districtID = districtID;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
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

    public int getCreatedByID() {
        return createdByID;
    }

    public void setCreatedByID(int createdByID) {
        this.createdByID = createdByID;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public int getLocationTypeID() {
        return locationTypeID;
    }

    public void setLocationTypeID(int locationTypeID) {
        this.locationTypeID = locationTypeID;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public Boolean isSelect() {
        return isSelect;
    }
    public void setSelect(Boolean select) {
        isSelect = select;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    public Boolean getRejected() {
        return isRejected;
    }

    public void setRejected(Boolean rejected) {
        isRejected = rejected;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
