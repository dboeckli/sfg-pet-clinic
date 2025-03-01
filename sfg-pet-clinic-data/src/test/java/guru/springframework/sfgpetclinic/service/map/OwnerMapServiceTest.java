package guru.springframework.sfgpetclinic.service.map;

import guru.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

    OwnerMapService ownerMapService;

    private Long givenId = 1L;
    private String givenLastName = "Boeckli";

    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());
        ownerMapService.save(Owner.builder().id(givenId).lastName(givenLastName).build());
    }

    @Test
    void findAll() {
        Set<Owner> owners = ownerMapService.findAll();
        assertEquals(1, owners.size());
    }

    @Test
    void findById() {
        Owner owner = ownerMapService.findById(givenId);
        assertEquals(givenId, owner.getId());
    }

    @Test
    void saveWithId() {
        Long id = 2L;

        Owner savedOwner = ownerMapService.save(Owner.builder().id(id).lastName("").build());

        assertEquals(id, savedOwner.getId());
    }

    @Test
    void saveWithoutId() {
        Owner savedOwner2 = ownerMapService.save(Owner.builder().build());

        assertNotNull(savedOwner2);
        assertNotNull(savedOwner2.getId());
    }

    @Test
    void delete() {
        ownerMapService.delete(ownerMapService.findById(givenId));

        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void deleteById() {
        ownerMapService.deleteById(givenId);
        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void findByLastName() {
        Owner ownerFoundByLastName = ownerMapService.findByLastName(givenLastName);

        assertNotNull(ownerFoundByLastName);
        assertEquals(givenLastName, ownerFoundByLastName.getLastName());

    }

    @Test
    void findByLastNameNotFound() {
        Owner ownerFoundByLastName = ownerMapService.findByLastName("pumukel");

        assertNull(ownerFoundByLastName);
    }
}
