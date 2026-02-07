package guru.springframework.sfgpetclinic.service;

import guru.springframework.sfgpetclinic.model.Speciality;
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

class SpecialityServiceTest {

    @Slf4j
    abstract static class AbstractSpecialityServiceTest {

        @Autowired
        SpecialityService specialityService;

        @BeforeEach
        void setUp() {
            Speciality speciality = Speciality
                .builder()
                .description("something")
                .build();
            specialityService.save(speciality);

            Speciality speciality2 = Speciality
                .builder()
                .description("something else")
                .build();
            specialityService.save(speciality2);

        }

        @AfterEach
        void tearDown() {
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
            Set<Speciality> specialities = specialityService.findAll();
            assertNotNull(specialities);
            assertEquals(2, specialities.size());
        }

        @Test
        void findById() {
            Set<Speciality> specialities = specialityService.findAll();
            Speciality foundSpeciality = specialityService.findById(specialities.stream().findFirst().get().getId());
            assertNotNull(foundSpeciality);
        }

        @Test
        void save() {
            Speciality speciality = Speciality
                .builder()
                .description("something")
                .build();
            Speciality savedSpeciality = specialityService.save(speciality);

            assertNotNull(savedSpeciality);
        }

        @Test
        void delete() {
            Speciality speciality = Speciality
                .builder()
                .description("something")
                .build();
            Speciality savedSpeciality = specialityService.save(speciality);

            specialityService.delete(savedSpeciality);

            Speciality foundSpeciality = specialityService.findById(savedSpeciality.getId());
            assertNull(foundSpeciality);
        }

        @Test
        void deleteById() {
            Speciality speciality = Speciality
                .builder()
                .description("something")
                .build();
            Speciality savedSpeciality = specialityService.save(speciality);

            specialityService.deleteById(savedSpeciality.getId());

            Speciality foundSpeciality = specialityService.findById(savedSpeciality.getId());
            assertNull(foundSpeciality);
        }

    }

    @Nested
    @ActiveProfiles("springdatajpa")
    @SpringBootTest(
        classes = AbstractSpecialityServiceTest.TestConfig.class,
        properties = "spring.main.allow-bean-definition-overriding=true"
    )
    class VetJpaServiceTest extends AbstractSpecialityServiceTest {

    }

    @Nested
    @ActiveProfiles("map")
    @SpringBootTest(
        classes = AbstractSpecialityServiceTest.TestConfig.class,
        properties = "spring.main.allow-bean-definition-overriding=true"
    )
    class VetMapServiceTest extends AbstractSpecialityServiceTest {

    }

}