package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.StationEntity;
import com.javaschool.railway.transport.company.domain.infodto.StationInfoDTO;
import com.javaschool.railway.transport.company.domain.repositories.StationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing station-related operations.
 */
@Service
public class StationService {

    private final StationRepository stationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public StationService(StationRepository stationRepository, ModelMapper modelMapper) {
        this.stationRepository = stationRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Creates a new station and returns the station's information.
     *
     * @param station The station entity to be created.
     * @return A DTO (Data Transfer Object) containing the station's information.
     */
    public StationInfoDTO createStation(StationEntity station) {
        // Save the station entity and map it to a DTO
        return modelMapper.map(stationRepository.save(station), StationInfoDTO.class);
    }

    /**
     * Updates a station entity by ID and returns the updated station's information.
     *
     * @param id             The ID of the station to be updated.
     * @param updatedStation The updated station entity.
     * @return A DTO (Data Transfer Object) containing the updated station's information.
     * @throws EntityNotFoundException If the station is not found.
     */
    public StationInfoDTO updateStation(Long id, StationInfoDTO updatedStation) {
        // Find the existing station by ID or throw an exception if not found
        StationEntity existingStation = stationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Station not found"));

        // Update the station information
        existingStation.setName(updatedStation.getName());
        existingStation.setCity(updatedStation.getCity());

        // Save the updated station entity and map it to a DTO
        return modelMapper.map(stationRepository.save(existingStation), StationInfoDTO.class);
    }


    /**
     * Deletes a station by its ID.
     *
     * @param id The ID of the station to be deleted.
     */
    public void deleteStationById(Long id) {
        // Delete the station by ID
        stationRepository.deleteById(id);
    }

    /**
     * Retrieves station information by its ID.
     *
     * @param id The ID of the station to retrieve.
     * @return A DTO containing the station's information.
     * @throws EntityNotFoundException If the station is not found.
     */
    public StationInfoDTO getStationById(Long id) {
        // Find the station by ID or throw an exception if not found
        StationEntity stationEntity = stationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Station not found"));
        // Map the station entity to a DTO
        return modelMapper.map(stationEntity, StationInfoDTO.class);
    }

    /**
     * Retrieves a list of all stations.
     *
     * @return A list of DTOs containing station information.
     */
    public List<StationInfoDTO> getAllStations() {
        // Retrieve all stations from the repository
        List<StationEntity> stations = stationRepository.findAll();
        // Map each station entity to a DTO and collect into a list
        return stations.stream()
                .map(station -> modelMapper.map(station, StationInfoDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of stations based on city.
     *
     * @param city The city to filter stations by.
     * @return A list of station entities that match the city.
     */
    public List<StationEntity> findStationsByCity(String city) {
        // Find stations based on city
        return stationRepository.findStationsByCity(city);
    }

}
