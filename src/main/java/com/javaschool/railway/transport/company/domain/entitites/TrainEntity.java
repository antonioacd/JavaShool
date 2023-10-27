package com.javaschool.railway.transport.company.domain.entitites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;

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
	private int seats;

	@Column(name="duration", nullable = false)
	private Duration duration;

	@Column(name="train_number", nullable = false)
	private String trainNumber;

	@ManyToOne
	@JoinColumn(name = "current_station_id", referencedColumnName= "id",  nullable = false)
	private StationEntity departureStation;

	@ManyToOne
	@JoinColumn(name = "station_station_id", referencedColumnName= "id",  nullable = false)
	private StationEntity arrivalStation;

}