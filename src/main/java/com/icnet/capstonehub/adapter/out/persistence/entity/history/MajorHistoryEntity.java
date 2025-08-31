package com.icnet.capstonehub.adapter.out.persistence.entity.history;

import com.icnet.capstonehub.adapter.out.persistence.entity.MajorEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "major_history")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MajorHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "predecessor_id")
    private MajorEntity predecessor;

    @ManyToOne
    @JoinColumn(name = "successor_id")
    private MajorEntity successor;

    @Column(name = "reason_for_change")
    private String reasonForChange;

    @Column(name = "change_type")
    private String changeType;

    @Column(name = "change_datetime")
    private LocalDateTime changeDateTime;
}
