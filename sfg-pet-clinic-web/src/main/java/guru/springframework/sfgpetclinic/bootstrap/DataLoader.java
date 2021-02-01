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

    public DataLoader() {
        this.ownerService = new OwnerServiceMap();
        this.vetService = new VetServiceMap();
    }

    @Override
    public void run(String... args) throws Exception {

        Owner owner1 = new Owner();
        owner1.setFirstName("Dominique");
        owner1.setLastName("Boeckli");
        owner1.setId(1L);

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Ruedi");
        owner2.setLastName("Rudolf");
        owner2.setId(2L);

        ownerService.save(owner2);

        System.out.println("owners intialized.....");

        Vet vet1 = new Vet();
        vet1.setFirstName("Meister");
        vet1.setLastName("Eder");
        vet1.setId(1L);

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Kid");
        vet2.setLastName("Pumukel");
        vet2.setId(2L);

        vetService.save(vet2);

        System.out.println("vets intialized.....");
    }
}
