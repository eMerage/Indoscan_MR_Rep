package emerge.project.mr_indoscan_rep.data.db;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.annotations.PrimaryKey;

public class User implements Serializable {

    @PrimaryKey
    int rowId;

    @SerializedName("id")
    String userId;

    @SerializedName("username")
    String userName;

    @SerializedName("name")
    String uName;

    @SerializedName("email")
    String userEmail;

    @SerializedName("contactNo")
    String userPhoneNumber;

    @SerializedName("userTypeID")
    String userTypeID;

    @SerializedName("code")
    String userCode;

    @SerializedName("createdByID")
    int createdByID;

    @SerializedName("createdByName")
    String createdByName;

    @SerializedName("approvedByID")
    int approvedByID;

    @SerializedName("approvedByName")
    String approvedByName;

    @SerializedName("imageUrl")
    String imageUrl;

    @SerializedName("approvedDate")
    String approvedDate;






    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserTypeID() {
        return userTypeID;
    }

    public void setUserTypeID(String userTypeID) {
        this.userTypeID = userTypeID;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
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

    public int getApprovedByID() {
        return approvedByID;
    }

    public void setApprovedByID(int approvedByID) {
        this.approvedByID = approvedByID;
    }

    public String getApprovedByName() {
        return approvedByName;
    }

    public void setApprovedByName(String approvedByName) {
        this.approvedByName = approvedByName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }
}
