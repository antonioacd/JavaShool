package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.ScheduleEntity;
import com.javaschool.railway.transport.company.domain.repositories.ScheduleRepository;
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

    public void deleteScheduleById(Long id) {
        scheduleRepository.deleteById(id);
    }


    public List<ScheduleEntity> findAll(){
        return scheduleRepository.findAll();
    }

}
