package com.javaschool.railway.transport.company.domain.controllers;

import com.javaschool.railway.transport.company.domain.entitites.ScheduleEntity;
import com.javaschool.railway.transport.company.domain.infodto.ScheduleInfoDTO;
import com.javaschool.railway.transport.company.domain.services.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
  public List<ScheduleInfoDTO> searchSchedules(
          @RequestParam(required = false) String departureStation,
          @RequestParam(required = false) String arrivalStation,
          @RequestParam(required = false) String departureDate
  ) {
    return scheduleService.getSchedulesByFilters(departureStation, arrivalStation, departureDate);
  }


}