package org.example.api.Repository;

import org.example.api.Model.Bag;
import org.example.api.Model.BagItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BagItemRepository extends JpaRepository<BagItem, Integer> {
    List<BagItem> findByBag(Bag bag);

    Optional<BagItem> findByBag_IdAndItem_Id(int bagId, int itemId);
    Optional<Object> findByItem_Id(int itemId);

    List<BagItem> findAllByBag_IdAndItem_Id(int id, int itemId);
}
