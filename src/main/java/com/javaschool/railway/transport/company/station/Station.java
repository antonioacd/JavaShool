package com.javaschool.railway.transport.company.station;

import com.javaschool.railway.transport.company.schedule.Schedule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="stations", schema = "public", catalog = "RAILWAY_TRANSPORT_COMPANY")
@Getter
@Setter
public class Station {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name="name")
	private String name;

	@Column(name="city")
	private String city;

	@OneToMany(mappedBy = "departure_station")
	List<Schedule> departureSchedulesList;

	@OneToMany(mappedBy = "arrival_station")
	List<Schedule> arrivalSchedulesList;

}