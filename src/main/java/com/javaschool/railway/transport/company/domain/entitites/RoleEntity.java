package com.javaschool.railway.transport.company.domain.entitites;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="roles", schema = "public", catalog = "RAILWAY_TRANSPORT_COMPANY")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="name", nullable=false)
	private String name;
}