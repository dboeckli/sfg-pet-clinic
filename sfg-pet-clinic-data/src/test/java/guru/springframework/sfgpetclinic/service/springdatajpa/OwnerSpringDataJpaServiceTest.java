package guru.springframework.sfgpetclinic.service.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
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
    classes = OwnerSpringDataJpaServiceTest.TestConfig.class,
    properties = "spring.main.allow-bean-definition-overriding=true"
)
@ActiveProfiles("springdatajpa")
@Slf4j
class OwnerSpringDataJpaServiceTest {

    @Autowired
    OwnerSpringDataJpaService service;

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

        service.save(owner1);
        service.save(owner2);
    }

    @AfterEach
    void tearDown() {
        service.findAll().forEach(service::delete);
    }

    @Test
    void findAll() {
        Set<Owner> owners = service.findAll();
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

        Owner savedOwner = service.save(owner3);

        Owner foundOwner = service.findById(savedOwner.getId());
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

        Owner savedOwner = service.save(owner3);

        assertNotNull(savedOwner);
        Owner foundOwner = service.findByLastName("Boeckli");
        assertNotNull(foundOwner);
    }

    @Test
    void delete() {
        service.findAll().forEach(service::delete);
        Set<Owner> ownersAfterDelete = service.findAll();

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

        Owner savedOwner = service.save(owner3);

        service.deleteById(savedOwner.getId());
        Owner foundOwner = service.findById(savedOwner.getId());
        assertNull(foundOwner);
    }

    @Test
    void findByLastName() {
        Owner owner = service.findByLastName("Mustermann");
        assertNotNull(owner);
        assertEquals("Max", owner.getFirstName());
        assertEquals("Mustermann", owner.getLastName());
    }
}