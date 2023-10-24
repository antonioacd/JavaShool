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

  @GetMapping
  public List<ScheduleEntity> getAllSchedules() {
    return scheduleService.findAll();
  }

  @DeleteMapping("/{id}")
  public void deleteScheduleById(@PathVariable Long id) {
    scheduleService.deleteScheduleById(id);
  }

  @GetMapping("/{id}")
  public ScheduleEntity getScheduleById(@PathVariable Long id) {
    return scheduleService.getScheduleById(id);
  }


}