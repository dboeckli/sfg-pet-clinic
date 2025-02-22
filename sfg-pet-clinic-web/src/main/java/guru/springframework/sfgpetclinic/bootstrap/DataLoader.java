package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.*;
import guru.springframework.sfgpetclinic.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;

    private final VetService vetService;

    private final PetTypeService petTypeService;

    private final SpecialityService specialityService;

    private final VisitService visitService;

    public DataLoader(OwnerService ownerService,
                      VetService vetService,
                      PetTypeService petTypeService,
                      SpecialityService specialityService,
                      VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (petTypeService.findAll().isEmpty()) {
            loadData();
        }
    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDog = petTypeService.save(dog);

        PetType cat = new PetType();
        dog.setName("Cat");
        PetType savedCat = petTypeService.save(cat);

        log.info("petType intialized: {}", petTypeService.findAll().size());

        Speciality radiology = new Speciality();
        radiology.setDescription("radiology");
        Speciality savedRadiology = specialityService.save(radiology);

        Speciality surgery = new Speciality();
        surgery.setDescription("surgery");
        Speciality savedSurgery = specialityService.save(surgery);

        Speciality dentistry = new Speciality();
        dentistry.setDescription("dentistry");
        specialityService.save(dentistry);

        log.info("speciality intialized: {}", specialityService.findAll().size());

        Owner owner1 = new Owner();
        owner1.setFirstName("Dominique");
        owner1.setLastName("Boeckli");
        owner1.setAddress("Zumhofstrasse 72");
        owner1.setCity("Kriens");
        owner1.setTelephone("0413207535");
        ownerService.save(owner1);

        Pet roscoePet = new Pet();
        roscoePet.setName("Rosoe");
        roscoePet.setBirthDate(LocalDate.now());
        roscoePet.setOwner(owner1);
        roscoePet.setPetType(savedDog);
        owner1.getPets().add(roscoePet);

        Owner ruedi = new Owner();
        ruedi.setFirstName("Ruedi");
        ruedi.setLastName("Rudolf");
        ruedi.setAddress("Weinbergstrasse 68");
        ruedi.setCity("Erlenbach");
        ruedi.setTelephone("0449151997");
        ownerService.save(ruedi);

        log.info("owners intialized: {}", ownerService.findAll().size());

        Visit catVisit = new Visit();
        catVisit.setPet(roscoePet);
        catVisit.setDate(LocalDate.now());
        catVisit.setDescription("nice pussy");

        Pet fionaPet = new Pet();
        fionaPet.setName("Fiona");
        fionaPet.setBirthDate(LocalDate.now());
        fionaPet.setOwner(ruedi);
        fionaPet.setPetType(savedCat);
        ruedi.getPets().add(fionaPet);

        Vet vet1 = new Vet();
        vet1.setFirstName("Meister");
        vet1.setLastName("Eder");
        vet1.getSpecialities().add(savedRadiology);
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Kid");
        vet2.setLastName("Pumukel");
        vet2.getSpecialities().add(savedSurgery);
        vetService.save(vet2);

        log.info("vets intialized: {}", vetService.findAll().size());
    }
}
