package guru.springframework.sfgpetclinic.ui.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IndexControllerTest {

    @Test
    void ooopsHandler() {
        IndexController indexController = new IndexController();
        String result = indexController.ooopsHandler();

        assertEquals("notimplemented", result);
    }

    @Test
    void index() {
        IndexController indexController = new IndexController();
        String view = indexController.index();

        assertEquals("index", view);
    }
}