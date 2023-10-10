package com.javaschool.railway.transport.company.domain.ticket;

import com.javaschool.railway.transport.company.domain.schedule.ScheduleEntity;
import com.javaschool.railway.transport.company.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="tickets", schema = "public", catalog = "RAILWAY_TRANSPORT_COMPANY")
@Getter
@Setter
public class TicketEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name="seat_number")
	private String seat_number;

	@OneToOne
	@JoinColumn(name = "user_id")
	UserEntity user;

	@ManyToOne
	@JoinColumn(name = "schedule_id")
    ScheduleEntity schedule;

}