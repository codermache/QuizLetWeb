
package bean;

import java.util.Date;

public class RegistrationManage {
     private int regId; /*Registration ID*/
    private String userMail; /*User Mail Registered*/
    private Date regTime; /*Registration Time*/
    private String subjectName;/*Subject Name*/
    private String packName; /*Price Package Name*/
    private double cost; /*Registration Cost*/
    private Date validFrom; /*Starting valid date*/
    private Date validTo; /*Ending valid date*/
    private String lastUpdatedBy; /*Last updated by user*/
    private String note; /*Update note*/
    private String status; /*Registration Status*/
    
    /**
     * Blank Constructor
     */
    public RegistrationManage() {
    }
    
    /**
     * 
     * @param regId
     * @param userMail
     * @param regTime
     * @param subjectName
     * @param packName
     * @param cost
     * @param validFrom
     * @param validTo
     * @param lastUpdatedBy
     * @param note
     * @param status 
     */
    
    public RegistrationManage(int regId, String userMail, Date regTime, String subjectName, String packName, double cost, Date validFrom, Date validTo, String lastUpdatedBy, String note, String status) {
        this.regId = regId;
        this.userMail = userMail;
        this.regTime = regTime;
        this.subjectName = subjectName;
        this.packName = packName;
        this.cost = cost;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.lastUpdatedBy = lastUpdatedBy;
        this.note = note;
        this.status = status;
    }
    
    /**
     * getRegId
     * @return 
     */
    public int getRegId() {
        return regId;
    }
    
    /**
     * setRegId
     * @param regId 
     */
    public void setRegId(int regId) {
        this.regId = regId;
    }
    
    /**
     * getUserMail
     * @return 
     */
    public String getUserMail() {
        return userMail;
    }
    
    /**
     * setUserMail
     * @param userMail 
     */
    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }
    
    /**
     * getRegTime
     * @return 
     */
    public Date getRegTime() {
        return regTime;
    }
    
    /**
     * setRegTime
     * @param regTime 
     */
    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }
    
    /**
     * getSubjectName
     * @return 
     */
    public String getSubjectName() {
        return subjectName;
    }
    
    /**
     * setSubjectName
     * @param subjectName 
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    
    /**
     * getPackName
     * @return 
     */
    public String getPackName() {
        return packName;
    }
    
    /**
     * setPackName
     * @param packName 
     */
    public void setPackName(String packName) {
        this.packName = packName;
    }
    
    /**
     * getCost
     * @return 
     */
    public double getCost() {
        return cost;
    }
    
    /**
     * setCost
     * @param cost 
     */
    public void setCost(double cost) {
        this.cost = cost;
    }
    
    /**
     * getValidFrom
     * @return 
     */
    public Date getValidFrom() {
        return validFrom;
    }
    
    /**
     * setValidFrom
     * @param validFrom 
     */
    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }
    /**
     * getValidTo
     * @return 
     */
    public Date getValidTo() {
        return validTo;
    }
    /**
     * setValidTo
     * @param validTo 
     */
    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }
    /**
     * getLastUpdatedBy
     * @return 
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    /**
     * setLastUpdatedBy
     * @param lastUpdatedBy 
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    
    /**
     * getNote
     * @return 
     */
    public String getNote() {
        return note;
    }
    /**
     * setNote
     * @param note 
     */
    public void setNote(String note) {
        this.note = note;
    }
    
    /**
     * getStatus
     * @return 
     */
    public String getStatus() {
        return status;
    }
    
    /**
     * setStatus
     * @param status 
     */
    public void setStatus(String status) {
        this.status = status;
    } 
    
    
}
