package org.kcheremnov.controllers;

import org.kcheremnov.entities.Item;
import org.kcheremnov.models.ItemRequest;
import org.kcheremnov.services.ItemsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemsController {

    private final ItemsService itemsService;

    public ItemsController(
            ItemsService itemsService
    ) {
        this.itemsService = itemsService;
    }

    @GetMapping
    public ResponseEntity<List<Item>> items(
            @RequestParam(required = false) Integer categoryId
    ) {
        List<Item> items = itemsService.getItems(categoryId);

        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(
            @PathVariable Long id
    ) {
        Item item = itemsService.getItemById(id);

        return ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<Item> addItem(
            @RequestBody ItemRequest itemToAdd
    ) {
        Item item = itemsService.saveItem(itemToAdd);

        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable Long id
    ) {
        itemsService.deleteItem(id);

        return ResponseEntity.ok().build();
    }
}
