package guru.springframework.sfgpetclinic.service.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
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

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
    classes = PetSpringDataJpaServiceTest.TestConfig.class,
    properties = "spring.main.allow-bean-definition-overriding=true"
)
@ActiveProfiles("springdatajpa")
@Slf4j
class PetSpringDataJpaServiceTest {

    @Autowired
    PetSpringDataJpaService petSpringDataJpaService;

    @Autowired
    OwnerSpringDataJpaService ownerSpringDataJpaService;

    @Autowired
    PetTypeSpringDataJpaService petTypeSpringDataJpaService;

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

        Owner savedOwner1 = ownerSpringDataJpaService.save(owner1);
        Owner savedOwner2 = ownerSpringDataJpaService.save(owner2);

        PetType petType = PetType.builder()
            .name("animal")
            .build();
        PetType savedPetType = petTypeSpringDataJpaService.save(petType);

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

        petSpringDataJpaService.save(pet1);
        petSpringDataJpaService.save(pet2);
    }

    @AfterEach
    void tearDown() {
        petSpringDataJpaService.findAll().forEach(petSpringDataJpaService::delete);
    }


    @Test
    void findAll() {
        Set<Pet> pets = petSpringDataJpaService.findAll();
        assertNotNull(pets);
        assertEquals(2, pets.size());
    }

    @Test
    void findById() {
        Set<Pet> pets = petSpringDataJpaService.findAll();

        Pet foundPet = petSpringDataJpaService.findById(pets.stream().findFirst().get().getId());
        assertNotNull(foundPet);
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
        Owner savedOwner3 = ownerSpringDataJpaService.save(owner3);

        PetType petType3 = PetType.builder()
            .name("animal3")
            .build();
        PetType savedPetType = petTypeSpringDataJpaService.save(petType3);

        Pet pet3 = Pet.builder()
            .owner(savedOwner3)
            .petType(savedPetType)
            .name("pet1")
            .birthDate(LocalDate.now())
            .build();

        Pet savedPet = petSpringDataJpaService.save(pet3);
        assertNotNull(savedPet);
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
        Owner savedOwner3 = ownerSpringDataJpaService.save(owner3);

        PetType petType3 = PetType.builder()
            .name("animal3")
            .build();
        PetType savedPetType = petTypeSpringDataJpaService.save(petType3);

        Pet pet3 = Pet.builder()
            .owner(savedOwner3)
            .petType(savedPetType)
            .name("pet1")
            .birthDate(LocalDate.now())
            .build();

        Pet savedPet = petSpringDataJpaService.save(pet3);
        assertNotNull(savedPet);

        petSpringDataJpaService.delete(savedPet);

        Pet foundPet = petSpringDataJpaService.findById(savedPet.getId());
        assertNull(foundPet);
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
        Owner savedOwner3 = ownerSpringDataJpaService.save(owner3);

        PetType petType3 = PetType.builder()
            .name("animal3")
            .build();
        PetType savedPetType = petTypeSpringDataJpaService.save(petType3);

        Pet pet3 = Pet.builder()
            .owner(savedOwner3)
            .petType(savedPetType)
            .name("pet1")
            .birthDate(LocalDate.now())
            .build();

        Pet savedPet = petSpringDataJpaService.save(pet3);
        assertNotNull(savedPet);

        petSpringDataJpaService.deleteById(savedPet.getId());

        Pet foundPet = petSpringDataJpaService.findById(savedPet.getId());
        assertNull(foundPet);
    }
}