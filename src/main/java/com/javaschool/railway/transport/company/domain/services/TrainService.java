package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.TrainEntity;
import com.javaschool.railway.transport.company.domain.entitites.TrainEntity;
import com.javaschool.railway.transport.company.domain.infodto.TrainInfoDTO;
import com.javaschool.railway.transport.company.domain.infodto.TrainInfoDTO;
import com.javaschool.railway.transport.company.domain.repositories.StationRepository;
import com.javaschool.railway.transport.company.domain.repositories.TrainRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing train-related operations.
 */
@Service
@RequiredArgsConstructor
public class TrainService {

    private final TrainRepository trainRepository;
    private final StationRepository stationRepository;
    private final ModelMapper modelMapper;

    /**
     * Creates a new train and returns the train's information.
     *
     * @param train The train entity to be created.
     * @return A DTO (Data Transfer Object) containing the train's information.
     */
    public TrainInfoDTO createTrain(TrainEntity train) {
        // Set departure and arrival stations by reference
        train.setDepartureStation(stationRepository.getReferenceById(train.getDepartureStation().getId()));
        train.setArrivalStation(stationRepository.getReferenceById(train.getArrivalStation().getId()));
        // Save the train entity and map it to a DTO
        return modelMapper.map(trainRepository.save(train), TrainInfoDTO.class);
    }

    /**
     * Updates a train entity and returns the updated train's information.
     *
     * @param train The train entity to be updated.
     * @return A DTO (Data Transfer Object) containing the updated train's information.
     * @throws EntityNotFoundException If the train is not found.
     */
    public TrainInfoDTO updateTrain(Long id, TrainInfoDTO train) {
        // Find the existing train by ID or throw an exception if not found
        TrainEntity existingTrain = trainRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Train not found"));

        // Update the train information
        existingTrain.setSeats(train.getSeats());
        existingTrain.setDuration(train.getDuration());
        existingTrain.setTrainNumber(train.getTrainNumber());
        existingTrain.setDepartureStation(stationRepository.getReferenceById(train.getDepartureStation().getId()));
        existingTrain.setArrivalStation(stationRepository.getReferenceById(train.getArrivalStation().getId()));

        // Save the updated train entity and map it to a DTO
        return modelMapper.map(trainRepository.save(existingTrain), TrainInfoDTO.class);
    }

    /**
     * Deletes a train by its ID.
     *
     * @param id The ID of the train to be deleted.
     */
    public void deleteTrainById(Long id) {
        // Delete the train by ID
        trainRepository.deleteById(id);
    }

    /**
     * Retrieves train information by its ID.
     *
     * @param trainId The ID of the train to retrieve.
     * @return A DTO containing the train's information.
     * @throws EntityNotFoundException If the train is not found.
     */
    public TrainInfoDTO getTrainById(Long trainId) {
        // Find the train by ID or throw an exception if not found
        TrainEntity train = trainRepository.findById(trainId)
                .orElseThrow(() -> new EntityNotFoundException("Train not found"));

        // Map the train entity to a DTO
        return modelMapper.map(train, TrainInfoDTO.class);
    }

    /**
     * Retrieves a list of all trains.
     *
     * @return A list of DTOs containing train information.
     */
    public List<TrainInfoDTO> getAllTrains() {
        // Retrieve all trains from the repository
        List<TrainEntity> trains = trainRepository.findAll();
        // Map each train entity to a DTO and collect into a list
        return trains.stream()
                .map(train -> modelMapper.map(train, TrainInfoDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of trains based on departure and arrival stations.
     *
     * @param departureCity The city of departure.
     * @param arrivalCity   The city of arrival.
     * @return A list of train entities that match the criteria.
     */
    public List<TrainEntity> findTrainsByDepartureAndArrivalStations(String departureCity, String arrivalCity) {
        // Find trains based on departure and arrival stations
        return trainRepository.findTrainsByDepartureAndArrivalStations(departureCity, arrivalCity);
    }

}
