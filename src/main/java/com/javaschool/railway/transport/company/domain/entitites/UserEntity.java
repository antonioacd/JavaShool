package com.javaschool.railway.transport.company.domain.entitites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="users", schema = "public", catalog = "RAILWAY_TRANSPORT_COMPANY")
@Getter
@Setter
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="name")
	private String name;

	@Column(name = "surname")
	private String surname;

	@Column(name = "date_of_birth")
	private LocalDate date_of_birth;

	@ManyToOne
	@JoinColumn(name = "rol_id")
	private RolEntity rol;

}