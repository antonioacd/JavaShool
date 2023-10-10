package com.javaschool.railway.transport.company.domain.station;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

  @GetMapping
  public List<StationEntity> getAllStations() {
    return stationService.findAll();
  }

}
