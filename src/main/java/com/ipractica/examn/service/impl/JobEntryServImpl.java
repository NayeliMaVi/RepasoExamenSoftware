package com.ipractica.examn.service.impl;


import com.ipractica.examn.dto.JobEntryRequestDTO;
import com.ipractica.examn.dto.JobEntryResponseDTO;
import com.ipractica.examn.dto.JobPaymentDTO;
import com.ipractica.examn.exception.JobEntryExistingException;
import com.ipractica.examn.exception.JobNotFoundException;
import com.ipractica.examn.mapper.JobEntryMapper;
import com.ipractica.examn.model.entity.JobEntry;
import com.ipractica.examn.repository.JobEntryRepository;
import com.ipractica.examn.service.JobEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class JobEntryServImpl implements JobEntryService {
    private final JobEntryRepository jobEntryRepository;
    private final JobEntryMapper jobEntryMapper;

    @Transactional(readOnly = true)
    @Override
    public List<JobEntryResponseDTO> getAll() {
        List<JobEntry> jobEntries = jobEntryRepository.findAll();
        return jobEntries.stream().map(jobEntryMapper::toResponseDTO).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public JobEntryResponseDTO findById(Integer id){
        JobEntry jobEntry = jobEntryRepository.findById(id).orElseThrow(() -> new JobNotFoundException("Job not found with id: " + id));
        return jobEntryMapper.toResponseDTO(jobEntry);
    }


    @Transactional
    @Override
    public JobEntryResponseDTO register(JobEntryRequestDTO jobEntryRequestDTO){
        jobEntryRepository.findByNombreAndApPaternoAndHoraInicio(jobEntryRequestDTO.getNombre(),jobEntryRequestDTO.getApPaterno(),jobEntryRequestDTO.getHoraInicio())
                .ifPresent(jobEntry -> {
                    throw new JobEntryExistingException("El usuario con el mismo nombre, apellido y hora ya existe");
                });
        JobEntry jobEntry = jobEntryMapper.toEntity(jobEntryRequestDTO);
        jobEntry.setHoraFin(LocalTime.now());
        return jobEntryMapper.toResponseDTO(jobEntryRepository.save(jobEntry));
    }

    @Transactional
    @Override
    public JobEntryResponseDTO update(Integer id, JobEntryRequestDTO jobEntryRequestDTO){
        JobEntry jobEntryFromDb = jobEntryRepository.findById(id).orElseThrow(() -> new JobNotFoundException("job not found with id: " + id));
        jobEntryFromDb.setNombre(jobEntryRequestDTO.getNombre());
        jobEntryFromDb.setApPaterno(jobEntryRequestDTO.getApPaterno());
        jobEntryFromDb.setHoraFin(jobEntryRequestDTO.getHoraFin());
        jobEntryFromDb.setHoraInicio(jobEntryRequestDTO.getHoraInicio());
        return jobEntryMapper.toResponseDTO(jobEntryRepository.save(jobEntryFromDb));
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        JobEntry treatment = jobEntryRepository.findById(id).orElseThrow(() -> new JobNotFoundException("job found with id: " + id));
        jobEntryRepository.delete(treatment);
    }

    @Override
    public JobPaymentDTO calculateJobPayment(Integer id, LocalTime exitTime){
        JobEntry jobEntryFromDb = jobEntryRepository.findById(id).orElseThrow(() -> new JobNotFoundException("job not found with id: " + id));
        Duration duration = Duration.between(jobEntryFromDb.getHoraInicio(), exitTime);
        long hoursWorked = duration.toHours();

        BigDecimal total;
        if (hoursWorked < 4) {
            total = new BigDecimal(200);
        } else if (hoursWorked <= 8) {
            total = new BigDecimal(500);
        } else {
            total = new BigDecimal(700);
        }

        jobEntryFromDb.setHoraFin(exitTime);
        jobEntryFromDb.setTotal(total);
        return jobEntryMapper.toJobPaymentDTO(jobEntryRepository.save(jobEntryFromDb),exitTime);
    }

    @Override
    public List<JobPaymentDTO> getJobEntriesByLastName(String lastNameWorker){
        List<JobEntry> jobEntries = jobEntryRepository.findByApPaterno(lastNameWorker);
        return jobEntries.stream()
                .map(jobEntry -> jobEntryMapper.toJobPaymentDTO(jobEntry, jobEntry.getHoraFin())) // Asumiendo que necesitas la hora de salida
                .collect(Collectors.toList());
    }

    @Override
    public List<JobPaymentDTO> getJobEntriesByTimeRange(LocalTime startTime, LocalTime endTime) {
        // Busca las entradas que caen dentro del rango de tiempo especificado
        List<JobEntry> jobEntries = jobEntryRepository.findByHoraInicioBetween(startTime, endTime);
        return jobEntries.stream()
                .map(jobEntry -> jobEntryMapper.toJobPaymentDTO(jobEntry, jobEntry.getHoraFin())) // Asumiendo que necesitas la hora de salida
                .collect(Collectors.toList());
    }
}
