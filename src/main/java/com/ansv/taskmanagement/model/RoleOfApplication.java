package com.ansv.taskmanagement.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role_of_application")
public class RoleOfApplication extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", columnDefinition = "nvarchar(500)")
    private String name;

    @Column(name = "code", columnDefinition = "varchar(20)")
    private String code;

    @Column(name = "description", columnDefinition = "nvarchar(500)")
    private String description;

//    @OneToMany(mappedBy = "roleOfApplication")
//    private Set<RolePermission> rolePermission;

}
