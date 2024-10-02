package com.ipractica.examn.repository;

import com.ipractica.examn.model.entity.JobEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface JobEntryRepository extends JpaRepository<JobEntry, Integer> {
    Optional<JobEntry> findByNombreAndApPaternoAndHoraInicio(String nombre, String apPaterno, LocalTime hora);
    List<JobEntry> findByApPaterno(String apPaterno);
    List<JobEntry> findByHoraInicioBetween(LocalTime startTime, LocalTime endTime);
}
