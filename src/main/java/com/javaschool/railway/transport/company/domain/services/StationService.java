package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.StationEntity;
import com.javaschool.railway.transport.company.domain.infodto.StationInfoDTO;
import com.javaschool.railway.transport.company.domain.repositories.StationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing station-related operations.
 */
@Service
@AllArgsConstructor
public class StationService {

    @Autowired
    private final StationRepository stationRepository;
    @Autowired
    private final ModelMapper modelMapper;

    /**
     * Creates a new station and returns the station's information.
     *
     * @param station The station entity to be created.
     * @return A DTO (Data Transfer Object) containing the station's information.
     */
    public StationInfoDTO createStation(StationEntity station) {
        return modelMapper.map(stationRepository.save(station), StationInfoDTO.class);
    }

    /**
     * Deletes a station by its ID.
     *
     * @param id The ID of the station to be deleted.
     */
    public void deleteStationById(Long id) {
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
        StationEntity stationEntity = stationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Station not found"));
        return modelMapper.map(stationEntity, StationInfoDTO.class);
    }

    /**
     * Retrieves a list of all stations.
     *
     * @return A list of DTOs containing station information.
     */
    public List<StationInfoDTO> getAllStations() {
        List<StationEntity> stations = stationRepository.findAll();

        return stations.stream()
                .map(station -> modelMapper.map(station, StationInfoDTO.class))
                .collect(Collectors.toList());
    }
}

