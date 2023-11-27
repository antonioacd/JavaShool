package com.javaschool.railway.transport.company.domain.entitites;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "schedules", schema = "public", catalog = "RAILWAY_TRANSPORT_COMPANY")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
	private int occupiedSeats;

	@ManyToOne
	@JoinColumn(name = "train_id", referencedColumnName= "id", nullable = false)
	private TrainEntity train;

	@Override
	public boolean equals(Object o) {
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		return ((ScheduleEntity) o).id.equals(this.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
