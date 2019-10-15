package emerge.project.mr_indoscan_rep.utils.entittes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class DetailsSummary implements Serializable {


    @SerializedName("id")
    String userID;

    @SerializedName("townCount")
    int townCount;

    @SerializedName("townList")
    ArrayList<Towns> townList;


    @SerializedName("doctorCount")
    int doctorCount;

    @SerializedName("doctorList")
    ArrayList<Doctor> doctorList;


    @SerializedName("pharmacyCount")
    int pharmacyCount;

    @SerializedName("pharmacyList")
    ArrayList<Pharmacy> pharmacyList;


    @SerializedName("collectedOrdersCount")
    int collectedOrdersCount;

    @SerializedName("officialMileage")
    int officialMileage;

    @SerializedName("privateMileage")
    int privateMileage;


    @SerializedName("dailyExpenses")
    Double dailyExpenses;

    @SerializedName("townAchievement")
    Double townAchievement;


    @SerializedName("currentMonth")
    String currentMonth;

    @SerializedName("uptodateAchievement")
    Double uptodateAchievement;

    @SerializedName("balanceTarget")
    Double balanceTarget;


    @SerializedName("notVisitedCustomersToday")
    int notVisitedCustomers;

    @SerializedName("notVisitedCustomersAll")
    int notVisitedCustomersAll;




    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getTownCount() {
        return townCount;
    }

    public void setTownCount(int townCount) {
        this.townCount = townCount;
    }

    public ArrayList<Towns> getTownList() {
        return townList;
    }

    public void setTownList(ArrayList<Towns> townList) {
        this.townList = townList;
    }

    public int getDoctorCount() {
        return doctorCount;
    }

    public void setDoctorCount(int doctorCount) {
        this.doctorCount = doctorCount;
    }

    public ArrayList<Doctor> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(ArrayList<Doctor> doctorList) {
        this.doctorList = doctorList;
    }

    public int getPharmacyCount() {
        return pharmacyCount;
    }

    public void setPharmacyCount(int pharmacyCount) {
        this.pharmacyCount = pharmacyCount;
    }

    public ArrayList<Pharmacy> getPharmacyList() {
        return pharmacyList;
    }

    public void setPharmacyList(ArrayList<Pharmacy> pharmacyList) {
        this.pharmacyList = pharmacyList;
    }

    public int getCollectedOrdersCount() {
        return collectedOrdersCount;
    }

    public void setCollectedOrdersCount(int collectedOrdersCount) {
        this.collectedOrdersCount = collectedOrdersCount;
    }

    public int getOfficialMileage() {
        return officialMileage;
    }

    public void setOfficialMileage(int officialMileage) {
        this.officialMileage = officialMileage;
    }

    public int getPrivateMileage() {
        return privateMileage;
    }

    public void setPrivateMileage(int privateMileage) {
        this.privateMileage = privateMileage;
    }

    public Double getDailyExpenses() {
        return dailyExpenses;
    }

    public void setDailyExpenses(Double dailyExpenses) {
        this.dailyExpenses = dailyExpenses;
    }

    public Double getTownAchievement() {
        return townAchievement;
    }

    public void setTownAchievement(Double townAchievement) {
        this.townAchievement = townAchievement;
    }

    public String getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(String currentMonth) {
        this.currentMonth = currentMonth;
    }

    public Double getUptodateAchievement() {
        return uptodateAchievement;
    }

    public void setUptodateAchievement(Double uptodateAchievement) {
        this.uptodateAchievement = uptodateAchievement;
    }

    public Double getBalanceTarget() {
        return balanceTarget;
    }

    public void setBalanceTarget(Double balanceTarget) {
        this.balanceTarget = balanceTarget;
    }

    public int getNotVisitedCustomers() {
        return notVisitedCustomers;
    }

    public void setNotVisitedCustomers(int notVisitedCustomers) {
        this.notVisitedCustomers = notVisitedCustomers;
    }

    public int getNotVisitedCustomersAll() {
        return notVisitedCustomersAll;
    }

    public void setNotVisitedCustomersAll(int notVisitedCustomersAll) {
        this.notVisitedCustomersAll = notVisitedCustomersAll;
    }
}
