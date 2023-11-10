package com.javaschool.railway.transport.company.domain.controllers;

import com.javaschool.railway.transport.company.domain.entitites.ScheduleEntity;
import com.javaschool.railway.transport.company.domain.infodto.ScheduleInfoDTO;
import com.javaschool.railway.transport.company.domain.services.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/schedules")
@AllArgsConstructor
public class ScheduleController {

    private ScheduleService scheduleService;

    /**
     * Creates a new schedule.
     *
     * @param scheduleEntity The schedule entity to be created.
     * @return A DTO (Data Transfer Object) containing the schedule's information.
     */
    @PostMapping
    public ScheduleInfoDTO createSchedule(@RequestBody ScheduleEntity scheduleEntity) {
        // Delegate schedule creation logic to the ScheduleService and return the DTO
        return scheduleService.createSchedule(scheduleEntity);
    }

    /**
     * Updates an existing schedule.
     *
     * @param id                The ID of the schedule to be updated.
     * @param updatedScheduleDTO The updated schedule DTO.
     * @return A DTO (Data Transfer Object) containing the updated schedule's information.
     */
    @PutMapping("/{id}")
    public ScheduleInfoDTO updateSchedule(@PathVariable Long id, @RequestBody ScheduleInfoDTO updatedScheduleDTO) {
        // Delegate schedule update logic to the ScheduleService and return the DTO
        return scheduleService.updateSchedule(id, updatedScheduleDTO);
    }

    /**
     * Retrieves a list of all schedules.
     *
     * @return A list of DTOs containing schedule information.
     */
    @GetMapping
    public List<ScheduleInfoDTO> getAllSchedules() {
        // Retrieve all schedules from the service and return the list of DTOs
        return scheduleService.getAllSchedules();
    }

    /**
     * Deletes a schedule by its ID.
     *
     * @param id The ID of the schedule to be deleted.
     */
    @DeleteMapping("/{id}")
    public void deleteScheduleById(@PathVariable Long id) {
        // Delegate schedule deletion logic to the ScheduleService
        scheduleService.deleteScheduleById(id);
    }

    /**
     * Retrieves schedule information by its ID.
     *
     * @param id The ID of the schedule to retrieve.
     * @return A DTO containing the schedule's information.
     */
    @GetMapping("/{id}")
    public ScheduleInfoDTO getScheduleById(@PathVariable Long id) {
        // Delegate schedule retrieval logic to the ScheduleService and return the DTO
        return scheduleService.getScheduleById(id);
    }

    /**
     * Searches for schedules based on departure city, arrival city, and selected date.
     *
     * @param departureCity The departure city.
     * @param arrivalCity   The arrival city.
     * @param selectedDate  The selected date.
     * @return ResponseEntity containing a list of schedule entities.
     */
    @GetMapping("/search")
    public ResponseEntity<List<ScheduleEntity>> searchSchedulesByCitiesAndDate(
            @RequestParam("departureCity") String departureCity,
            @RequestParam("arrivalCity") String arrivalCity,
            @RequestParam("selectedDate") Date selectedDate) {
        // Search for schedules based on the provided parameters and return the ResponseEntity
        List<ScheduleEntity> schedules = scheduleService.findSchedulesByCitiesAndDate(departureCity, arrivalCity, selectedDate);
        return ResponseEntity.ok(schedules);
    }

    /**
     * Searches for schedules based on train number.
     *
     * @param trainNumber The train number.
     * @return ResponseEntity containing a list of schedule entities.
     */
    @GetMapping("/searchByTrainNumber")
    public ResponseEntity<List<ScheduleEntity>> searchSchedulesByTrainNumber(
            @RequestParam("trainNumber") String trainNumber) {
        // Search for schedules based on the provided train number and return the ResponseEntity
        List<ScheduleEntity> schedules = scheduleService.findByTrainNumber(trainNumber);
        return ResponseEntity.ok(schedules);
    }
}
