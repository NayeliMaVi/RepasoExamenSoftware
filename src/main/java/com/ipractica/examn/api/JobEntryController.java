package com.ipractica.examn.api;

import com.ipractica.examn.dto.JobEntryRequestDTO;
import com.ipractica.examn.dto.JobEntryResponseDTO;
import com.ipractica.examn.dto.JobPaymentDTO;
import com.ipractica.examn.service.JobEntryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jobentrys")
public class JobEntryController {
    private final JobEntryService jobEntryService;

    @GetMapping
    public ResponseEntity<List<JobEntryResponseDTO>> findAll() {
        List<JobEntryResponseDTO> jobentries = jobEntryService.getAll();
        return new ResponseEntity<List<JobEntryResponseDTO>>(jobentries, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobEntryResponseDTO> getJobEntryById(@PathVariable("id") Integer id) {
        JobEntryResponseDTO jobentry = jobEntryService.findById(id);
        return new ResponseEntity<JobEntryResponseDTO>(jobentry, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JobEntryResponseDTO> createJobEntry(@Valid @RequestBody JobEntryRequestDTO jobEntryRequestDTO) {
        JobEntryResponseDTO jobEntryCreado = jobEntryService.register(jobEntryRequestDTO);
        return new ResponseEntity<JobEntryResponseDTO>(jobEntryCreado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobEntryResponseDTO> update(@PathVariable Integer id, @Valid @RequestBody JobEntryRequestDTO jobEntryRequestDTO) {
        JobEntryResponseDTO jobEntryCreado = jobEntryService.update(id, jobEntryRequestDTO);
        return new ResponseEntity<>(jobEntryCreado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        jobEntryService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/calculatePayment")
    public ResponseEntity<JobPaymentDTO> calculateJobPayment(
            @PathVariable Integer id,
            @RequestParam("exitTime") LocalTime exitTime) {
        // Llamar al servicio para calcular el pago
        JobPaymentDTO jobPaymentDTO = jobEntryService.calculateJobPayment(id, exitTime);
        return ResponseEntity.ok(jobPaymentDTO);
    }

    // Endpoint para obtener JobEntries por apellido paterno
    @GetMapping("/byLastName")
    public ResponseEntity<List<JobPaymentDTO>> getJobEntriesByLastName(
            @RequestParam("lastNameWorker") String lastNameWorker) {

        // Llamamos al servicio para obtener la lista de JobPaymentDTO
        List<JobPaymentDTO> jobPayments = jobEntryService.getJobEntriesByLastName(lastNameWorker);

        // Devolvemos la lista en una respuesta HTTP 200 (OK)
        return ResponseEntity.ok(jobPayments);
    }

    @GetMapping("/byTimeRange")
    public ResponseEntity<List<JobPaymentDTO>> getJobEntriesByTimeRange(
            @RequestParam("startTime") LocalTime startTime,
            @RequestParam("endTime")  LocalTime endTime) {

        // Llamamos al servicio para obtener la lista de JobPaymentDTO
        List<JobPaymentDTO> jobPayments = jobEntryService.getJobEntriesByTimeRange(startTime, endTime);

        // Devolvemos la lista en una respuesta HTTP 200 (OK)
        return ResponseEntity.ok(jobPayments);
    }
}
