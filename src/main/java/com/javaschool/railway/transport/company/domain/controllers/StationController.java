package com.javaschool.railway.transport.company.domain.controllers;

import com.javaschool.railway.transport.company.domain.entitites.StationEntity;
import com.javaschool.railway.transport.company.domain.services.StationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/stations")
@AllArgsConstructor
public class StationController {

  private final StationService stationService;

  @PostMapping
  public StationEntity createStation(@RequestBody StationEntity station) {
    return stationService.createStation(station);
  }

  @GetMapping("/{id}")
  public StationEntity getStationById(@PathVariable Long id) {
    return stationService.getStationById(id);
  }

  @DeleteMapping("/{id}")
  public void deleteStationById(@PathVariable Long id) {
    stationService.deleteStationById(id);
  }

  @GetMapping
  public List<StationEntity> getAllStations() {
    return stationService.findAll();
  }

}
