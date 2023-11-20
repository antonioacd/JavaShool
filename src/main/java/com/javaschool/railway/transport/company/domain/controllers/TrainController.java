package com.javaschool.railway.transport.company.domain.controllers;

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

    /**
     * Creates a new train.
     *
     * @param train The train entity to be created.
     * @return A DTO (Data Transfer Object) containing the train's information.
     */
    @PostMapping
    public TrainInfoDTO createTrain(@RequestBody TrainEntity train) {
        // Delegate train creation logic to the TrainService and return the DTO
        return trainService.createTrain(train);
    }

    /**
     * Updates an existing train.
     *
     * @param id    The ID of the train to be updated.
     * @param train The updated train entity.
     * @return A DTO (Data Transfer Object) containing the updated train's information.
     */
    @PutMapping("/{id}")
    public TrainInfoDTO updateTrain(@PathVariable Long id, @RequestBody TrainInfoDTO train) {
        // Delegate train update logic to the TrainService and return the DTO
        return trainService.updateTrain(id, train);
    }

    /**
     * Retrieves train information by its ID.
     *
     * @param id The ID of the train to retrieve.
     * @return A DTO containing the train's information.
     */
    @GetMapping("/{id}")
    public TrainInfoDTO getTrainById(@PathVariable Long id) {
        // Delegate train retrieval logic to the TrainService and return the DTO
        return trainService.getTrainById(id);
    }

    /**
     * Deletes a train by its ID.
     *
     * @param id The ID of the train to be deleted.
     */
    @DeleteMapping("/{id}")
    public void deleteTrainById(@PathVariable Long id) {
        // Delegate train deletion logic to the TrainService
        trainService.deleteTrainById(id);
    }

    /**
     * Retrieves a list of all trains.
     *
     * @return A list of DTOs containing train information.
     */
    @GetMapping
    public List<TrainInfoDTO> getAllTrains() {
        // Retrieve all trains from the service and return the list of DTOs
        return trainService.getAllTrains();
    }

    /**
     * Searches for trains based on departure and arrival stations.
     *
     * @param departureStation The departure station.
     * @param arrivalStation   The arrival station.
     * @return ResponseEntity containing a list of train entities.
     */
    @GetMapping("/searchByDepartureStationAndArrivalStation")
    public ResponseEntity<List<TrainEntity>> findTrainsByDepartureStationAndArrivalStation(
            @RequestParam(name = "departureStation", required = false) String departureStation,
            @RequestParam(name = "arrivalStation", required = false) String arrivalStation) {
        // Search for trains based on the provided departure and arrival stations and return the ResponseEntity
        List<TrainEntity> trains = trainService.findTrainsByDepartureAndArrivalStations(departureStation, arrivalStation);
        return ResponseEntity.ok(trains);
    }
}
