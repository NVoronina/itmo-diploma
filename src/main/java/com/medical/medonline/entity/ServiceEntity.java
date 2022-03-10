package com.medical.medonline.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "services")
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private String service;

    private Integer time;

    @ManyToMany(mappedBy = "services", cascade = CascadeType.ALL)
    Set<DoctorEntity> doctors;

    @ManyToMany(mappedBy = "services", cascade = CascadeType.ALL)
    Set<DoctorEntity> appointments;
}
