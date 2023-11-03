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
          @RequestParam("departureStation") String departureStation,
          @RequestParam("arrivalStation") String arrivalStation) {
    List<TrainEntity> trains = trainService.findTrainsByDepartureStationAndArrivalStation(departureStation, arrivalStation);
    return ResponseEntity.ok(trains);
  }

  @GetMapping("/searchTrainsByDepartureStation")
  public ResponseEntity<List<TrainEntity>> findTrainsByDepartureStation(
          @RequestParam("departureStation") String departureStation) {
    List<TrainEntity> trains = trainService.findTrainsByDepartureStation(departureStation);
    return ResponseEntity.ok(trains);
  }

  @GetMapping("/searchTrainsByArrivalStation")
  public ResponseEntity<List<TrainEntity>> findTrainsByArrivalStation(
          @RequestParam("arrivalStation") String arrivalStation) {
    List<TrainEntity> trains = trainService.findTrainsByArrivalStation(arrivalStation);
    return ResponseEntity.ok(trains);
  }

}