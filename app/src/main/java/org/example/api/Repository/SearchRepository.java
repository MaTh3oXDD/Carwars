package org.example.api.Repository;

import org.example.api.Model.Search;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SearchRepository extends JpaRepository<Search, Integer> {
    Optional<Search> findByName(String name);
    List<Search> findAll();

}
