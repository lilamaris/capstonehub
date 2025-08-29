package com.icnet.capstonehub.model.college.history;

import com.icnet.capstonehub.model.college.Lecture;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "lecture_history")
public class LectureHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "predecessor_id")
    private Lecture predecessor;

    @ManyToOne
    @JoinColumn(name = "successor_id")
    private Lecture successor;

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

    public Lecture getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Lecture predecessor) {
        this.predecessor = predecessor;
    }

    public Lecture getSuccessor() {
        return successor;
    }

    public void setSuccessor(Lecture successor) {
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
