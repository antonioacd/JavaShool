package com.javaschool.railway.transport.company.station;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
public class StationController {

  @Autowired
  private StationRepository stationRepository;

  @GetMapping
  public List<Station> getAllStations() {
    return stationRepository.findAll();
  } 

  @GetMapping("/{id}")
  public Station getStationById(@PathVariable Long id) {
    return stationRepository.findById(id).get();
  }

  @PostMapping
  public Station createStation(@RequestBody Station station) {
    return stationRepository.save(station);
  }
}