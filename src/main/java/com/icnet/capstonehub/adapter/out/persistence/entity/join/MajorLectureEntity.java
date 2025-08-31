package com.icnet.capstonehub.adapter.out.persistence.entity.join;

import com.icnet.capstonehub.adapter.out.persistence.entity.LectureEntity;
import com.icnet.capstonehub.adapter.out.persistence.entity.MajorEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "major_lecture")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MajorLectureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_id", nullable = false)
    private MajorEntity major;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private LectureEntity lecture;

    @Column(name = "effective_start_date", nullable = false)
    private LocalDate effectiveStartDate;

    @Column(name = "effective_end_date")
    private LocalDate effectiveEndDate;
}
