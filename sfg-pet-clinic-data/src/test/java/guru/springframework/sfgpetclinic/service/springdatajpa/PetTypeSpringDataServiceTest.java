package guru.springframework.sfgpetclinic.service.springdatajpa;

import guru.springframework.sfgpetclinic.model.PetType;
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

class PetTypeSpringDataServiceTest {

    @Slf4j
    abstract static class AbstractPetTypeSpringDataServiceTest {

        @Autowired
        PetTypeService petTypeService;

        @BeforeEach
        void setUp() {
            PetType petType1 = PetType.builder()
                .name("animal")
                .build();
            petTypeService.save(petType1);

            PetType petType2 = PetType.builder()
                .name("animal2")
                .build();
            petTypeService.save(petType2);
        }

        @AfterEach
        void tearDown() {
            petTypeService.findAll().forEach(petTypeService::delete);
        }

        @Test
        void findAll() {
            Set<PetType> petTypes = petTypeService.findAll();
            assertNotNull(petTypes);
            assertEquals(2, petTypes.size());
        }

        @Test
        void findById() {
            Set<PetType> petTypes = petTypeService.findAll();

            PetType foundPetType = petTypeService.findById(petTypes.stream().findFirst().get().getId());
            assertNotNull(foundPetType);
        }

        @Test
        void save() {
            PetType petType3 = PetType.builder()
                .name("animal3")
                .build();
            PetType savedPetType = petTypeService.save(petType3);
            assertNotNull(savedPetType);
        }

        @Test
        void delete() {
            PetType petType3 = PetType.builder()
                .name("animal3")
                .build();
            PetType savedPetType = petTypeService.save(petType3);
            assertNotNull(savedPetType);

            petTypeService.delete(savedPetType);

            PetType foundPetType = petTypeService.findById(savedPetType.getId());
            assertNull(foundPetType);
        }

        @Test
        void deleteById() {
            PetType petType3 = PetType.builder()
                .name("animal3")
                .build();
            PetType savedPetType = petTypeService.save(petType3);
            assertNotNull(savedPetType);

            petTypeService.deleteById(savedPetType.getId());

            PetType foundPetType = petTypeService.findById(savedPetType.getId());
            assertNull(foundPetType);
        }

        @SpringBootConfiguration
        @EnableAutoConfiguration
        @ComponentScan(basePackages = "guru.springframework.sfgpetclinic")
        @EnableJpaRepositories(basePackages = "guru.springframework.sfgpetclinic.repository")
        @EntityScan(basePackages = "guru.springframework.sfgpetclinic.model")
        static class TestConfig {
        }

    }

    @Nested
    @ActiveProfiles("springdatajpa")
    @SpringBootTest(
        classes = AbstractPetTypeSpringDataServiceTest.TestConfig.class,
        properties = "spring.main.allow-bean-definition-overriding=true"
    )
    class PetTypeSpringDataJpaServiceTest extends AbstractPetTypeSpringDataServiceTest {

    }

    @Nested
    @ActiveProfiles("map")
    @SpringBootTest(
        classes = AbstractPetTypeSpringDataServiceTest.TestConfig.class,
        properties = "spring.main.allow-bean-definition-overriding=true"
    )
    class PetTypeSpringDataMapServiceTest extends AbstractPetTypeSpringDataServiceTest {

    }


}