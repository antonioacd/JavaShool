package com.javaschool.railway.transport.company.domain.controllers;

import com.javaschool.railway.transport.company.domain.entitites.TrainEntity;
import com.javaschool.railway.transport.company.domain.entitites.TrainEntity;
import com.javaschool.railway.transport.company.domain.infodto.TrainInfoDTO;
import com.javaschool.railway.transport.company.domain.services.TrainService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/trains")
@AllArgsConstructor
public class TrainController {

  private final TrainService trainService;

  @PostMapping
  public TrainInfoDTO createTrain(@RequestBody TrainEntity train) {
    return trainService.createTrain(train);
  }

  @PutMapping("/{id}")
  public TrainInfoDTO updateTrain(@PathVariable Long id, @RequestBody TrainEntity train) {
    return trainService.updateTrain(id, train);
  }

  @GetMapping("/{id}")
  public TrainInfoDTO getTrainById(@PathVariable Long id) {
    return trainService.getTrainById(id);
  }

  @DeleteMapping("/{id}")
  public void deleteTrainById(@PathVariable Long id) {
    trainService.deleteTrainById(id);
  }

  @GetMapping
  public List<TrainInfoDTO> getAllTrains() {
    return trainService.getAllTrains();
  }

  @GetMapping("/searchByDepartureStationAndArrivalStation")
  public ResponseEntity<List<TrainEntity>> findTrainsByDepartureStationAndArrivalStation(
          @RequestParam(name = "departureStation", required = false) String departureStation,
          @RequestParam(name = "arrivalStation", required = false) String arrivalStation) {
    List<TrainEntity> trains = trainService.findTrainsByDepartureAndArrivalStations(departureStation, arrivalStation);
    return ResponseEntity.ok(trains);
  }


}