package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.TrainEntity;
import com.javaschool.railway.transport.company.domain.entitites.TrainEntity;
import com.javaschool.railway.transport.company.domain.infodto.TrainInfoDTO;
import com.javaschool.railway.transport.company.domain.infodto.TrainInfoDTO;
import com.javaschool.railway.transport.company.domain.repositories.StationRepository;
import com.javaschool.railway.transport.company.domain.repositories.TrainRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing train-related operations.
 */
@Service
@AllArgsConstructor
public class TrainService {

    @Autowired
    private final TrainRepository trainRepository;
    @Autowired
    private final StationRepository stationRepository;
    @Autowired
    private final ModelMapper modelMapper;

    /**
     * Creates a new train and returns the train's information.
     *
     * @param train The train entity to be created.
     * @return A DTO (Data Transfer Object) containing the train's information.
     */
    public TrainInfoDTO createTrain(TrainEntity train) {
        train.setDepartureStation(stationRepository.getReferenceById(train.getDepartureStation().getId()));
        train.setArrivalStation(stationRepository.getReferenceById(train.getArrivalStation().getId()));
        return modelMapper.map(trainRepository.save(train), TrainInfoDTO.class);
    }

    /**
     * Updates a train entity and returns the updated train's information.
     *
     * @param train The train entity to be updated.
     * @return A DTO (Data Transfer Object) containing the updated train's information.
     * @throws EntityNotFoundException If the train is not found.
     */
    public TrainInfoDTO updateTrain(Long id, TrainEntity train) {
        TrainEntity existingTrain = trainRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Train not found"));

        // Update the train information
        existingTrain.setSeats(train.getSeats());
        existingTrain.setDuration(train.getDuration());
        existingTrain.setTrainNumber(train.getTrainNumber());
        existingTrain.setDepartureStation(stationRepository.getReferenceById(train.getDepartureStation().getId()));
        existingTrain.setArrivalStation(stationRepository.getReferenceById(train.getArrivalStation().getId()));

        return modelMapper.map(trainRepository.save(existingTrain), TrainInfoDTO.class);
    }


    /**
     * Deletes a train by its ID.
     *
     * @param id The ID of the train to be deleted.
     */
    public void deleteTrainById(Long id) {
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
        TrainEntity train = trainRepository.findById(trainId)
                .orElseThrow(() -> new EntityNotFoundException("Train not found"));

        return modelMapper.map(train, TrainInfoDTO.class);
    }

    /**
     * Retrieves a list of all trains.
     *
     * @return A list of DTOs containing train information.
     */
    public List<TrainInfoDTO> getAllTrains() {
        List<TrainEntity> trains = trainRepository.findAll();

        return trains.stream()
                .map(train -> modelMapper.map(train, TrainInfoDTO.class))
                .collect(Collectors.toList());
    }

    public List<TrainEntity> findTrainsByDepartureStationAndArrivalStation(String departureCity, String arrivalCity) {
        return trainRepository.findTrainsByDepartureStationAndArrivalStation(departureCity, arrivalCity);
    }

    public List<TrainEntity> findTrainsByDepartureStation(String departureCity) {
        return trainRepository.findTrainsByDepartureStation(departureCity);
    }

    public List<TrainEntity> findTrainsByArrivalStation(String arrivalCity) {
        return trainRepository.findTrainsByArrivalStation(arrivalCity);
    }
}
