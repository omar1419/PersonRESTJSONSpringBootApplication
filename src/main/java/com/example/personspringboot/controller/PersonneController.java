package com.example.personspringboot.controller;

import java.util.HashMap;

import java.util.List;
import java.util.Map;



import com.example.personspringboot.exception.ResourceNotFoundException;
import com.example.personspringboot.model.Personne;
import com.example.personspringboot.repository.PersonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/personne")
public class PersonneController {
    @Autowired
    private PersonneRepository personneRepository;

    @GetMapping("/all")
    public List<Personne> getAllPersonnes() {
        return personneRepository.findAll();
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Personne> getPersonneById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        Personne personne = personneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Personne not found for this id : " + id));
        return ResponseEntity.ok().body(personne);
    }
    @GetMapping("/search")
    public ResponseEntity <List <Personne>> searchPersons(@RequestParam("query") String query) {
        return   ResponseEntity.ok(personneRepository.searchPersons(query));
    }
    @PostMapping("/add")
    public Personne createPersonne(@Valid @RequestBody Personne personne) {
        return personneRepository.save(personne);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Personne> updatePersonne(@PathVariable(value = "id") Long id,
                                                   @Valid @RequestBody Personne personneDetails) throws ResourceNotFoundException {
        Personne personne = personneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Personne not found for this id : " + id));


        personne.setPrenom(personneDetails.getPrenom());
        personne.setNom(personneDetails.getNom());
        final Personne updatedPersonne = personneRepository.save(personne);
        return ResponseEntity.ok(updatedPersonne);
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Boolean> deletePersonne(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        Personne personne = personneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Personne not found for this id : " + id));

        personneRepository.delete(personne);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
