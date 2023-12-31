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

    private final ScheduleService scheduleService;

    /**
     * Creates a new schedule.
     *
     * @param scheduleEntity The schedule entity to be created.
     * @return A DTO (Data Transfer Object) containing the schedule's information.
     */
    @PostMapping
    public ScheduleInfoDTO createSchedule(@RequestBody ScheduleEntity scheduleEntity) {
        return scheduleService.createSchedule(scheduleEntity);
    }

    /**
     * Updates an existing schedule.
     *
     * @param id                 The ID of the schedule to be updated.
     * @param updatedScheduleDTO The updated schedule DTO.
     * @return A DTO (Data Transfer Object) containing the updated schedule's information.
     */
    @PutMapping("/{id}")
    public ScheduleInfoDTO updateSchedule(@PathVariable Long id, @RequestBody ScheduleInfoDTO updatedScheduleDTO) {
        return scheduleService.updateSchedule(id, updatedScheduleDTO);
    }

    /**
     * Retrieves a list of all schedules.
     *
     * @return A list of DTOs containing schedule information.
     */
    @GetMapping
    public List<ScheduleInfoDTO> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    /**
     * Deletes a schedule by its ID.
     *
     * @param id The ID of the schedule to be deleted.
     */
    @DeleteMapping("/{id}")
    public void deleteScheduleById(@PathVariable Long id) {
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
            @RequestParam(name = "departureCity", required = false) String departureCity,
            @RequestParam(name = "arrivalCity", required = false) String arrivalCity,
            @RequestParam(name = "selectedDate", required = false) Date selectedDate) {
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
        List<ScheduleEntity> schedules = scheduleService.findByTrainNumber(trainNumber);
        return ResponseEntity.ok(schedules);
    }
}
