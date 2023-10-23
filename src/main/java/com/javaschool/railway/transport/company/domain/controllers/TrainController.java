package com.javaschool.railway.transport.company.domain.controllers;

import com.javaschool.railway.transport.company.domain.entitites.TrainEntity;
import com.javaschool.railway.transport.company.domain.services.TrainService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/trains")
@AllArgsConstructor
public class TrainController {

  private final TrainService trainService;

  @PostMapping
  public TrainEntity createTrain(@RequestBody TrainEntity train) {
    return trainService.createTrain(train);
  }

  @GetMapping("/{id}")
  public TrainEntity getTrainById(@PathVariable Long id) {
    return trainService.getTrainById(id);
  }

  @DeleteMapping("/{id}")
  public void deleteTrainById(@PathVariable Long id) {
    trainService.deleteTrainById(id);
  }

  @GetMapping
  public List<TrainEntity> getAllTrains() {
    return trainService.findAll();
  } 

}