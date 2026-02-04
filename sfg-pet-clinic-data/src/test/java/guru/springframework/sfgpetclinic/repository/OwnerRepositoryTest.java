package guru.springframework.sfgpetclinic.repository;

import guru.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OwnerRepositoryTest {

    abstract static class AbstractOwnerRepositoryTest {

        @Autowired
        OwnerRepository ownerRepository;

        @EnableJpaRepositories(basePackages = "guru.springframework.sfgpetclinic.repository")
        @EntityScan(basePackages = "guru.springframework.sfgpetclinic.model")
        static class TestConfig {
        }

        @Test
        void findByLastName() {
            Owner owner = Owner.builder()
                .firstName("Max")
                .lastName("Mustermann")
                .address("Musterstraße 1")
                .city("Berlin")
                .telephone("123456789")
                .build();

            ownerRepository.save(owner);

            Owner foundOwner = ownerRepository.findByLastName("Mustermann");
            assertNotNull(foundOwner);
            assertEquals("Max", foundOwner.getFirstName());
        }
    }

    @Nested
    @ActiveProfiles("springdatajpa")
    @DataJpaTest
    @ContextConfiguration(classes = {AbstractOwnerRepositoryTest.TestConfig.class})
    class JpaTest extends AbstractOwnerRepositoryTest {

    }

    @Nested
    @ActiveProfiles("map")
    @DataJpaTest
    @ContextConfiguration(classes = {AbstractOwnerRepositoryTest.TestConfig.class})
    class MapTest extends AbstractOwnerRepositoryTest {

    }
}