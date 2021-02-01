package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.service.OwnerService;
import guru.springframework.sfgpetclinic.service.PetService;
import guru.springframework.sfgpetclinic.service.VetService;
import guru.springframework.sfgpetclinic.service.map.OwnerServiceMap;
import guru.springframework.sfgpetclinic.service.map.VetServiceMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;

    private final VetService vetService;

    public DataLoader(OwnerService ownerService, VetService vetService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
    }

    @Override
    public void run(String... args) throws Exception {

        Owner owner1 = new Owner();
        owner1.setFirstName("Dominique");
        owner1.setLastName("Boeckli");

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Ruedi");
        owner2.setLastName("Rudolf");

        ownerService.save(owner2);

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
