package com.javaschool.railway.transport.company.domain.station;

import com.javaschool.railway.transport.company.domain.station.StationEntity;
import com.javaschool.railway.transport.company.domain.station.StationRepository;
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
