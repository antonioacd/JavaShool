package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.ScheduleEntity;
import com.javaschool.railway.transport.company.domain.infodto.ScheduleInfoDTO;
import com.javaschool.railway.transport.company.domain.infodto.UserInfoDTO;
import com.javaschool.railway.transport.company.domain.repositories.ScheduleRepository;
import com.javaschool.railway.transport.company.domain.repositories.StationRepository;
import com.javaschool.railway.transport.company.domain.repositories.TrainRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final TrainRepository trainRepository;
    private final StationRepository stationRepository;

    private final ModelMapper modelMapper;


    public ScheduleInfoDTO createSchedule(ScheduleEntity schedule){
        schedule.setDepartureStation(stationRepository.getReferenceById(schedule.getDepartureStation().getId()));
        schedule.setArrivalStation(stationRepository.getReferenceById(schedule.getArrivalStation().getId()));
        schedule.setTrain(trainRepository.getReferenceById(schedule.getTrain().getId()));
        return modelMapper.map(scheduleRepository.save(schedule), ScheduleInfoDTO.class);
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
