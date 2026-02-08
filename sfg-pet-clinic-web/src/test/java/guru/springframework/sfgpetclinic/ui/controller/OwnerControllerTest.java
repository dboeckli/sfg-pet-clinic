package guru.springframework.sfgpetclinic.ui.controller;

import guru.springframework.sfgpetclinic.service.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    OwnerService ownerService;

    @Mock
    Model model;

    @InjectMocks
    OwnerController ownerController;

    @Test
    void listOwners() {
        when(ownerService.findAll()).thenReturn(new HashSet<>());
        String view = ownerController.listOwners(model);

        assertEquals("owners/index", view);
    }

    @Test
    void findOwners() {
        String view = ownerController.findOwners();
        assertEquals("notimplemented", view);
    }
}