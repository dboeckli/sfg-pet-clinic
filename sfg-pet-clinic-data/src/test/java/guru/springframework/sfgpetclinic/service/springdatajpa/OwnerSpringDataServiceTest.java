package guru.springframework.sfgpetclinic.service.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.service.OwnerService;
import guru.springframework.sfgpetclinic.service.PetService;
import guru.springframework.sfgpetclinic.service.PetTypeService;
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

class OwnerSpringDataServiceTest {

    @Slf4j
    abstract static class AbstractOwnerSpringDataServiceTest {

        @Autowired
        OwnerService ownerService;

        @SpringBootConfiguration
        @EnableAutoConfiguration
        @ComponentScan(basePackages = "guru.springframework.sfgpetclinic")
        @EnableJpaRepositories(basePackages = "guru.springframework.sfgpetclinic.repository")
        @EntityScan(basePackages = "guru.springframework.sfgpetclinic.model")
        static class TestConfig {
        }

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

            ownerService.save(owner1);
            ownerService.save(owner2);
        }

        @AfterEach
        void tearDown() {
            ownerService.findAll().forEach(ownerService::delete);
        }

        @Test
        void findAll() {
            Set<Owner> owners = ownerService.findAll();
            assertNotNull(owners);
            assertEquals(2, owners.size());
        }

        @Test
        void findById() {
            Owner owner3 = Owner.builder()
                .firstName("Dominique")
                .lastName("Boeckli")
                .address("Weinbergstrasse")
                .city("Erlenbach")
                .telephone("123456789")
                .build();

            Owner savedOwner = ownerService.save(owner3);

            Owner foundOwner = ownerService.findById(savedOwner.getId());
            assertNotNull(foundOwner);
        }

        @Test
        void save() {
            Owner owner3 = Owner.builder()
                .firstName("Dominique")
                .lastName("Boeckli")
                .address("Weinbergstrasse")
                .city("Erlenbach")
                .telephone("123456789")
                .build();

            Owner savedOwner = ownerService.save(owner3);

            assertNotNull(savedOwner);
            Owner foundOwner = ownerService.findByLastName("Boeckli");
            assertNotNull(foundOwner);
        }

        @Test
        void delete() {
            ownerService.findAll().forEach(ownerService::delete);
            Set<Owner> ownersAfterDelete = ownerService.findAll();

            assertTrue(ownersAfterDelete.isEmpty());
        }

        @Test
        void deleteById() {
            Owner owner3 = Owner.builder()
                .firstName("Dominique")
                .lastName("Boeckli")
                .address("Weinbergstrasse")
                .city("Erlenbach")
                .telephone("123456789")
                .build();

            Owner savedOwner = ownerService.save(owner3);

            ownerService.deleteById(savedOwner.getId());
            Owner foundOwner = ownerService.findById(savedOwner.getId());
            assertNull(foundOwner);
        }

        @Test
        void findByLastName() {
            Owner owner = ownerService.findByLastName("Mustermann");
            assertNotNull(owner);
            assertEquals("Max", owner.getFirstName());
            assertEquals("Mustermann", owner.getLastName());
        }
    }

    @Nested
    @ActiveProfiles("springdatajpa")
    @SpringBootTest(
        classes = AbstractOwnerSpringDataServiceTest.TestConfig.class,
        properties = "spring.main.allow-bean-definition-overriding=true"
    )
    class OwnerSpringDataJpaServiceTest extends AbstractOwnerSpringDataServiceTest {

    }

    @Nested
    @ActiveProfiles("map")
    @SpringBootTest(
        classes = AbstractOwnerSpringDataServiceTest.TestConfig.class,
        properties = "spring.main.allow-bean-definition-overriding=true"
    )
    class OwnerSpringDataMapServiceTest extends AbstractOwnerSpringDataServiceTest {

        @Autowired
        PetService petService;

        @Autowired
        PetTypeService petTypeService;

        @Test
        void saveNullOwner() {
            Owner savedOwner = ownerService.save(null);
            assertNull(savedOwner);
        }

        @Test
        void saveOwnerWithPets() {
            Owner owner3 = Owner.builder()
                .firstName("Dominique")
                .lastName("Boeckli")
                .address("Weinbergstrasse")
                .city("Erlenbach")
                .telephone("123456789")
                .build();

            Pet pet1 = Pet.builder().petType(PetType.builder().build()).build();
            Pet pet2 = Pet.builder().petType(PetType.builder().build()).build();
            Pet pet3 = Pet.builder().petType(PetType.builder().build()).build();
            owner3.setPets(Set.of(pet1, pet2, pet3));

            Owner savedOwner = ownerService.save(owner3);

            assertNotNull(savedOwner);
        }

        @Test
        void saveOwnerWithExistingPets() {
            PetType petType = PetType.builder().build();
            PetType savedPetType = petTypeService.save(petType);

            Pet pet = Pet.builder().petType(savedPetType).build();
            Pet savedPet = petService.save(pet);

            Owner owner3 = Owner.builder()
                .firstName("Dominique")
                .lastName("Boeckli")
                .address("Weinbergstrasse")
                .city("Erlenbach")
                .telephone("123456789")
                .build();
            owner3.setPets(Set.of(savedPet));

            Owner savedOwner = ownerService.save(owner3);

            assertNotNull(savedOwner);
        }

    }

}