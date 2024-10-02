package com.ipractica.examn.mapper;

import com.ipractica.examn.dto.JobEntryRequestDTO;
import com.ipractica.examn.dto.JobEntryResponseDTO;
import com.ipractica.examn.dto.JobPaymentDTO;
import com.ipractica.examn.model.entity.JobEntry;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;

@Component
public class JobEntryMapper {

    private final ModelMapper modelMapper;

    public JobEntryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    // Convierte la entidad JobEntry a JobEntryResponseDTO
    public JobEntryResponseDTO toResponseDTO(JobEntry jobEntry) {
        return modelMapper.map(jobEntry, JobEntryResponseDTO.class);
    }

    // Convierte JobEntryRequestDTO a entidad JobEntry
    public JobEntry toEntity(JobEntryRequestDTO jobEntryRequestDTO) {
        return modelMapper.map(jobEntryRequestDTO, JobEntry.class);
    }

    public JobPaymentDTO toJobPaymentDTO(JobEntry jobEntry, LocalTime exitTime) {
        JobPaymentDTO jobpaymentDTO = modelMapper.map(jobEntry, JobPaymentDTO.class);

        Duration duration = Duration.between(jobEntry.getHoraInicio(), exitTime);

        long hoursWorked = duration.toHours();
        long minutesWorked = duration.toMinutes() % 60;

        // Formatear las horas trabajadas como cadena
        jobpaymentDTO.setHorasTrabajadas(String.format("%d horas y %d minutos", hoursWorked, minutesWorked));
        jobpaymentDTO.setTotal(jobEntry.getTotal());

        return jobpaymentDTO;
    }
}