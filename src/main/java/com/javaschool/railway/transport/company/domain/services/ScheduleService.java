package com.javaschool.railway.transport.company.domain.services;

import com.javaschool.railway.transport.company.domain.entitites.ScheduleEntity;
import com.javaschool.railway.transport.company.domain.infodto.ScheduleInfoDTO;
import com.javaschool.railway.transport.company.domain.repositories.ScheduleRepository;
import com.javaschool.railway.transport.company.domain.repositories.StationRepository;
import com.javaschool.railway.transport.company.domain.repositories.TrainRepository;
import com.sun.tools.jconsole.JConsoleContext;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.text.SimpleDateFormat;

/**
 * Service class for managing schedule-related operations.
 */
@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final TrainRepository trainRepository;
    private final StationRepository stationRepository;
    private final ModelMapper modelMapper;

    /**
     * Creates a new schedule and returns the schedule's information.
     *
     * @param schedule The schedule entity to be created.
     * @return A DTO (Data Transfer Object) containing the schedule's information.
     */
    public ScheduleInfoDTO createSchedule(ScheduleEntity schedule) {
        schedule.setTrain(trainRepository.getReferenceById(schedule.getTrain().getId()));
        return modelMapper.map(scheduleRepository.save(schedule), ScheduleInfoDTO.class);
    }

    /**
     * Updates an existing schedule and returns the updated schedule's information.
     *
     * @param id              The ID of the schedule to be updated.
     * @param updatedSchedule The updated schedule entity.
     * @return A DTO (Data Transfer Object) containing the updated schedule's information.
     * @throws EntityNotFoundException If the schedule is not found.
     */
    public ScheduleInfoDTO updateSchedule(Long id, ScheduleInfoDTO updatedSchedule) {
        // Busca el schedule existente por su ID
        ScheduleEntity existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));

        // Actualiza los campos relevantes con los valores del updatedSchedule
        existingSchedule.setDepartureTime(updatedSchedule.getDepartureTime());
        existingSchedule.setArrivalTime(updatedSchedule.getArrivalTime());

        // Actualiza el tren asociado (si es diferente)
        if (!existingSchedule.getTrain().getId().equals(updatedSchedule.getTrain().getId())) {
            existingSchedule.setTrain(trainRepository.getReferenceById(updatedSchedule.getTrain().getId()));
        }

        // Guarda y devuelve el schedule actualizado
        return modelMapper.map(scheduleRepository.save(existingSchedule), ScheduleInfoDTO.class);
    }


    /**
     * Deletes a schedule by its ID.
     *
     * @param id The ID of the schedule to be deleted.
     */
    public void deleteScheduleById(Long id) {
        scheduleRepository.deleteById(id);
    }

    /**
     * Retrieves schedule information by its ID.
     *
     * @param id The ID of the schedule to retrieve.
     * @return A DTO containing the schedule's information.
     * @throws EntityNotFoundException If the schedule is not found.
     */
    public ScheduleInfoDTO getScheduleById(Long id) {
        ScheduleEntity scheduleEntity = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));
        return modelMapper.map(scheduleEntity, ScheduleInfoDTO.class);
    }

    /**
     * Retrieves a list of all schedules.
     *
     * @return A list of DTOs containing schedule information.
     */
    public List<ScheduleInfoDTO> getAllSchedules() {
        List<ScheduleEntity> schedules = scheduleRepository.findAll();

        return schedules.stream()
                .map(schedule -> modelMapper.map(schedule, ScheduleInfoDTO.class))
                .collect(Collectors.toList());
    }

    public List<ScheduleInfoDTO> getSchedulesByFilters(String departureStation, String arrivalStation, String departureDate) {
        List<ScheduleEntity> schedules = scheduleRepository.findAll();

        if (departureStation != null) {
            schedules = schedules.stream()
                    .filter(schedule -> schedule.getTrain().getDepartureStation().getCity().equals(departureStation))
                    .collect(Collectors.toList());
        }

        if (arrivalStation != null) {
            schedules = schedules.stream()
                    .filter(schedule -> schedule.getTrain().getArrivalStation().getCity().equals(arrivalStation))
                    .collect(Collectors.toList());
        }

        if (departureDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date date = sdf.parse(departureDate);

                for (ScheduleEntity schedule: schedules) {
                    SimpleDateFormat scheduleSdf = new SimpleDateFormat("yyyy-MM-dd");
                    String scheduleDate = scheduleSdf.format(schedule.getDepartureTime());
                    String inputDate = scheduleSdf.format(date);
                    System.out.println("Comparacion " + scheduleDate + " - " + inputDate);
                }

                // Filtra las fechas utilizando la parte de la fecha (ignorando la hora)
                schedules = schedules.stream()
                        .filter(schedule -> {
                            SimpleDateFormat scheduleSdf = new SimpleDateFormat("yyyy-MM-dd");
                            String scheduleDate = scheduleSdf.format(schedule.getDepartureTime());
                            String inputDate = scheduleSdf.format(date);
                            return scheduleDate.equals(inputDate);
                        })
                        .collect(Collectors.toList());

            } catch (ParseException e) {
                System.out.print("Error: " + e.getMessage());
            }
        }

        System.out.println("Schedules" + schedules);

        return schedules.stream()
                .map(schedule -> modelMapper.map(schedule, ScheduleInfoDTO.class))
                .collect(Collectors.toList());
    }

}
