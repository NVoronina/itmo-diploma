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
@Table(name = "specializations")
public class SpecializationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;

    // TODO: 17.03.2022 to Enum and many
    // this field is a name of possible specialization
    // we have API for adding new specialization name to this table, that's why I think I should't do it enum
    // Am I wrong?
    @Column(nullable = false)
    private String specialization;

    @OneToMany(mappedBy = "specialization")
    private Set<DoctorEntity> doctors;
}
