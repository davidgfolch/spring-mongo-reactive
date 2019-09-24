package com.dgf.casumotest.repo;

import static junit.framework.TestCase.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerRepoTest {

    @Autowired
    private CustomerRepo repo;

    @Test
    public void test() {
        StepVerifier
            .create(repo.findByFirstNameAndLastNameIgnoreCase("John", "Doe"))
            .assertNext(customer -> {
                assertEquals("John",customer.getFirstName());
                assertEquals("Doe",customer.getLastName());
            })
            .expectComplete()
            .verify();
    }
}
