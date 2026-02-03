package guru.springframework.sfgpetclinic.service.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.model.Vet;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
    classes = VetSpringDataJpaServiceTest.TestConfig.class,
    properties = "spring.main.allow-bean-definition-overriding=true"
)
@ActiveProfiles("springdatajpa")
@Slf4j
class VetSpringDataJpaServiceTest {

    @BeforeEach
    void setUp() {
        Speciality speciality = Speciality
            .builder()
            .description("something")
            .build();
        Speciality savedSpeciality = specialSpringDataJpaService.save(speciality);

        Vet vet1 = Vet.builder()
            .specialities(new java.util.HashSet<>(Set.of(savedSpeciality)))
            .build();
        vet1.setFirstName("John");
        vet1.setLastName("Mustermann");

        vetSpringDataJpaService.save(vet1);

        Vet vet2 = Vet.builder()
            .specialities(new java.util.HashSet<>(Set.of(speciality)))
            .build();
        vet2.setFirstName("Erika");
        vet2.setLastName("Musterfrau");

        vetSpringDataJpaService.save(vet2);
    }

    @AfterEach
    void tearDown() {
        vetSpringDataJpaService.findAll().forEach(vetSpringDataJpaService::delete);
        specialSpringDataJpaService.findAll().forEach(specialSpringDataJpaService::delete);
    }

    @Autowired
    VetSpringDataJpaService vetSpringDataJpaService;

    @Autowired
    SpecialSpringDataJpaService specialSpringDataJpaService;

    @SpringBootConfiguration
    @EnableAutoConfiguration
    @ComponentScan(basePackages = "guru.springframework.sfgpetclinic")
    @EnableJpaRepositories(basePackages = "guru.springframework.sfgpetclinic.repository")
    @EntityScan(basePackages = "guru.springframework.sfgpetclinic.model")
    static class TestConfig {
    }

    @Test
    void findAll() {
        Set<Vet> vets = vetSpringDataJpaService.findAll();
        assertNotNull(vets);
        assertEquals(2, vets.size());
    }

    @Test
    void findById() {
        Set<Vet> vets = vetSpringDataJpaService.findAll();
        Vet foundVet = vetSpringDataJpaService.findById(vets.stream().findFirst().get().getId());
        assertNotNull(foundVet);
    }

    @Test
    void save() {
        Speciality speciality = Speciality
            .builder()
            .description("something")
            .build();
        Speciality savedSpeciality = specialSpringDataJpaService.save(speciality);

        Vet vet = Vet.builder()
            .specialities(new java.util.HashSet<>(Set.of(savedSpeciality)))
            .build();
        vet.setFirstName("John");
        vet.setLastName("Mustermann");

        Vet savedVet = vetSpringDataJpaService.save(vet);

        assertNotNull(savedVet);
    }

    @Test
    void delete() {
        Speciality speciality = Speciality
            .builder()
            .description("something")
            .build();
        Speciality savedSpeciality = specialSpringDataJpaService.save(speciality);

        Vet vet = Vet.builder()
            .specialities(new java.util.HashSet<>(Set.of(savedSpeciality)))
            .build();
        vet.setFirstName("John");
        vet.setLastName("Mustermann");

        Vet savedVet = vetSpringDataJpaService.save(vet);

        vetSpringDataJpaService.delete(savedVet);

        Vet foundVet = vetSpringDataJpaService.findById(savedVet.getId());
        assertNull(foundVet);
    }

    @Test
    void deleteById() {
        Speciality speciality = Speciality
            .builder()
            .description("something")
            .build();
        Speciality savedSpeciality = specialSpringDataJpaService.save(speciality);

        Vet vet = Vet.builder()
            .specialities(new java.util.HashSet<>(Set.of(savedSpeciality)))
            .build();
        vet.setFirstName("John");
        vet.setLastName("Mustermann");

        Vet savedVet = vetSpringDataJpaService.save(vet);

        vetSpringDataJpaService.deleteById(savedVet.getId());

        Vet foundVet = vetSpringDataJpaService.findById(savedVet.getId());
        assertNull(foundVet);
    }
}