package com.javaschool.railway.transport.company.domain.controllers;

import com.javaschool.railway.transport.company.domain.entitites.ScheduleEntity;
import com.javaschool.railway.transport.company.domain.services.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

  private ScheduleService scheduleService;

  @PostMapping
  public ScheduleEntity createSchedule(@RequestBody ScheduleEntity scheduleEntity) {
    return scheduleService.createSchedule(scheduleEntity);
  }

  @GetMapping
  public List<ScheduleEntity> getAllSchedules() {
    return scheduleService.findAll();
  } 

  @GetMapping("/{id}")
  public ScheduleEntity getScheduleById(@PathVariable Long id) {
    return scheduleService.getScheduleById(id);
  }


}