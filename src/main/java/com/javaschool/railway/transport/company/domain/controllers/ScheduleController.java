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

  @PostMapping
  public ScheduleInfoDTO createSchedule(@RequestBody ScheduleEntity scheduleEntity) {
    return scheduleService.createSchedule(scheduleEntity);
  }

  @PutMapping("/{id}")
  public ScheduleInfoDTO updateSchedule(@PathVariable Long id, @RequestBody ScheduleInfoDTO updatedScheduleDTO) {
    return scheduleService.updateSchedule(id, updatedScheduleDTO);
  }

  @GetMapping
  public List<ScheduleInfoDTO> getAllSchedules() {
    return scheduleService.getAllSchedules();
  }

  @DeleteMapping("/{id}")
  public void deleteScheduleById(@PathVariable Long id) {
    scheduleService.deleteScheduleById(id);
  }

  @GetMapping("/{id}")
  public ScheduleInfoDTO getScheduleById(@PathVariable Long id) {
    return scheduleService.getScheduleById(id);
  }

  @GetMapping("/search")
  public ResponseEntity<List<ScheduleEntity>> searchSchedulesByCitiesAndDate(
          @RequestParam("departureCity") String departureCity,
          @RequestParam("arrivalCity") String arrivalCity,
          @RequestParam("selectedDate") Date selectedDate) {
    List<ScheduleEntity> schedules = scheduleService.findSchedulesByCitiesAndDate(departureCity, arrivalCity, selectedDate);
    return ResponseEntity.ok(schedules);
  }

  @GetMapping("/searchByTrainNumber")
  public ResponseEntity<List<ScheduleEntity>> searchSchedulesByTrainNumber(
          @RequestParam("trainNumber") String trainNumber) {
    List<ScheduleEntity> schedules = scheduleService.findByTrainNumber(trainNumber);
    return ResponseEntity.ok(schedules);
  }




}