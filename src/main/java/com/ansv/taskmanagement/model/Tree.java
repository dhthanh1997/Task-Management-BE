package com.ansv.taskmanagement.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Immutable
public class Tree implements Serializable {
    @Id
    private Long id;

    private String name;

    private String code;

    private String parentCode;

    private String description;

    private Integer type;

    private Integer depth;

}
