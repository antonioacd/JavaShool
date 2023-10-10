package com.javaschool.railway.transport.company.schedule;

import com.javaschool.railway.transport.company.rol.Rol;
import com.javaschool.railway.transport.company.station.Station;
import com.javaschool.railway.transport.company.train.Train;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="schedules", schema = "public", catalog = "RAILWAY_TRANSPORT_COMPANY")
@Getter
@Setter
public class Schedule {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="departure_time")
	private String departure_time;

	@Column(name="arrival_time")
	private String arrival_time;

	@Column(name="occupied_seats")
	private String occupied_seats;

	@ManyToOne
	@JoinColumn(name = "departure_station_id")
	Station departure_station;

	@ManyToOne
	@JoinColumn(name = "arrival_station_id")
	Station arrival_station;

	@ManyToOne
	@JoinColumn(name = "train_id")
	Train train;

}