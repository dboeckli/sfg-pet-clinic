package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.service.OwnerService;
import guru.springframework.sfgpetclinic.service.PetService;
import guru.springframework.sfgpetclinic.service.PetTypeService;
import guru.springframework.sfgpetclinic.service.VetService;
import guru.springframework.sfgpetclinic.service.map.OwnerServiceMap;
import guru.springframework.sfgpetclinic.service.map.VetServiceMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;

    private final VetService vetService;

    private final PetTypeService petTypeService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }

    @Override
    public void run(String... args) throws Exception {

        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDog = petTypeService.save(dog);

        PetType cat = new PetType();
        dog.setName("Cat");
        PetType savedCat = petTypeService.save(cat);

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

        Pet fionaPet = new Pet();
        fionaPet.setName("Fiona");
        fionaPet.setBirthDate(LocalDate.now());
        fionaPet.setOwner(ruedi);
        fionaPet.setPetType(savedCat);
        ruedi.getPets().add(fionaPet);

        System.out.println("owners intialized.....");

        Vet vet1 = new Vet();
        vet1.setFirstName("Meister");
        vet1.setLastName("Eder");

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Kid");
        vet2.setLastName("Pumukel");

        vetService.save(vet2);

        System.out.println("vets intialized.....");
    }
}
