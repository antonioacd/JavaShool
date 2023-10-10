package com.javaschool.railway.transport.company.domain.schedule;

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