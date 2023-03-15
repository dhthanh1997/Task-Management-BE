package com.ansv.taskmanagement.model;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
