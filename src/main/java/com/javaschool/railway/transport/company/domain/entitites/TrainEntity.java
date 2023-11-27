package com.javaschool.railway.transport.company.domain.entitites;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;

@Entity
@Table(name = "trains", schema = "public", catalog = "RAILWAY_TRANSPORT_COMPANY")
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TrainEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "seats", nullable = false)
	private int seats;

	@Column(name = "duration", nullable = false)
	private Duration duration;

	@Column(name = "train_number", nullable = false)
	private String trainNumber;

	@ManyToOne
	@JoinColumn(name = "current_station_id", referencedColumnName = "id", nullable = false)
	private StationEntity departureStation;

	@ManyToOne
	@JoinColumn(name = "arrival_station_id", referencedColumnName= "id",  nullable = false)
	private StationEntity arrivalStation;

	@Override
	public boolean equals(Object o) {
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		return ((TrainEntity) o).id.equals(this.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
