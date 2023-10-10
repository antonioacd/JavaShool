package com.javaschool.railway.transport.company.train;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trains")
public class TrainController {

  @Autowired
  private TrainRepository trainRepository;

  @GetMapping
  public List<Train> getAllTrains() {
    return trainRepository.findAll();
  } 

  @GetMapping("/{id}")
  public Train getTrainById(@PathVariable Long id) {
    return trainRepository.findById(id).get();
  }

  @PostMapping
  public Train createTrain(@RequestBody Train train) {
    return trainRepository.save(train);
  }
}