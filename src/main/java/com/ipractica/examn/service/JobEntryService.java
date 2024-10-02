package com.ipractica.examn.service;

import com.ipractica.examn.dto.JobEntryResponseDTO;
import com.ipractica.examn.dto.JobEntryRequestDTO;
import com.ipractica.examn.dto.JobPaymentDTO;

import java.time.LocalTime;
import java.util.List;

public interface JobEntryService {

    List<JobEntryResponseDTO> getAll();

    JobEntryResponseDTO findById(Integer id);

    JobEntryResponseDTO register(JobEntryRequestDTO jobEntryRequest); // Cambiado para usar JobEntryRequestDTO

    JobEntryResponseDTO update(Integer id, JobEntryRequestDTO updateJobEntry); // Cambiado para usar JobEntryRequestDTO


    JobPaymentDTO calculateJobPayment(Integer jobEntryId, LocalTime exitTime);

    List<JobPaymentDTO> getJobEntriesByLastName(String lastNameWorker);
    List<JobPaymentDTO> getJobEntriesByTimeRange(LocalTime startTime, LocalTime endTime);
    void delete(Integer id);


}
