package com.javaschool.railway.transport.company.domain.entitites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="trains", schema = "public", catalog = "RAILWAY_TRANSPORT_COMPANY")
@Getter
@Setter
public class TrainEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="seats")
	private String seats;

}