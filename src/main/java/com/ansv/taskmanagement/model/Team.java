package com.ansv.taskmanagement.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "team")
public class Team extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "team_name", columnDefinition = "nvarchar(500)")
    private String teamName;

//    @Column(name = "project_id")
//    private Long projectId;

    @Column(name = "description", columnDefinition = "nvarchar(500)")
    private String description;

    @ManyToMany(mappedBy = "teams", fetch = FetchType.LAZY)
    private Set<Member> members = new HashSet<>();

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

}
