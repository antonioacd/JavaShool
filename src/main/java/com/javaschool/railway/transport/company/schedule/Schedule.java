package com.javaschool.railway.transport.company.schedule;

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

}