package guru.springframework.spring5webfluxrest.bootstrap;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repositories.CategoryRepository;
import guru.springframework.spring5webfluxrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Bootstrap implements CommandLineRunner {
    private CategoryRepository categoryRepository;
    private VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //invoca al repositorio
        if(categoryRepository.count().block() == 0){
            //cargar los datos
            System.out.println("*** LOADING DATA... ***");
            categoryRepository.save(Category.builder()
                    .description("Fruits").build()).block();
            categoryRepository.save(Category.builder()
                    .description("Nuts").build()).block();
            categoryRepository.save(Category.builder()
                    .description("Breads").build()).block();
            categoryRepository.save(Category.builder()
                    .description("Meats").build()).block();
            categoryRepository.save(Category.builder()
                    .description("Eggs").build()).block();
            System.out.println("Loaded Categories: " + categoryRepository.count().block());

            vendorRepository.save(Vendor.builder()
                    .firstName("Sam")
                    .lastName("Licea")
                    .build()).block();
            vendorRepository.save(Vendor.builder()
                    .firstName("Eva")
                    .lastName("Medina")
                    .build()).block();
            vendorRepository.save(Vendor.builder()
                    .firstName("Jose Miguel")
                    .lastName("de la Mora")
                    .build()).block();
            vendorRepository.save(Vendor.builder()
                    .firstName("Jose Luis")
                    .lastName("Lobera")
                    .build()).block();
            vendorRepository.save(Vendor.builder()
                    .firstName("Diego")
                    .lastName("Ramirez")
                    .build()).block();
            System.out.println("Loaded Vendors: " + vendorRepository.count().block());
        }
    }
}
