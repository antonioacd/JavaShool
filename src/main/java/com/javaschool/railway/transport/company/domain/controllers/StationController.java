package com.javaschool.railway.transport.company.domain.controllers;

import com.javaschool.railway.transport.company.domain.entitites.StationEntity;
import com.javaschool.railway.transport.company.domain.entitites.StationEntity;
import com.javaschool.railway.transport.company.domain.infodto.StationInfoDTO;
import com.javaschool.railway.transport.company.domain.services.StationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/stations")
@AllArgsConstructor
public class StationController {

  private final StationService stationService;

  @PostMapping
  public StationInfoDTO createStation(@RequestBody StationEntity station) {
    return stationService.createStation(station);
  }

  @PutMapping("/{id}")
  public StationInfoDTO updateStation(@PathVariable Long id, @RequestBody StationInfoDTO updatedStation) {
    return stationService.updateStation(id, updatedStation);
  }

  @GetMapping("/{id}")
  public StationInfoDTO getStationById(@PathVariable Long id) {
    return stationService.getStationById(id);
  }

  @DeleteMapping("/{id}")
  public void deleteStationById(@PathVariable Long id) {
    stationService.deleteStationById(id);
  }

  @GetMapping
  public List<StationInfoDTO> getAllStations() {
    return stationService.getAllStations();
  }

  @GetMapping("/search")
  public ResponseEntity<List<StationEntity>> searchStations(
          @RequestParam("city") String city) {
    List<StationEntity> stations = stationService.findStationsByCity(city);
    return ResponseEntity.ok(stations);
  }

}
