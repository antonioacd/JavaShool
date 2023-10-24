package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.RolEntity;
import com.javaschool.railway.transport.company.domain.entitites.StationEntity;
import com.javaschool.railway.transport.company.domain.entitites.StationEntity;
import com.javaschool.railway.transport.company.domain.infodto.RolInfoDTO;
import com.javaschool.railway.transport.company.domain.infodto.StationInfoDTO;
import com.javaschool.railway.transport.company.domain.infodto.StationInfoDTO;
import com.javaschool.railway.transport.company.domain.repositories.StationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StationService {

    @Autowired
    private final StationRepository stationRepository;
    @Autowired
    private final ModelMapper modelMapper;

    public StationInfoDTO createStation(StationEntity station){
        return modelMapper.map(stationRepository.save(station), StationInfoDTO.class);
    }

    public void deleteStationById(Long id) {
        stationRepository.deleteById(id);
    }

    public StationInfoDTO getStationById(Long id) {
        StationEntity stationEntity = stationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Station not founded"));
        return modelMapper.map(stationEntity, StationInfoDTO.class);
    }

    public List<StationInfoDTO> getAllStations() {
        List<StationEntity> stations = stationRepository.findAll();

        return stations.stream()
                .map(station -> modelMapper.map(station, StationInfoDTO.class))
                .collect(Collectors.toList());
    }

}
