package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.ScheduleEntity;
import com.javaschool.railway.transport.company.domain.infodto.ScheduleInfoDTO;
import com.javaschool.railway.transport.company.domain.repositories.ScheduleRepository;
import com.javaschool.railway.transport.company.domain.repositories.StationRepository;
import com.javaschool.railway.transport.company.domain.repositories.TrainRepository;
import com.sun.tools.jconsole.JConsoleContext;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.text.SimpleDateFormat;

/**
 * Service class for managing schedule-related operations.
 */
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final TrainRepository trainRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, TrainRepository trainRepository, ModelMapper modelMapper) {
        this.scheduleRepository = scheduleRepository;
        this.trainRepository = trainRepository;
        this.modelMapper = modelMapper;
    }



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
     * Updates an existing schedule and returns the updated schedule's information.
     *
     * @param id              The ID of the schedule to be updated.
     * @param updatedSchedule The updated schedule entity.
     * @return A DTO (Data Transfer Object) containing the updated schedule's information.
     * @throws EntityNotFoundException If the schedule is not found.
     */
    public ScheduleInfoDTO updateSchedule(Long id, ScheduleInfoDTO updatedSchedule) {

        ScheduleEntity existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));

        existingSchedule.setDepartureTime(updatedSchedule.getDepartureTime());
        existingSchedule.setArrivalTime(updatedSchedule.getArrivalTime());

        if (!existingSchedule.getTrain().getId().equals(updatedSchedule.getTrain().getId())) {
            existingSchedule.setTrain(trainRepository.getReferenceById(updatedSchedule.getTrain().getId()));
        }

        return modelMapper.map(scheduleRepository.save(existingSchedule), ScheduleInfoDTO.class);
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

    public List<ScheduleEntity> findSchedulesByCitiesAndDate(String departureCity, String arrivalCity, Date selectedDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(selectedDate);
        System.out.println("Data formateada:" + formattedDate);
        return scheduleRepository.findSchedulesByCitiesAndDate(departureCity, arrivalCity, formattedDate);
    }

    public List<ScheduleEntity> findByTrainNumber(String trainNumber) {
        return scheduleRepository.findSchedulesByTrainNumber(trainNumber);
    }

}
