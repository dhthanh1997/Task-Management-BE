package com.ansv.taskmanagement.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "member")
public class Member extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", columnDefinition = "nvarchar(100)")
    private String name;

    @Column(name = "username", columnDefinition = "varchar(20)")
    private String username;

    @Column(name = "phone", columnDefinition = "varchar(20)")
    private String phone;

    @Column(name = "email", columnDefinition = "varchar(100)")
    private String email;

    @Column(name = "role_id")
    private Long roleId;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "team_member", joinColumns = {
            @JoinColumn(name = "member_id", referencedColumnName = "id")
    },
            inverseJoinColumns = {
                    @JoinColumn(name = "team_id", referencedColumnName = "id")
            }
    )
    @JsonIgnore
    private Set<Team> teams = new HashSet<>();


    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "member_role_project", joinColumns = {
            @JoinColumn(name = "member_id", referencedColumnName = "id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "role_project_id", referencedColumnName = "id")
    }
    )
    @JsonIgnore
    private Set<RoleOfApplication> roleOfProject;


    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY)
    private Set<Task>  tasks = new HashSet<>();

}
