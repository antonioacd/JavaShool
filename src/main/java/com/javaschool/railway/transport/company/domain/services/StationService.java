package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.StationEntity;
import com.javaschool.railway.transport.company.domain.repositories.StationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StationService {

    private final StationRepository stationRepository;

    public StationEntity createStation(StationEntity station){
        return stationRepository.save(station);
    }

    public StationEntity getStationById(Long id) {
        return stationRepository.findById(id).get();
    }

    public List<StationEntity> findAll(){
        return stationRepository.findAll();
    }

}
