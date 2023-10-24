package com.javaschool.railway.transport.company.domain.entitites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="trains", schema = "public", catalog = "RAILWAY_TRANSPORT_COMPANY")
@Getter
@Setter
@ToString
public class TrainEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="seats", nullable = false)
	private String seats;

	@ManyToOne
	@JoinColumn(name = "current_station_id", referencedColumnName= "id",  nullable = false)
	private StationEntity currentStation;

}