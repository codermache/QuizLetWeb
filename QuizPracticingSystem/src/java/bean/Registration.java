
package bean;

import java.util.Date;

public class Registration {

    private int regId; /*Registration ID*/
    private int userId; /*ID User Registered*/
    private Date regTime; /*Registration Time*/
    private int packId; /*Price Package ID*/
    private double cost; /*Registration Cost*/
    private Date validFrom; /*Starting valid date*/
    private Date validTo; /*Ending valid date*/
    private int lastUpdatedBy; /*Last updated by user(id)*/
    private String note; /*Update note*/
    private boolean status; /*Registration Status*/

    /**
     * Blank Constructor
     */
    public Registration() {
    }

    /**
     * Complete constructor
     * @param regId
     * @param userId
     * @param regTime
     * @param packId
     * @param cost
     * @param validFrom
     * @param validTo
     * @param lastUpdatedBy
     * @param note
     * @param status 
     */
    public Registration(int regId, int userId, Date regTime, int packId, double cost, Date validFrom, Date validTo, int lastUpdatedBy, String note, boolean status) {
        this.regId = regId;
        this.userId = userId;
        this.regTime = regTime;
        this.packId = packId;
        this.cost = cost;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.lastUpdatedBy = lastUpdatedBy;
        this.note = note;
        this.status = status;
    }

    /**
     * Get registration id
     * @return 
     */
    public int getRegId() {
        return regId;
    }

    /**
     * Set Registration ID
     * @param regId 
     */
    public void setRegId(int regId) {
        this.regId = regId;
    }

    /**
     * Get Registered user id
     * @return 
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Set Registered user id
     * @param userId 
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Get price package id
     * @return 
     */
    public int getPackId() {
        return packId;
    }

    /**
     * Set price package id
     * @param packId 
     */
    public void setPackId(int packId) {
        this.packId = packId;
    }

    /**
     * Get last update user
     * @return 
     */
    public int getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Set last update user
     * @param lastUpdatedBy 
     */
    public void setLastUpdatedBy(int lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Get registration cost
     * @return 
     */
    public double getCost() {
        return cost;
    }

    /**
     * Set registration cost
     * @param cost 
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Get registration time
     * @return 
     */
    public Date getRegTime() {
        return regTime;
    }

    /**
     * Get starting valid date
     * @return 
     */
    public Date getValidFrom() {
        return validFrom;
    }

    /**
     * Get ending valid date
     * @return 
     */
    public Date getValidTo() {
        return validTo;
    }

    /**
     * Set register time
     * @param regTime 
     */
    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    /**
     * Set starting valid date
     * @param validFrom 
     */
    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    /**
     * Set ending valid date
     * @param validTo 
     */
    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }
    
    /**
     * Get note
     * @return 
     */
    public String getNote() {
        return note;
    }

    /**
     * Set note
     * @param note 
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Get status
     * @return 
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * Set Status
     * @param status 
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

}
