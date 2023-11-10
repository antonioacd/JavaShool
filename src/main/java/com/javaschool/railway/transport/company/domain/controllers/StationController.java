package com.javaschool.railway.transport.company.domain.controllers;

import com.javaschool.railway.transport.company.domain.entitites.StationEntity;
import com.javaschool.railway.transport.company.domain.infodto.StationInfoDTO;
import com.javaschool.railway.transport.company.domain.services.StationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/stations")
@AllArgsConstructor
public class StationController {

    private final StationService stationService;

    /**
     * Creates a new station.
     *
     * @param station The station entity to be created.
     * @return A DTO (Data Transfer Object) containing the station's information.
     */
    @PostMapping
    public StationInfoDTO createStation(@RequestBody StationEntity station) {
        // Delegate station creation logic to the StationService and return the DTO
        return stationService.createStation(station);
    }

    /**
     * Updates an existing station.
     *
     * @param id             The ID of the station to be updated.
     * @param updatedStation The updated station DTO.
     * @return A DTO (Data Transfer Object) containing the updated station's information.
     */
    @PutMapping("/{id}")
    public StationInfoDTO updateStation(@PathVariable Long id, @RequestBody StationInfoDTO updatedStation) {
        // Delegate station update logic to the StationService and return the DTO
        return stationService.updateStation(id, updatedStation);
    }

    /**
     * Retrieves station information by its ID.
     *
     * @param id The ID of the station to retrieve.
     * @return A DTO containing the station's information.
     */
    @GetMapping("/{id}")
    public StationInfoDTO getStationById(@PathVariable Long id) {
        // Delegate station retrieval logic to the StationService and return the DTO
        return stationService.getStationById(id);
    }

    /**
     * Deletes a station by its ID.
     *
     * @param id The ID of the station to be deleted.
     */
    @DeleteMapping("/{id}")
    public void deleteStationById(@PathVariable Long id) {
        // Delegate station deletion logic to the StationService
        stationService.deleteStationById(id);
    }

    /**
     * Retrieves a list of all stations.
     *
     * @return A list of DTOs containing station information.
     */
    @GetMapping
    public List<StationInfoDTO> getAllStations() {
        // Retrieve all stations from the service and return the list of DTOs
        return stationService.getAllStations();
    }

    /**
     * Searches for stations based on city.
     *
     * @param city The city to search for.
     * @return ResponseEntity containing a list of station entities.
     */
    @GetMapping("/search")
    public ResponseEntity<List<StationEntity>> searchStations(
            @RequestParam("city") String city) {
        // Search for stations based on the provided city and return the ResponseEntity
        List<StationEntity> stations = stationService.findStationsByCity(city);
        return ResponseEntity.ok(stations);
    }
}
