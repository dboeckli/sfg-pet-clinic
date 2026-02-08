package guru.springframework.sfgpetclinic.ui.controller;

import guru.springframework.sfgpetclinic.service.VetService;
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
class VetControllerTest {

    @Mock
    VetService vetService;

    @Mock
    Model model;

    @InjectMocks
    VetController vetController;

    @Test
    void listVets() {
        when(vetService.findAll()).thenReturn(new HashSet<>());
        String view = vetController.listVets(model);

        assertEquals("vets/index", view);
    }
}