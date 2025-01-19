package org.kcheremnov.repositories;

import org.kcheremnov.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Item, Long> {
    List<Item> findByCategoryId(Integer categoryId);
}