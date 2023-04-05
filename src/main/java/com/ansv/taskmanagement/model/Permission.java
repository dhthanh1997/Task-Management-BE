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
@Table(name = "permission")
public class Permission extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", columnDefinition = "nvarchar(500)")
    private String name;

    @Column(name = "code", columnDefinition = "varchar(500)")
    private String code;

    @Column(name = "parent_code", columnDefinition = "varchar(500)")
    private String parentCode;

    @Column(name = "description", columnDefinition = "nvarchar(500)")
    private String description;

    @Column(name = "type")
    private Byte type;

//    @OneToMany(mappedBy = "permission")
//    private Set<RolePermission> rolePermission;

}
