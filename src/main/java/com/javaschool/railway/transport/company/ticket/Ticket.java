package com.javaschool.railway.transport.company.ticket;

import com.javaschool.railway.transport.company.schedule.Schedule;
import com.javaschool.railway.transport.company.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="tickets", schema = "public", catalog = "RAILWAY_TRANSPORT_COMPANY")
@Getter
@Setter
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name="seat_number")
	private String seat_number;

	@OneToOne
	@JoinColumn(name = "user_id")
	User user;

	@ManyToOne
	@JoinColumn(name = "schedule_id")
	Schedule schedule;

}