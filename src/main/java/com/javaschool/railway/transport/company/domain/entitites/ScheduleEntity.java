package com.javaschool.railway.transport.company.domain.entitites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name="schedules", schema = "public", catalog = "RAILWAY_TRANSPORT_COMPANY")
@Getter
@Setter
public class ScheduleEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name="departure_time", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date departureTime;

	@Column(name="arrival_time", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date arrivalTime;

	@Column(name="occupied_seats", nullable = false)
	private String occupiedSeats;

	@ManyToOne
	@JoinColumn(name = "departure_station_id", referencedColumnName= "id", nullable = false)
	private StationEntity departureStation;

	@ManyToOne
	@JoinColumn(name = "arrival_station_id", referencedColumnName= "id", nullable = false)
	private StationEntity arrivalStation;

	@ManyToOne
	@JoinColumn(name = "train_id", referencedColumnName= "id", nullable = false)
	private TrainEntity train;

}