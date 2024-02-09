package dev.beomseok.jpashop.service;

import dev.beomseok.jpashop.domain.item.Item;
import dev.beomseok.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public void saveItem(Item item){
        itemRepository.save(item);
    }

    public Item findItem(Long itemId){
        return itemRepository.findOne(itemId);
    }

    public List<Item> findAll(){
        return itemRepository.findAll();
    }
}
