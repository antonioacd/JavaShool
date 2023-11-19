package com.javaschool.railway.transport.company.domain.entitites;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stations", schema = "public", catalog = "RAILWAY_TRANSPORT_COMPANY")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "city", nullable = false)
	private String city;
}