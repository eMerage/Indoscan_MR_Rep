package emerge.project.mr_indoscan_rep.utils.entittes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TargetDetails implements Serializable {


    @SerializedName("ytdTarget")
    String yearlyTarget;

    @SerializedName("ytdAchievement")
    String yearlyAchievement;

    @SerializedName("ytdPercentage")
    String yearlyPercentage;

    @SerializedName("monthlyTarget")
    String monthlyTarget;

    @SerializedName("monthlyAchievement")
    String monthlyAchievement;

    @SerializedName("monthlyPercentage")
    String monthlyPercentage;


    @SerializedName("targetBalance")
    String targetBalance;

    //presntage
    @SerializedName("totalCurrentMonthTarget")
    String totalCurrentMonthTarget;

    @SerializedName("promotionBalance")
    String promotionBalance;

    @SerializedName("nextMonthPromotionBudget")
    String nextMonthPromotionBudget;

    @SerializedName("totalPromotionBudget")
    String totalPromotionBudget;

    @SerializedName("totalApprovedDoctors")
    String totalApprovedDoctors;

    @SerializedName("totalVisitedDoctors")
    String totalVisitedDoctors;

    @SerializedName("doctorVisitingPercentage")
    String doctorVisitingPercentage;

    @SerializedName("averageSalePerDoctor")
    String averageSalePerDoctor;










    public TargetDetails() {
    }



    public String getYearlyTarget() {
        return yearlyTarget;
    }

    public void setYearlyTarget(String yearlyTarget) {
        this.yearlyTarget = yearlyTarget;
    }

    public String getYearlyAchievement() {
        return yearlyAchievement;
    }

    public void setYearlyAchievement(String yearlyAchievement) {
        this.yearlyAchievement = yearlyAchievement;
    }

    public String getYearlyPercentage() {
        return yearlyPercentage;
    }

    public void setYearlyPercentage(String yearlyPercentage) {
        this.yearlyPercentage = yearlyPercentage;
    }

    public String getMonthlyTarget() {
        return monthlyTarget;
    }

    public void setMonthlyTarget(String monthlyTarget) {
        this.monthlyTarget = monthlyTarget;
    }

    public String getMonthlyAchievement() {
        return monthlyAchievement;
    }

    public void setMonthlyAchievement(String monthlyAchievement) {
        this.monthlyAchievement = monthlyAchievement;
    }

    public String getMonthlyPercentage() {
        return monthlyPercentage;
    }

    public void setMonthlyPercentage(String monthlyPercentage) {
        this.monthlyPercentage = monthlyPercentage;
    }


    public String getTargetBalance() {
        return targetBalance;
    }

    public void setTargetBalance(String targetBalance) {
        this.targetBalance = targetBalance;
    }

    public String getTotalCurrentMonthTarget() {
        return totalCurrentMonthTarget;
    }

    public void setTotalCurrentMonthTarget(String totalCurrentMonthTarget) {
        this.totalCurrentMonthTarget = totalCurrentMonthTarget;
    }

    public String getPromotionBalance() {
        return promotionBalance;
    }

    public void setPromotionBalance(String promotionBalance) {
        this.promotionBalance = promotionBalance;
    }

    public String getNextMonthPromotionBudget() {
        return nextMonthPromotionBudget;
    }

    public void setNextMonthPromotionBudget(String nextMonthPromotionBudget) {
        this.nextMonthPromotionBudget = nextMonthPromotionBudget;
    }

    public String getTotalPromotionBudget() {
        return totalPromotionBudget;
    }

    public void setTotalPromotionBudget(String totalPromotionBudget) {
        this.totalPromotionBudget = totalPromotionBudget;
    }

    public String getTotalApprovedDoctors() {
        return totalApprovedDoctors;
    }

    public void setTotalApprovedDoctors(String totalApprovedDoctors) {
        this.totalApprovedDoctors = totalApprovedDoctors;
    }

    public String getTotalVisitedDoctors() {
        return totalVisitedDoctors;
    }

    public void setTotalVisitedDoctors(String totalVisitedDoctors) {
        this.totalVisitedDoctors = totalVisitedDoctors;
    }

    public String getDoctorVisitingPercentage() {
        return doctorVisitingPercentage;
    }

    public void setDoctorVisitingPercentage(String doctorVisitingPercentage) {
        this.doctorVisitingPercentage = doctorVisitingPercentage;
    }

    public String getAverageSalePerDoctor() {
        return averageSalePerDoctor;
    }

    public void setAverageSalePerDoctor(String averageSalePerDoctor) {
        this.averageSalePerDoctor = averageSalePerDoctor;
    }
}
