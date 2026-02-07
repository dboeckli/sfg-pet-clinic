package guru.springframework.sfgpetclinic.service;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.model.Visit;
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

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class VisitServiceTest {

    @Slf4j
    abstract static class AbstractVisitServiceTest {

        @Autowired
        VisitService visitService;

        @Autowired
        OwnerService ownerService;

        @Autowired
        PetService petService;

        @Autowired
        PetTypeService petTypeService;

        @BeforeEach
        void setUp() {
            Owner owner1 = Owner.builder()
                .firstName("Max")
                .lastName("Mustermann")
                .address("Musterstraße 1")
                .city("Berlin")
                .telephone("123456789")
                .build();

            Owner owner2 = Owner.builder()
                .firstName("Erika")
                .lastName("Musterfrau")
                .address("Hauptstraße 5")
                .city("Hamburg")
                .telephone("987654321")
                .build();

            Owner savedOwner1 = ownerService.save(owner1);
            Owner savedOwner2 = ownerService.save(owner2);

            PetType petType = PetType.builder()
                .name("animal")
                .build();
            PetType savedPetType = petTypeService.save(petType);

            Pet pet1 = Pet.builder()
                .owner(savedOwner1)
                .petType(savedPetType)
                .name("pet1")
                .birthDate(LocalDate.now())
                .build();

            Pet pet2 = Pet.builder()
                .owner(savedOwner2)
                .petType(savedPetType)
                .name("pet2")
                .birthDate(LocalDate.now())
                .build();

            petService.save(pet1);
            petService.save(pet2);

            Visit visit1 = Visit
                .builder()
                .description("something")
                .date(LocalDate.now())
                .pet(pet1)
                .build();
            visitService.save(visit1);

            Visit visit2 = Visit
                .builder()
                .description("something2")
                .date(LocalDate.now())
                .pet(pet2)
                .build();
            visitService.save(visit2);
        }

        @AfterEach
        void tearDown() {
            visitService.findAll().forEach(visitService::delete);
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
            Set<Visit> visits = visitService.findAll();
            assertNotNull(visits);
            assertEquals(2, visits.size());
        }

        @Test
        void findById() {
            Set<Visit> visits = visitService.findAll();

            Visit foundVisit = visitService.findById(visits.stream().findFirst().get().getId());
            assertNotNull(foundVisit);
        }

        @Test
        void save() {
            Owner owner3 = Owner.builder()
                .firstName("Dominique")
                .lastName("Boeckli")
                .address("Weinbergstrasse 68")
                .city("Erlenbach")
                .telephone("123456789")
                .build();
            Owner savedOwner3 = ownerService.save(owner3);

            PetType petType3 = PetType.builder()
                .name("animal3")
                .build();
            PetType savedPetType = petTypeService.save(petType3);

            Pet pet3 = Pet.builder()
                .owner(savedOwner3)
                .petType(savedPetType)
                .name("pet3")
                .birthDate(LocalDate.now())
                .build();

            Pet savedPet = petService.save(pet3);

            Visit visit1 = Visit
                .builder()
                .description("something")
                .date(LocalDate.now())
                .pet(savedPet)
                .build();
            Visit savedVisit = visitService.save(visit1);
            assertNotNull(savedVisit);
        }

        @Test
        void delete() {
            Owner owner3 = Owner.builder()
                .firstName("Dominique")
                .lastName("Boeckli")
                .address("Weinbergstrasse 68")
                .city("Erlenbach")
                .telephone("123456789")
                .build();
            Owner savedOwner3 = ownerService.save(owner3);

            PetType petType3 = PetType.builder()
                .name("animal3")
                .build();
            PetType savedPetType = petTypeService.save(petType3);

            Pet pet3 = Pet.builder()
                .owner(savedOwner3)
                .petType(savedPetType)
                .name("pet1")
                .birthDate(LocalDate.now())
                .build();

            Pet savedPet = petService.save(pet3);
            assertNotNull(savedPet);

            Visit visit1 = Visit
                .builder()
                .description("something")
                .date(LocalDate.now())
                .pet(savedPet)
                .build();
            Visit savedVisit = visitService.save(visit1);

            visitService.delete(savedVisit);

            Visit foundVisit = visitService.findById(savedVisit.getId());
            assertNull(foundVisit);
        }

        @Test
        void deleteById() {
            Owner owner3 = Owner.builder()
                .firstName("Dominique")
                .lastName("Boeckli")
                .address("Weinbergstrasse 68")
                .city("Erlenbach")
                .telephone("123456789")
                .build();
            Owner savedOwner3 = ownerService.save(owner3);

            PetType petType3 = PetType.builder()
                .name("animal3")
                .build();
            PetType savedPetType = petTypeService.save(petType3);

            Pet pet3 = Pet.builder()
                .owner(savedOwner3)
                .petType(savedPetType)
                .name("pet1")
                .birthDate(LocalDate.now())
                .build();

            Pet savedPet = petService.save(pet3);
            assertNotNull(savedPet);

            Visit visit1 = Visit
                .builder()
                .description("something")
                .date(LocalDate.now())
                .pet(savedPet)
                .build();
            Visit savedVisit = visitService.save(visit1);

            visitService.deleteById(savedVisit.getId());

            Visit foundVisit = visitService.findById(savedVisit.getId());
            assertNull(foundVisit);
        }

    }

    @Nested
    @ActiveProfiles("springdatajpa")
    @SpringBootTest(
        classes = AbstractVisitServiceTest.TestConfig.class,
        properties = "spring.main.allow-bean-definition-overriding=true"
    )
    class VisitJpaServiceTest extends AbstractVisitServiceTest {

    }

    @Nested
    @ActiveProfiles("map")
    @SpringBootTest(
        classes = AbstractVisitServiceTest.TestConfig.class,
        properties = "spring.main.allow-bean-definition-overriding=true"
    )
    class VisitMapServiceTest extends AbstractVisitServiceTest {

    }

}