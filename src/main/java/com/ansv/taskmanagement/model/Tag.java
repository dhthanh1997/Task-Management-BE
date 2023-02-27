package com.ansv.taskmanagement.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tag")
public class Tag extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "tag_name", columnDefinition = "nvarchar(1000)")
    private String name;

    @Column(name = "slug", columnDefinition = "varchar(500)")
    private String slug;

    @Column(name = "icon", columnDefinition = "varchar(500)")
    private String icon;

    @Column(name = "color", columnDefinition = "varchar(50)")
    private String color;

}
