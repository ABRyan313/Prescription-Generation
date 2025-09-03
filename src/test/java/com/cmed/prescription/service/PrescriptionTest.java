package com.cmed.prescription.service;

import com.cmed.prescription.mapper.PrescriptionMapper;
import com.cmed.prescription.model.Gender;
import com.cmed.prescription.model.domain.Prescription;
import com.cmed.prescription.model.dto.CreatePrescriptionRequest;
import com.cmed.prescription.model.dto.UpdatePrescriptionRequest;
import com.cmed.prescription.persistence.entity.PrescriptionEntity;
import com.cmed.prescription.persistence.repository.PrescriptionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PrescriptionTest {

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @Mock
    private PrescriptionMapper mapper;

    @InjectMocks
    private PrescriptionService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

        @Test
        void getPrescriptions_returnsMappedPrescriptions() {
            PrescriptionRepository repository = mock(PrescriptionRepository.class);
            PrescriptionMapper mapper = mock(PrescriptionMapper.class);
            PrescriptionService service = new PrescriptionService(repository, mapper);

            LocalDate start = LocalDate.of(2024, 6, 1);
            LocalDate end = LocalDate.of(2024, 6, 30);

            PrescriptionEntity entity1 = new PrescriptionEntity();
            PrescriptionEntity entity2 = new PrescriptionEntity();
            List<PrescriptionEntity> entities = Arrays.asList(entity1, entity2);

            Prescription domain1 = new Prescription();
            Prescription domain2 = new Prescription();

            when(repository.findByPrescriptionDateBetween(start, end)).thenReturn(entities);
            when(mapper.entityToDomain(entity1)).thenReturn(domain1);
            when(mapper.entityToDomain(entity2)).thenReturn(domain2);

            List<Prescription> result = service.getPrescriptions(start, end);

            assertEquals(2, result.size());
            assertTrue(result.contains(domain1));
            assertTrue(result.contains(domain2));
            verify(repository).findByPrescriptionDateBetween(start, end);
            verify(mapper).entityToDomain(entity1);
            verify(mapper).entityToDomain(entity2);
        }

    @Test
        void getPrescriptions_nullDates_usesCurrentMonth() {
            PrescriptionRepository repository = mock(PrescriptionRepository.class);
            PrescriptionMapper mapper = mock(PrescriptionMapper.class);
            PrescriptionService service = new PrescriptionService(repository, mapper);

            LocalDate now = LocalDate.now();
            LocalDate expectedStart = now.withDayOfMonth(1);
            LocalDate expectedEnd = now.withDayOfMonth(now.lengthOfMonth());

            when(repository.findByPrescriptionDateBetween(expectedStart, expectedEnd)).thenReturn(List.of());

            List<Prescription> result = service.getPrescriptions(null, null);

            assertNotNull(result);
            assertTrue(result.isEmpty());
            verify(repository).findByPrescriptionDateBetween(expectedStart, expectedEnd);
        }

        @Test
        void testCreatePrescription() {
            PrescriptionRepository repository = Mockito.mock(PrescriptionRepository.class);
            PrescriptionMapper mapper = Mockito.mock(PrescriptionMapper.class);

            CreatePrescriptionRequest request = new CreatePrescriptionRequest( LocalDate.now(), // prescriptionDate
                    "John Doe",                 // patientName
                    30,                         // patientAge
                    Gender.MALE,                // patientGender
                    "Flu symptoms",             // diagnosis
                    "Paracetamol 500mg",        // medicines
                    LocalDate.now().plusDays(7));
            PrescriptionEntity entity = new PrescriptionEntity();
            PrescriptionEntity savedEntity = new PrescriptionEntity();
            Prescription domain = new Prescription();

            Mockito.when(mapper.createRequestToEntity(request)).thenReturn(entity);
            Mockito.when(repository.save(entity)).thenReturn(savedEntity);
            Mockito.when(mapper.entityToDomain(savedEntity)).thenReturn(domain);

            PrescriptionService service = new PrescriptionService(repository, mapper);

            Prescription result = service.createPrescription(request);

            assertEquals(domain, result);
            Mockito.verify(mapper).createRequestToEntity(request);
            Mockito.verify(repository).save(entity);
            Mockito.verify(mapper).entityToDomain(savedEntity);
        }

    @Test
    void testUpdatePrescription_success() {
        Long id = 1L;
        UpdatePrescriptionRequest request = new UpdatePrescriptionRequest(
                LocalDate.now(),
                "Jane Doe",
                28,
                Gender.FEMALE,
                "Cold",
                "Ibuprofen",
                LocalDate.now().plusDays(5)
        );

        PrescriptionEntity entity = new PrescriptionEntity();
        PrescriptionEntity updatedEntity = new PrescriptionEntity();
        Prescription domain = new Prescription();

        // Mock repository
        when(prescriptionRepository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.updateRequestToEntity(request, entity)).thenReturn(updatedEntity);
        when(prescriptionRepository.save(updatedEntity)).thenReturn(updatedEntity);
        when(mapper.entityToDomain(updatedEntity)).thenReturn(domain);

        // Act
        Prescription result = service.updatePrescription(id, request);

        // Assert
        assertNotNull(result);
        verify(prescriptionRepository).findById(id);
        verify(mapper).updateRequestToEntity(request, entity);
        verify(prescriptionRepository).save(updatedEntity);
        verify(mapper).entityToDomain(updatedEntity);
    }

    @Test
    public void testFindById_ReturnsPrescription_WhenEntityExists() {
        // Arrange
        Long id = 1L;
        PrescriptionEntity entity = new PrescriptionEntity();
        Prescription expectedPrescription = new Prescription();
        PrescriptionRepository mockRepository = Mockito.mock(PrescriptionRepository.class);
        PrescriptionMapper mockMapper = Mockito.mock(PrescriptionMapper.class);
        PrescriptionService service = new PrescriptionService(mockRepository, mockMapper);

        Mockito.when(mockRepository.findById(id)).thenReturn(Optional.of(entity));
        Mockito.when(mockMapper.entityToDomain(entity)).thenReturn(expectedPrescription);

        // Act
        Prescription result = service.findById(id);

        // Assert
        Assertions.assertEquals(expectedPrescription, result);
    }
}


