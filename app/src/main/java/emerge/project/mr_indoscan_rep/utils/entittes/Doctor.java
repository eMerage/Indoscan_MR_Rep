package emerge.project.mr_indoscan_rep.utils.entittes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Doctor implements Serializable {



    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("code")
    String code;

    @SerializedName("contactNo")
    String contactNo;

    @SerializedName("registrationNo")
    String registrationNo;


    @SerializedName("qualification")
    String qualification;

    @SerializedName("createdDate")
    String createdDate;

    @SerializedName("createdByID")
    int createdByID;

    @SerializedName("createdByName")
    String createdByName;


    @SerializedName("imageUrl")
    String imageUrl;


    @SerializedName("isApproved")
    boolean isApproved;


    @SerializedName("isRejected")
    boolean isRejected;




    @SerializedName("error")
    Error error =  new Error();


    @SerializedName("specializationList")
    ArrayList<Specialization> specializationList;


    @SerializedName("locationList")
    ArrayList<LocationEntitie> locationList;


    String status;
    String specialization;
    String location;

    Boolean isSelect;



    public Doctor() {
    }


    public Doctor(String name) {
        this.name = name;
    }

    public Doctor(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Doctor(int id, String name, Boolean isSelect) {
        this.id = id;
        this.name = name;
        this.isSelect = isSelect;
    }

    public Doctor(int id, String name, String contactNo, String registrationNo, int createdByID, String createdByName) {
        this.id = id;
        this.name = name;
        this.contactNo = contactNo;
        this.registrationNo = registrationNo;
        this.createdByID = createdByID;
        this.createdByName = createdByName;
    }

    public Doctor(int id, String name, String imageUrl, Boolean isSelect) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.isSelect = isSelect;
    }

    public Doctor(int id, String name, String code, String contactNo, String registrationNo, String qualification, String createdDate, int createdByID, String createdByName, String imageUrl) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.contactNo = contactNo;
        this.registrationNo = registrationNo;
        this.qualification = qualification;
        this.createdDate = createdDate;
        this.createdByID = createdByID;
        this.createdByName = createdByName;
        this.imageUrl = imageUrl;
    }

    public Doctor(int id, String name, String code, String contactNo, String registrationNo, String qualification, String createdDate,
                  int createdByID, String createdByName, String imageUrl, boolean isApproved, boolean isRejected,
                  String specialization,String location,ArrayList<Specialization> specializationList,ArrayList<LocationEntitie> locationList,String status) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.contactNo = contactNo;
        this.registrationNo = registrationNo;
        this.qualification = qualification;
        this.createdDate = createdDate;
        this.createdByID = createdByID;
        this.createdByName = createdByName;
        this.imageUrl = imageUrl;
        this.isApproved = isApproved;
        this.isRejected = isRejected;
        this.specialization = specialization;
        this.location = location;
        this.specializationList = specializationList;
        this.locationList = locationList;
        this.status = status;

    }


    public Doctor(int id, String name, String code, String contactNo, String registrationNo, String qualification, String createdDate,
                  int createdByID, String createdByName, String imageUrl, boolean isApproved, boolean isRejected,
                  String specialization,String location,ArrayList<Specialization> specializationList,ArrayList<LocationEntitie> locationList,String status,boolean isSelect) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.contactNo = contactNo;
        this.registrationNo = registrationNo;
        this.qualification = qualification;
        this.createdDate = createdDate;
        this.createdByID = createdByID;
        this.createdByName = createdByName;
        this.imageUrl = imageUrl;
        this.isApproved = isApproved;
        this.isRejected = isRejected;
        this.specialization = specialization;
        this.location = location;
        this.specializationList = specializationList;
        this.locationList = locationList;
        this.status = status;
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

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
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



    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Boolean isSelect() {
        return isSelect;
    }

    public void setSelect(Boolean select) {
        isSelect = select;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public boolean isRejected() {
        return isRejected;
    }

    public void setRejected(boolean rejected) {
        isRejected = rejected;
    }


    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Specialization> getSpecializationList() {
        return specializationList;
    }

    public void setSpecializationList(ArrayList<Specialization> specializationList) {
        this.specializationList = specializationList;
    }

    public ArrayList<LocationEntitie> getLocationList() {
        return locationList;
    }

    public void setLocationList(ArrayList<LocationEntitie> locationList) {
        this.locationList = locationList;
    }
}
