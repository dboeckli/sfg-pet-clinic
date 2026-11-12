package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.service.OwnerService;
import guru.springframework.sfgpetclinic.service.PetTypeService;
import guru.springframework.sfgpetclinic.service.SpecialityService;
import guru.springframework.sfgpetclinic.service.VetService;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class DataLoaderTest {

    @Test
    void runDoesNothingWhenPetTypesAlreadyExist() throws Exception {
        OwnerService ownerService = mock(OwnerService.class);
        VetService vetService = mock(VetService.class);
        PetTypeService petTypeService = mock(PetTypeService.class);
        SpecialityService specialityService = mock(SpecialityService.class);
        when(petTypeService.findAll()).thenReturn(Set.of(new PetType()));

        DataLoader dataLoader = new DataLoader(ownerService, vetService, petTypeService, specialityService);
        dataLoader.run();

        verify(petTypeService).findAll();
        verifyNoInteractions(ownerService, vetService, specialityService);
    }

}
