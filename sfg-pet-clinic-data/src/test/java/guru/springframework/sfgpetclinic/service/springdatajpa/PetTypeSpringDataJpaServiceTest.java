package guru.springframework.sfgpetclinic.service.springdatajpa;

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

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
    classes = PetTypeSpringDataJpaServiceTest.TestConfig.class,
    properties = "spring.main.allow-bean-definition-overriding=true"
)
@ActiveProfiles("springdatajpa")
@Slf4j
class PetTypeSpringDataJpaServiceTest {

    @SpringBootConfiguration
    @EnableAutoConfiguration
    @ComponentScan(basePackages = "guru.springframework.sfgpetclinic")
    @EnableJpaRepositories(basePackages = "guru.springframework.sfgpetclinic.repository")
    @EntityScan(basePackages = "guru.springframework.sfgpetclinic.model")
    static class TestConfig {
    }

    @BeforeEach
    void setUp() {
        PetType petType1 = PetType.builder()
            .name("animal")
            .build();
        petTypeSpringDataJpaService.save(petType1);

        PetType petType2 = PetType.builder()
            .name("animal2")
            .build();
        petTypeSpringDataJpaService.save(petType2);
    }

    @AfterEach
    void tearDown() {
        petTypeSpringDataJpaService.findAll().forEach(petTypeSpringDataJpaService::delete);
    }

    @Autowired
    PetTypeSpringDataJpaService petTypeSpringDataJpaService;

    @Test
    void findAll() {
        Set<PetType> petTypes = petTypeSpringDataJpaService.findAll();
        assertNotNull(petTypes);
        assertEquals(2, petTypes.size());
    }

    @Test
    void findById() {
        Set<PetType> petTypes = petTypeSpringDataJpaService.findAll();

        PetType foundPetType = petTypeSpringDataJpaService.findById(petTypes.stream().findFirst().get().getId());
        assertNotNull(foundPetType);
    }

    @Test
    void save() {
        PetType petType3 = PetType.builder()
            .name("animal3")
            .build();
        PetType savedPetType = petTypeSpringDataJpaService.save(petType3);
        assertNotNull(savedPetType);
    }

    @Test
    void delete() {
        PetType petType3 = PetType.builder()
            .name("animal3")
            .build();
        PetType savedPetType = petTypeSpringDataJpaService.save(petType3);
        assertNotNull(savedPetType);

        petTypeSpringDataJpaService.delete(savedPetType);

        PetType foundPetType = petTypeSpringDataJpaService.findById(savedPetType.getId());
        assertNull(foundPetType);
    }

    @Test
    void deleteById() {
        PetType petType3 = PetType.builder()
            .name("animal3")
            .build();
        PetType savedPetType = petTypeSpringDataJpaService.save(petType3);
        assertNotNull(savedPetType);

        petTypeSpringDataJpaService.deleteById(savedPetType.getId());

        PetType foundPetType = petTypeSpringDataJpaService.findById(savedPetType.getId());
        assertNull(foundPetType);
    }
}