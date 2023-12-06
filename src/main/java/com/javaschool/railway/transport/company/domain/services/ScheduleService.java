package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.ScheduleEntity;
import com.javaschool.railway.transport.company.domain.infodto.ScheduleInfoDTO;
import com.javaschool.railway.transport.company.domain.repositories.ScheduleRepository;
import com.javaschool.railway.transport.company.domain.repositories.TrainRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing schedule-related operations.
 */
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final TrainRepository trainRepository;
    private final ModelMapper modelMapper;

    /**
     * Creates a new schedule and returns the schedule's information.
     *
     * @param schedule The schedule entity to be created.
     * @return A DTO (Data Transfer Object) containing the schedule's information.
     */
    @Secured({"ROLE_ADMIN"})
    public ScheduleInfoDTO createSchedule(ScheduleEntity schedule) {
        // Set the train reference using the train ID from the schedule entity
        schedule.setTrain(trainRepository.getReferenceById(schedule.getTrain().getId()));

        // Save the schedule entity and map it to a DTO
        ScheduleEntity savedSchedule = scheduleRepository.save(schedule);

        return modelMapper.map(savedSchedule, ScheduleInfoDTO.class);
    }

    /**
     * Updates an existing schedule and returns the updated schedule's information.
     *
     * @param id              The ID of the schedule to be updated.
     * @param updatedSchedule The updated schedule entity.
     * @return A DTO (Data Transfer Object) containing the updated schedule's information.
     * @throws EntityNotFoundException If the schedule is not found.
     */
    @Secured({"ROLE_ADMIN"})
    public ScheduleInfoDTO updateSchedule(Long id, ScheduleInfoDTO updatedSchedule) {

        ScheduleEntity existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));

        // Update the existing schedule with the information from the updated schedule DTO
        existingSchedule.setDepartureTime(updatedSchedule.getDepartureTime());
        existingSchedule.setArrivalTime(updatedSchedule.getArrivalTime());

        // Check if the train ID has changed and update the reference accordingly
        if (!existingSchedule.getTrain().getId().equals(updatedSchedule.getTrain().getId())) {
            existingSchedule.setTrain(trainRepository.getReferenceById(updatedSchedule.getTrain().getId()));
        }

        // Save the updated schedule entity and map it to a DTO
        return modelMapper.map(scheduleRepository.save(existingSchedule), ScheduleInfoDTO.class);
    }

    /**
     * Deletes a schedule by its ID.
     *
     * @param id The ID of the schedule to be deleted.
     */
    @Secured({"ROLE_ADMIN"})
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
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ScheduleInfoDTO getScheduleById(Long id) {
        // Find the schedule entity by ID or throw an exception if not found
        ScheduleEntity scheduleEntity = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));
        // Map the schedule entity to a DTO
        return modelMapper.map(scheduleEntity, ScheduleInfoDTO.class);
    }

    /**
     * Retrieves a list of all schedules.
     *
     * @return A list of DTOs containing schedule information.
     */
    @Secured({"ROLE_ADMIN"})
    public List<ScheduleInfoDTO> getAllSchedules() {
        // Retrieve all schedules from the repository
        List<ScheduleEntity> schedules = scheduleRepository.findAll();
        // Map each schedule entity to a DTO and collect into a list
        return schedules.stream()
                .map(schedule -> modelMapper.map(schedule, ScheduleInfoDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of schedules based on departure city, arrival city, and selected date.
     *
     * @param departureCity The departure city.
     * @param arrivalCity   The arrival city.
     * @param selectedDate  The selected date.
     * @return A list of schedule entities that match the criteria.
     */
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public List<ScheduleEntity> findSchedulesByCitiesAndDate(String departureCity, String arrivalCity, Date selectedDate) {
        // Format the selected date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(selectedDate);

        // Find schedules based on cities and date
        return scheduleRepository.findSchedulesByCitiesAndDate(departureCity, arrivalCity, formattedDate);
    }

    /**
     * Retrieves a list of schedules based on train number.
     *
     * @param trainNumber The train number.
     * @return A list of schedule entities that match the train number.
     */
    @Secured({"ROLE_ADMIN"})
    public List<ScheduleEntity> findByTrainNumber(String trainNumber) {
        // Find schedules based on train number
        return scheduleRepository.findSchedulesByTrainNumber(trainNumber);
    }

}
