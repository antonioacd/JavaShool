package com.javaschool.railway.transport.company.domain.services;
import com.javaschool.railway.transport.company.domain.entitites.TrainEntity;
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

@Service
@AllArgsConstructor
public class TrainService {

    @Autowired
    private final TrainRepository trainRepository;
    @Autowired
    private final StationRepository stationRepository;
    @Autowired
    private final ModelMapper modelMapper;

    public TrainInfoDTO createTrain(TrainEntity train){

        System.out.println("Tren1: " + train.toString());
        System.out.println("Tren1.5" + stationRepository.getReferenceById(train.getCurrentStation().getId()).toString());

        train.setCurrentStation(stationRepository.getReferenceById(train.getCurrentStation().getId()));

        System.out.println("Tren2: " + train.toString());

        System.out.print("Tren3: " + modelMapper.map(trainRepository.save(train), TrainInfoDTO.class));

        return modelMapper.map(trainRepository.save(train), TrainInfoDTO.class);
    }

    public void deleteTrainById(Long id) {
        trainRepository.deleteById(id);
    }

    public TrainInfoDTO findTrainById(Long trainId) {
        TrainEntity train = trainRepository.findById(trainId)
                .orElseThrow(() -> new EntityNotFoundException("Tren no encontrado"));

        return modelMapper.map(train, TrainInfoDTO.class);
    }

    public List<TrainInfoDTO> getAllTrains() {
        List<TrainEntity> trains = trainRepository.findAll();

        return trains.stream()
                .map(train -> modelMapper.map(train, TrainInfoDTO.class))
                .collect(Collectors.toList());
    }

}
