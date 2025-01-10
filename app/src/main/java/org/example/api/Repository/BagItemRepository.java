package org.example.api.Repository;

import org.example.api.Model.BagItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BagItemRepository extends JpaRepository<BagItem, Integer> {
}
