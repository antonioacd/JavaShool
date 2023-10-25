package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.ScheduleEntity;
import com.javaschool.railway.transport.company.domain.infodto.ScheduleInfoDTO;
import com.javaschool.railway.transport.company.domain.repositories.ScheduleRepository;
import com.javaschool.railway.transport.company.domain.repositories.StationRepository;
import com.javaschool.railway.transport.company.domain.repositories.TrainRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing schedule-related operations.
 */
@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final TrainRepository trainRepository;
    private final StationRepository stationRepository;
    private final ModelMapper modelMapper;

    /**
     * Creates a new schedule and returns the schedule's information.
     *
     * @param schedule The schedule entity to be created.
     * @return A DTO (Data Transfer Object) containing the schedule's information.
     */
    public ScheduleInfoDTO createSchedule(ScheduleEntity schedule) {
        schedule.setTrain(trainRepository.getReferenceById(schedule.getTrain().getId()));
        return modelMapper.map(scheduleRepository.save(schedule), ScheduleInfoDTO.class);
    }

    /**
     * Deletes a schedule by its ID.
     *
     * @param id The ID of the schedule to be deleted.
     */
    public void deleteScheduleById(Long id) {
        scheduleRepository.deleteById(id);
    }

    /**
     * Retrieves schedule information by its ID.
     *
     * @param id The ID of the schedule to retrieve.
     * @return A DTO containing the schedule's information.
     * @throws EntityNotFoundException If the schedule is not found.
     */
    public ScheduleInfoDTO getScheduleById(Long id) {
        ScheduleEntity scheduleEntity = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));
        return modelMapper.map(scheduleEntity, ScheduleInfoDTO.class);
    }

    /**
     * Retrieves a list of all schedules.
     *
     * @return A list of DTOs containing schedule information.
     */
    public List<ScheduleInfoDTO> getAllSchedules() {
        List<ScheduleEntity> schedules = scheduleRepository.findAll();

        return schedules.stream()
                .map(schedule -> modelMapper.map(schedule, ScheduleInfoDTO.class))
                .collect(Collectors.toList());
    }
}
