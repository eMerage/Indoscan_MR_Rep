package emerge.project.mr_indoscan_rep.utils.entittes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sample implements Serializable ,Comparable {

    @SerializedName("id")
    Integer id;

    @SerializedName("name")
    String name;

    @SerializedName("sampleTypeID")
    Integer sampleTypeID;

    @SerializedName("assignedQty")
    Integer assignedQty;

    @SerializedName("availableQty")
    Integer availableQty;

    @SerializedName("maxQty")
    Integer maxQty;

    @SerializedName("isApproved")
    Boolean isSelect = false;

    Integer redeemQty = 0;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSelect() {
        return isSelect;
    }

    public void setSelect(Boolean select) {
        isSelect = select;
    }

    public Integer getSampleTypeID() {
        return sampleTypeID;
    }

    public void setSampleTypeID(Integer sampleTypeID) {
        this.sampleTypeID = sampleTypeID;
    }

    public Integer getAssignedQty() {
        return assignedQty;
    }

    public void setAssignedQty(Integer assignedQty) {
        this.assignedQty = assignedQty;
    }

    public Integer getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(Integer availableQty) {
        this.availableQty = availableQty;
    }

    public Integer getMaxQty() {
        return maxQty;
    }

    public void setMaxQty(Integer maxQty) {
        this.maxQty = maxQty;
    }

    public Integer getRedeemQty() {
        return redeemQty;
    }

    public void setRedeemQty(Integer redeemQty) {
        this.redeemQty = redeemQty;
    }

    @Override
    public int compareTo(Object o) {
        int comparQty=((Sample)o).getRedeemQty();
        return comparQty - this.redeemQty;
    }
}
