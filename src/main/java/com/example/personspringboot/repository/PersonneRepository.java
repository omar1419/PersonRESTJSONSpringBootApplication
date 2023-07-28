package com.example.personspringboot.repository;

import com.example.personspringboot.model.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PersonneRepository extends JpaRepository<Personne, Long> {

    @Query("SELECT p FROM Personne p WHERE" +
            " LOWER(p.nom) LIKE  CONCAT('%',:query, '%')" +
            "Or LOWER(p.prenom) LIKE CONCAT('%',:query, '%')")
    List<Personne> searchPersons(String query);
}
