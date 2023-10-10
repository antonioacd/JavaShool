package com.javaschool.railway.transport.company.domain.train;

import com.javaschool.railway.transport.company.domain.train.TrainEntity;
import com.javaschool.railway.transport.company.domain.train.TrainRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TrainService {

    private final TrainRepository trainRepository;

    public TrainEntity createTrain(TrainEntity train){
        return trainRepository.save(train);
    }

    public TrainEntity getTrainById(Long id) {
        return trainRepository.findById(id).get();
    }

    public List<TrainEntity> findAll(){
        return trainRepository.findAll();
    }


}
