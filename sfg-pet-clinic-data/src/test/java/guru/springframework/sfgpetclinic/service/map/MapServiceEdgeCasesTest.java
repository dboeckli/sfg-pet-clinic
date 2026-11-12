package guru.springframework.sfgpetclinic.service.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.service.PetService;
import guru.springframework.sfgpetclinic.service.PetTypeService;
import guru.springframework.sfgpetclinic.service.SpecialityService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class MapServiceEdgeCasesTest {

    @Test
    void saveNullObjectThrows() {
        PetTypeMapService service = new PetTypeMapService();

        assertThrows(IllegalArgumentException.class, () -> service.save(null));
    }

    @Test
    void saveObjectWithExistingIdKeepsId() {
        PetTypeMapService service = new PetTypeMapService();
        PetType petType = new PetType();
        petType.setId(42L);

        assertSame(petType, service.save(petType));
        assertEquals(42L, service.findById(42L).getId());
    }

    @Test
    void visitWithoutPetThrows() {
        VisitMapService service = new VisitMapService();

        assertThrows(IllegalArgumentException.class, () -> service.save(new Visit()));
    }

    @Test
    void visitWithoutOwnerThrows() {
        VisitMapService service = new VisitMapService();
        Visit visit = new Visit();
        visit.setPet(new Pet());

        assertThrows(IllegalArgumentException.class, () -> service.save(visit));
    }

    @Test
    void visitWithoutPetIdThrows() {
        VisitMapService service = new VisitMapService();
        Owner owner = new Owner();
        owner.setId(7L);
        Pet pet = new Pet();
        pet.setOwner(owner);
        Visit visit = new Visit();
        visit.setPet(pet);

        assertThrows(IllegalArgumentException.class, () -> service.save(visit));
    }

    @Test
    void visitWithoutOwnerIdThrows() {
        VisitMapService service = new VisitMapService();
        Owner owner = new Owner();
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setOwner(owner);
        Visit visit = new Visit();
        visit.setPet(pet);

        assertThrows(IllegalArgumentException.class, () -> service.save(visit));
    }

    @Test
    void validVisitIsSaved() {
        VisitMapService service = new VisitMapService();
        Owner owner = new Owner();
        owner.setId(7L);
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setOwner(owner);
        Visit visit = new Visit();
        visit.setPet(pet);

        assertNotNull(service.save(visit));
    }

    @Test
    void vetWithoutSpecialitiesIsSaved() {
        VetMapService service = new VetMapService(mock(SpecialityService.class));

        assertNotNull(service.save(new Vet()));
    }

    @Test
    void vetWithUnsavedSpecialitySavesIt() {
        SpecialityService specialityService = mock(SpecialityService.class);
        Speciality persisted = new Speciality();
        persisted.setId(5L);
        when(specialityService.save(any(Speciality.class))).thenReturn(persisted);

        VetMapService service = new VetMapService(specialityService);
        Speciality speciality = new Speciality();
        Vet vet = new Vet();
        vet.getSpecialities().add(speciality);

        assertNotNull(service.save(vet));
        assertEquals(5L, speciality.getId());
        verify(specialityService).save(speciality);
    }

    @Test
    void vetWithSavedSpecialityDoesNotSaveItAgain() {
        SpecialityService specialityService = mock(SpecialityService.class);
        VetMapService service = new VetMapService(specialityService);
        Speciality speciality = new Speciality();
        speciality.setId(9L);
        Vet vet = new Vet();
        vet.getSpecialities().add(speciality);

        assertNotNull(service.save(vet));
        verifyNoInteractions(specialityService);
    }

    @Test
    void ownerWithSavedPetIsSaved() {
        OwnerMapService service = new OwnerMapService(mock(PetTypeService.class), mock(PetService.class));
        PetType petType = new PetType();
        petType.setId(1L);
        Pet pet = new Pet();
        pet.setId(3L);
        pet.setPetType(petType);
        Owner owner = new Owner();
        owner.getPets().add(pet);

        assertNotNull(service.save(owner));
    }

    @Test
    void ownerWithoutPetsIsSaved() {
        OwnerMapService service = new OwnerMapService(mock(PetTypeService.class), mock(PetService.class));
        Owner owner = new Owner();
        owner.setPets(null);

        assertNotNull(service.save(owner));
    }

    @Test
    void saveNullOwnerReturnsNull() {
        OwnerMapService service = new OwnerMapService(mock(PetTypeService.class), mock(PetService.class));

        assertNull(service.save(null));
    }

}
