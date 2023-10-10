package com.javaschool.railway.transport.company.domain.schedule;

import com.javaschool.railway.transport.company.domain.schedule.ScheduleEntity;
import com.javaschool.railway.transport.company.domain.schedule.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleEntity createSchedule(ScheduleEntity schedule){
        return scheduleRepository.save(schedule);
    }

    public ScheduleEntity getScheduleById(Long id) {
        return scheduleRepository.findById(id).get();
    }

    public List<ScheduleEntity> findAll(){
        return scheduleRepository.findAll();
    }

}
