package com.medical.medonline.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    private String secondName;

    private String phone;

    @Column(nullable = false)
    private String email;

    @OneToOne(mappedBy = "user")
    private PatientEntity patient;

    @OneToOne(mappedBy = "user")
    private DoctorEntity doctor;

    @OneToOne(mappedBy = "user")
    private ManagerEntity manager;

}
