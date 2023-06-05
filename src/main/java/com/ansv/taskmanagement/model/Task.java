package com.ansv.taskmanagement.model;


import com.ansv.taskmanagement.constants.StateEnum;
import com.ansv.taskmanagement.util.formatdate.LocalDateTimeDeserializer;
import com.ansv.taskmanagement.util.formatdate.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task")
@Builder
public class Task extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", columnDefinition = "nvarchar(500)")
    private String name;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "total_cost", precision = 18, scale = 2)
    private BigDecimal totalCost;

    @Column(name = "revenue", precision = 18, scale = 2)
    private BigDecimal revenue;

    @Column(name = "total_hour", precision = 2, scale = 2)
    private Float totalHour;

    @Column(name = "start_date")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startDate;

    @Column(name = "end_date")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endDate;

    @Column(name = "estimate_start_date")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime estimateStartDate;

    @Column(name = "estimate_end_date")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime estimateEndDate;

    @Column(name = "real_start_date")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime realStartDate;

    @Column(name = "real_end_date")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime realEndDate;

    @Column(name = "description", columnDefinition = "nvarchar(500)")
    private String description;

    @Column(name = "problem", columnDefinition = "nvarchar(500)")
    private String problem;

    @Column(name = "solution", columnDefinition = "nvarchar(500)")
    private String solution;

    @Column(name = "note", columnDefinition = "nvarchar(500)")
    private String note;

    @Column(name = "attach_file", columnDefinition = "varchar(2000)")
    private String attachFile;

    @Column(name = "parent_id")
    private Long parentId;

    @Basic
    @Column(name = "state")
    private Byte state;

    @Transient
    private String stateName;

    @PostLoad
    void fillTransient() {
        if (state >= 0) {
            this.stateName = StateEnum.of(state).getValue();
        }
    }

    @PrePersist
    void fillPersistent() {
        if (stateName != null) {
            this.state = (byte) StateEnum.ordinalOf(stateName);
        }
    }

    @Column(name = "section_id")
    private Long sectionId;

    @Column(name = "tag_id")
    private Long tagId;

//    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
//    @JoinTable(name = "task_member", joinColumns = {
//            @JoinColumn(name = "task_id", referencedColumnName = "id")
//    },
//            inverseJoinColumns = {
//                    @JoinColumn(name = "member_id", referencedColumnName = "id")
//            }
//    )
//    @JsonIgnore
//    private Set<Member> members = new HashSet<>();

}
