package guru.springframework.sfgpetclinic.service;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.model.Vet;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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

class VetServiceTest {

    @Slf4j
    abstract static class AbstractVetServiceTest {

        @Autowired
        VetService vetService;

        @Autowired
        SpecialityService specialityService;

        @BeforeEach
        void setUp() {
            Speciality speciality = Speciality
                .builder()
                .description("something")
                .build();
            Speciality savedSpeciality = specialityService.save(speciality);

            Vet vet1 = Vet.builder()
                .specialities(new java.util.HashSet<>(Set.of(savedSpeciality)))
                .build();
            vet1.setFirstName("John");
            vet1.setLastName("Mustermann");

            vetService.save(vet1);

            Vet vet2 = Vet.builder()
                .specialities(new java.util.HashSet<>(Set.of(speciality)))
                .build();
            vet2.setFirstName("Erika");
            vet2.setLastName("Musterfrau");

            vetService.save(vet2);
        }

        @AfterEach
        void tearDown() {
            vetService.findAll().forEach(vetService::delete);
            specialityService.findAll().forEach(specialityService::delete);
        }

        @SpringBootConfiguration
        @EnableAutoConfiguration
        @ComponentScan(basePackages = "guru.springframework.sfgpetclinic")
        @EnableJpaRepositories(basePackages = "guru.springframework.sfgpetclinic.repository")
        @EntityScan(basePackages = "guru.springframework.sfgpetclinic.model")
        static class TestConfig {
        }

        @Test
        void findAll() {
            Set<Vet> vets = vetService.findAll();
            assertNotNull(vets);
            assertEquals(2, vets.size());
        }

        @Test
        void findById() {
            Set<Vet> vets = vetService.findAll();
            Vet foundVet = vetService.findById(vets.stream().findFirst().get().getId());
            assertNotNull(foundVet);
        }

        @Test
        void save() {
            Speciality speciality = Speciality
                .builder()
                .description("something")
                .build();
            Speciality savedSpeciality = specialityService.save(speciality);

            Vet vet = Vet.builder()
                .specialities(new java.util.HashSet<>(Set.of(savedSpeciality)))
                .build();
            vet.setFirstName("John");
            vet.setLastName("Mustermann");

            Vet savedVet = vetService.save(vet);

            assertNotNull(savedVet);
        }

        @Test
        void delete() {
            Speciality speciality = Speciality
                .builder()
                .description("something")
                .build();
            Speciality savedSpeciality = specialityService.save(speciality);

            Vet vet = Vet.builder()
                .specialities(new java.util.HashSet<>(Set.of(savedSpeciality)))
                .build();
            vet.setFirstName("John");
            vet.setLastName("Mustermann");

            Vet savedVet = vetService.save(vet);

            vetService.delete(savedVet);

            Vet foundVet = vetService.findById(savedVet.getId());
            assertNull(foundVet);
        }

        @Test
        void deleteById() {
            Speciality speciality = Speciality
                .builder()
                .description("something")
                .build();
            Speciality savedSpeciality = specialityService.save(speciality);

            Vet vet = Vet.builder()
                .specialities(new java.util.HashSet<>(Set.of(savedSpeciality)))
                .build();
            vet.setFirstName("John");
            vet.setLastName("Mustermann");

            Vet savedVet = vetService.save(vet);

            vetService.deleteById(savedVet.getId());

            Vet foundVet = vetService.findById(savedVet.getId());
            assertNull(foundVet);
        }
    }

    @Nested
    @ActiveProfiles("springdatajpa")
    @SpringBootTest(
        classes = AbstractVetServiceTest.TestConfig.class,
        properties = "spring.main.allow-bean-definition-overriding=true"
    )
    class VetJpaServiceTest extends AbstractVetServiceTest {

    }

    @Nested
    @ActiveProfiles("map")
    @SpringBootTest(
        classes = AbstractVetServiceTest.TestConfig.class,
        properties = "spring.main.allow-bean-definition-overriding=true"
    )
    class VetMapServiceTest extends AbstractVetServiceTest {

    }
}