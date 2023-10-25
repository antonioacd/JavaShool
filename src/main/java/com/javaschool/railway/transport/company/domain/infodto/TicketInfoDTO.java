package com.javaschool.railway.transport.company.domain.infodto;

import com.javaschool.railway.transport.company.domain.entitites.ScheduleEntity;
import com.javaschool.railway.transport.company.domain.entitites.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;

@NoArgsConstructor
@Data
public class TicketInfoDTO {
    private Long id;
    private String seatNumber;
    private UserInfoDTO user;
    private ScheduleInfoDTO schedule;
}
