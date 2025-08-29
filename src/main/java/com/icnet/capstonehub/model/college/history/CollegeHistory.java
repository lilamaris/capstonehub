package com.icnet.capstonehub.model.college.history;

import com.icnet.capstonehub.model.college.College;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "college_history")
public class CollegeHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "predecessor_id")
    private College predecessor;

    @ManyToOne
    @JoinColumn(name = "successor_id")
    private College successor;

    @Column(name = "reason_for_change")
    private String reasonForChange;

    @Column(name = "change_type")
    private String changeType;

    @Column(name = "change_datetime")
    private LocalDateTime changeDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public College getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(College predecessor) {
        this.predecessor = predecessor;
    }

    public College getSuccessor() {
        return successor;
    }

    public void setSuccessor(College successor) {
        this.successor = successor;
    }

    public String getReasonForChange() {
        return reasonForChange;
    }

    public void setReasonForChange(String reasonForChange) {
        this.reasonForChange = reasonForChange;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public LocalDateTime getChangeDateTime() {
        return changeDateTime;
    }

    public void setChangeDateTime(LocalDateTime changeDateTime) {
        this.changeDateTime = changeDateTime;
    }
}
