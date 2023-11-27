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
        return trainService.getTrainById(id);
    }

    /**
     * Deletes a train by its ID.
     *
     * @param id The ID of the train to be deleted.
     */
    @DeleteMapping("/{id}")
    public void deleteTrainById(@PathVariable Long id) {
        trainService.deleteTrainById(id);
    }

    /**
     * Retrieves a list of all trains.
     *
     * @return A list of DTOs containing train information.
     */
    @GetMapping
    public List<TrainInfoDTO> getAllTrains() {
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
        List<TrainEntity> trains = trainService.findTrainsByDepartureAndArrivalStations(departureStation, arrivalStation);
        return ResponseEntity.ok(trains);
    }
}
