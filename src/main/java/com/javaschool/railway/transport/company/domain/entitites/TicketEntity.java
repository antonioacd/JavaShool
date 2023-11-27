package com.javaschool.railway.transport.company.domain.entitites;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tickets", schema = "public", catalog = "RAILWAY_TRANSPORT_COMPANY")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "seat_number", nullable = false)
	private int seatNumber;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private UserEntity user;

	@ManyToOne
	@JoinColumn(name = "schedule_id", referencedColumnName= "id", nullable = false)
	private ScheduleEntity schedule;

	@Override
	public boolean equals(Object o) {
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		return ((TicketEntity) o).id.equals(this.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
