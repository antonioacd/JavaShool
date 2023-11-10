package com.javaschool.railway.transport.company.domain.infodto;

import lombok.NoArgsConstructor;
import lombok.Data;

@NoArgsConstructor
@Data
public class TicketInfoDTO {
    private Long id;
    private int seatNumber;
    private UserInfoDTO user;
    private ScheduleInfoDTO schedule;
}
