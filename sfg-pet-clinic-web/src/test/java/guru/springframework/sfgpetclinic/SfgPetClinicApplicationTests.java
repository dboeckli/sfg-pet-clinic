package guru.springframework.sfgpetclinic;

import guru.springframework.sfgpetclinic.service.OwnerService;
import guru.springframework.sfgpetclinic.service.PetTypeService;
import guru.springframework.sfgpetclinic.service.SpecialityService;
import guru.springframework.sfgpetclinic.service.VetService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(useMainMethod = SpringBootTest.UseMainMethod.ALWAYS)
@Slf4j
class SfgPetClinicApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private VetService vetService;

    @Autowired
    private PetTypeService petTypeService;

    @Autowired
    private SpecialityService specialityService;

    @Test
    void contextLoads() {
        assertNotNull(applicationContext, "Application context should not be null");
        log.info("Testing Spring 6 Application {}", applicationContext.getApplicationName());
    }

    @Test
    void testDataLoad() {
        assertAll("Data should be empty at start",
            () -> assertEquals(2, ownerService.findAll().size()),
            () -> assertEquals(2, vetService.findAll().size()),
            () -> assertEquals(2, petTypeService.findAll().size()),
            () -> assertEquals(3, specialityService.findAll().size())
        );
    }

}
