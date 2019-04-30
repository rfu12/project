/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Raymondsama
 */
@Entity
@Table(name = "REPORT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Report.findAll", query = "SELECT r FROM Report r")
    , @NamedQuery(name = "Report.findByReportId", query = "SELECT r FROM Report r WHERE r.reportId = :reportId")
    , @NamedQuery(name = "Report.findByDate", query = "SELECT r FROM Report r WHERE r.date = :date")
    , @NamedQuery(name = "Report.findByTotalCaloriesConsumed", query = "SELECT r FROM Report r WHERE r.totalCaloriesConsumed = :totalCaloriesConsumed")
    , @NamedQuery(name = "Report.findByTotalCaloriesBurned", query = "SELECT r FROM Report r WHERE r.totalCaloriesBurned = :totalCaloriesBurned")
    , @NamedQuery(name = "Report.findByTotalStepsTaken", query = "SELECT r FROM Report r WHERE r.totalStepsTaken = :totalStepsTaken")
    , @NamedQuery(name = "Report.findByUserId", query = "SELECT s FROM Report s WHERE s.userId = :userId")
    , @NamedQuery(name = "Report.findByCaloriesGoal", query = "SELECT s FROM Report s WHERE s.caloriesGoal = :caloriesGoal")})
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "REPORT_ID")
    private Integer reportId;
    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "TOTAL_CALORIES_CONSUMED")
    private Integer totalCaloriesConsumed;
    @Column(name = "TOTAL_CALORIES_BURNED")
    private Integer totalCaloriesBurned;
    @Column(name = "TOTAL_STEPS_TAKEN")
    private Integer totalStepsTaken;
    @Column(name = "CALORIES_GOAL")
    private Integer caloriesGoal;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;

    public Report() {
    }

    public Report(Integer reportId) {
        this.reportId = reportId;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getTotalCaloriesConsumed() {
        return totalCaloriesConsumed;
    }

    public void setTotalCaloriesConsumed(Integer totalCaloriesConsumed) {
        this.totalCaloriesConsumed = totalCaloriesConsumed;
    }

    public Integer getTotalCaloriesBurned() {
        return totalCaloriesBurned;
    }

    public void setTotalCaloriesBurned(Integer totalCaloriesBurned) {
        this.totalCaloriesBurned = totalCaloriesBurned;
    }

    public Integer getTotalStepsTaken() {
        return totalStepsTaken;
    }

    public void setTotalStepsTaken(Integer totalStepsTaken) {
        this.totalStepsTaken = totalStepsTaken;
    }
    
    public Integer getCaloriesGoal() {
        return caloriesGoal;
    }

    public void setCaloriesGoal(Integer CaloriesGoal) {
        this.caloriesGoal = CaloriesGoal;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reportId != null ? reportId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Report)) {
            return false;
        }
        Report other = (Report) object;
        if ((this.reportId == null && other.reportId != null) || (this.reportId != null && !this.reportId.equals(other.reportId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "application.Report[ reportId=" + reportId + " ]";
    }
    
}
