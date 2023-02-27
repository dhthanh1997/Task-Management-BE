package com.ansv.taskmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
// dùng để lưu section của các task : section có thể là to do, do next (kế hoạch sang tuần)
@Entity
@Table(name = "section")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Section extends Auditable<String> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", columnDefinition = "nvarchar(200)")
    private String name;

    @Column(name = "note", columnDefinition = "nvarchar(500)")
    private String note;

}
