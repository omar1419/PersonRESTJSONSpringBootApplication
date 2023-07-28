package com.example.personspringboot;

import com.example.personspringboot.model.Personne;
import com.example.personspringboot.repository.PersonneRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.annotation.Order;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersonspringbootApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonspringbootApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private PersonneRepository personneRepository;
    @LocalServerPort
    private int port;
    private String getRootUrl() {
        return "http://localhost:" + port;
    }
    @Test
    void contextLoads() {
    }

    @Test
    @Order(3)
    @Rollback(value = false)
    public void testGetAllPersonnes() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/personne/all",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
        System.out.println(response.getBody());
    }
    @Test
    @Order(2)
    @Rollback(value = false)
    public void testGetPersonneById() {
        Personne personne = restTemplate.getForObject(getRootUrl() + "/personne/find/1", Personne.class);
        System.out.println(personne.getNom());
        System.out.println(personne.getPrenom());
        assertNotNull(personne);
    }
    @Test
    @Order(6)
    @Rollback(value = false)
    public void testSearchFilterPersonne() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/personne/search/?query=séb",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
        System.out.println(response.getBody());
    }
    @Test
    @Order(1)
    @Rollback(value = false)
    public void testCreatePerson() {
        Personne personne = new Personne();
        personne.setNom("Johnny");
        personne.setPrenom("Deep");

        ResponseEntity<Personne> postResponse = restTemplate.postForEntity(getRootUrl() + "/personne/add", personne, Personne.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
        System.out.println(postResponse.getBody());
    }
    @Test
    @Order(4)
    @Rollback(value = false)
    public void testUpdatePersonne() {
        //int id =4;
        Personne personne = personneRepository.findById(9L).get();
        personne.setPrenom("test1admin");
        personne.setNom("test1admin");
        Personne perUpdate = personneRepository.save(personne);
        Assertions.assertThat(perUpdate.getPrenom()).isEqualTo("test1admin");
        Assertions.assertThat(perUpdate.getNom()).isEqualTo("test1admin");

       /* 	int id = 8;
        Personne personne = restTemplate.getForObject(getRootUrl() + "/personne/update/" + id, Personne.class);

        personne.setNom("admin1");
        personne.setPrenom("admin2");

		restTemplate.put(getRootUrl() + "/personne/update/" + id, personne);

        Personne updatedPersonne = restTemplate.getForObject(getRootUrl() + "/personne/update/" + id, Personne.class);
		assertNotNull(updatedPersonne);*/

    }
    @Test
    @Order(5)
    @Rollback(value = false)
    public void testDeletePersonne() {
        int id = 4;
        Personne personne = restTemplate.getForObject(getRootUrl() + "/personne/delete/" + id, Personne.class);
        //assertNotNull(personne);

        restTemplate.delete(getRootUrl() + "/personne/delete/" + id);

        try {
            personne = restTemplate.getForObject(getRootUrl() + "/personne/delete/" + id, Personne.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }


}
