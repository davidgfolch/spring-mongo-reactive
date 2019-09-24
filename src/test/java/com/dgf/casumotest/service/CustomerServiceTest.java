package com.dgf.casumotest.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    private CustomerService service;

    @Test
    public void test() {
        StepVerifier
            .create(service.findByFirstNameAndLastNameIgnoreCase("John","Doe"))
            .assertNext(loaded -> assertEquals("John",loaded.getFirstName()))
            .expectComplete()
            .verify();
    }
}
