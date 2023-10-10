package com.javaschool.railway.transport.company.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

  @Autowired
  private ScheduleRepository scheduleRepository;

  @GetMapping
  public List<Schedule> getAllSchedules() {
    return scheduleRepository.findAll();
  } 

  @GetMapping("/{id}")
  public Schedule getScheduleById(@PathVariable Long id) {
    return scheduleRepository.findById(id).get();
  }

  @PostMapping
  public Schedule createSchedule(@RequestBody Schedule schedule) {
    return scheduleRepository.save(schedule);
  }
}