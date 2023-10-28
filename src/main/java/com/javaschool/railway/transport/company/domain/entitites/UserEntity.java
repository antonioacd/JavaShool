package com.javaschool.railway.transport.company.domain.entitites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

import java.time.LocalDate;

@Entity
@Table(name="users", schema = "public", catalog = "RAILWAY_TRANSPORT_COMPANY")
@Getter
@Setter
@AllArgsConstructor
public class UserEntity {

	public UserEntity(String email, String password) {
		this.email = email;
		this.password = password;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="name", nullable = false)
	private String name;

	@Column(name = "surname", nullable = false)
	private String surname;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@ManyToOne
	@JoinColumn(name = "rol_id", referencedColumnName= "id", nullable = false)
	private RolEntity rol;
}