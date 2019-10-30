package emerge.project.mr_indoscan_rep.utils.entittes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Mileage implements Serializable {

    @SerializedName("mileageID")
    int mileageID = 0;

    @SerializedName("userID")
    int userID;


    @SerializedName("dayEndOdometerReading")
    String dayEndOdometerReading="";


    @SerializedName("dayStartOdometerReading")
    String dayStartOdometerReading="";



    public int getMileageID() {
        return mileageID;
    }

    public void setMileageID(int mileageID) {
        this.mileageID = mileageID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getDayEndOdometerReading() {
        return dayEndOdometerReading;
    }

    public void setDayEndOdometerReading(String dayEndOdometerReading) {
        this.dayEndOdometerReading = dayEndOdometerReading;
    }

    public String getDayStartOdometerReading() {
        return dayStartOdometerReading;
    }

    public void setDayStartOdometerReading(String dayStartOdometerReading) {
        this.dayStartOdometerReading = dayStartOdometerReading;
    }
}
