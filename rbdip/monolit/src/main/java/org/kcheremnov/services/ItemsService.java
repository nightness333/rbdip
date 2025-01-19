package org.kcheremnov.services;

import org.kcheremnov.entities.Item;
import org.kcheremnov.models.ItemRequest;
import org.kcheremnov.repositories.ItemsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemsService {

    private final ItemsRepository itemsRepository;

    public ItemsService(
            ItemsRepository itemsRepository
    ) {
        this.itemsRepository = itemsRepository;
    }

    public List<Item> getItems(Integer categoryId) {
        List<Item> items = new ArrayList<>();

        if (categoryId != null) {
            items.addAll(
                    itemsRepository.findByCategoryId(categoryId)
            );
        } else {
            itemsRepository.findAll().forEach(items::add);
        }

        return items;
    }

    public Item getItemById(Long id) {
        Optional<Item> item = itemsRepository.findById(id);

        if (!item.isPresent()) {
            // TODO: Изменить ошибку
            throw new IllegalArgumentException("No such items");
        }

        return item.get();
    }

    public Item saveItem(ItemRequest itemRequest) {
        Item newItem = new Item();

        newItem.setPrice(itemRequest.getPrice());
        newItem.setNumber(itemRequest.getNumber());
        newItem.setCategoryId(itemRequest.getCategoryId());
        newItem.setDescription(itemRequest.getDescription());

        return itemsRepository.save(newItem);
    }

    public void deleteItem(Long id) {
        itemsRepository.deleteById(id);
    }
}
