package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;

public class VendorControllerTest {
    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorController vendorController;

    @BeforeEach
    public void setUp() throws Exception {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    public void list() throws Exception {
        BDDMockito.given(vendorRepository.findAll())
                .willReturn(Flux.just(Vendor.builder().firstName("Perro").lastName("Licea").build()
                , Vendor.builder().firstName("Simba").lastName("Licea").build()));

        webTestClient.get()
                .uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    public void getById() throws Exception {
        BDDMockito.given(vendorRepository.findById("xID"))
                .willReturn(Mono.just(Vendor.builder().firstName("Sam").lastName("Licea").build()));
        webTestClient.get()
                .uri("/api/v1/vendors/xID")
                .exchange()
                .expectBodyList(Vendor.class);

    }

    @Test
    public void testCreateVendor() throws Exception {
        BDDMockito.given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().build()));

        Mono<Vendor> newVendor = Mono.just(Vendor.builder().firstName("anyName").lastName("anyLName").build());

        webTestClient.post()
                .uri("/api/v1/vendors")
                .body(newVendor, Vendor.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void testUpdateVendor() throws Exception {
        BDDMockito.given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> updateVendor = Mono.just(Vendor.builder().firstName("Somename").lastName("somelname").build());

        webTestClient.put()
                .uri("/api/v1/vendors/whatever")
                .body(updateVendor, Vendor.class)
                .exchange()
                .expectStatus().isOk();
    }
}