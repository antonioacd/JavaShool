package com.javaschool.railway.transport.company.user;

import com.javaschool.railway.transport.company.rol.Rol;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name="users", schema = "public", catalog = "RAILWAY_TRANSPORT_COMPANY")
@Getter
@Setter
public class User{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="name")
	private String name;

	@Column(name = "surname")
	private String surname;

	@Column(name = "date_of_birth")
	private Date date_of_birth;

	@OneToOne
	@JoinColumn(name = "rol_id")
	Rol rol;

}