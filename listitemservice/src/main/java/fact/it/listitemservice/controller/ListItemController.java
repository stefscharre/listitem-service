package fact.it.listitemservice.controller;

import fact.it.listitemservice.model.ListItem;
import fact.it.listitemservice.repository.ListItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class ListItemController {
    @Autowired
    private ListItemRepository listItemRepository;

    @GetMapping("/listitems/user/{userId}")
    public List<ListItem> getListitemByUserId(@PathVariable Integer userId){
        return listItemRepository.findListItemsByUserId(userId);
    }
    @GetMapping("/listitems/list/naam/{naam}")
    public List<ListItem> getListitemByListNaam(@PathVariable String naam){
        return listItemRepository.findListItemsByListNaam(naam);
    }

    @GetMapping("/listitems/user/{userId}/list/naam/{naam}")
    public List<ListItem> getListitemByUserIdAndNaam(@PathVariable Integer userId, @PathVariable String naam){
        return listItemRepository.findListItemsByUserIdAndListNaam(userId, naam);
    }
    @GetMapping("/listitems/{code}")
    public ListItem getListitemById(@PathVariable String code){
        return listItemRepository.findListItemByListItemCode(code);
    }
    @PostMapping("/listitems")
    public ListItem addListitem(@RequestBody ListItem listItem){
        listItemRepository.save(listItem);
        return listItem;
    }
    @PutMapping("/listitems")
    public ListItem updateListitem(@RequestBody ListItem updatedListItem){
        ListItem retrievedListitem = listItemRepository.findListItemByListItemCode(updatedListItem.getListItemCode());
        retrievedListitem.setListNaam(updatedListItem.getListNaam());
        retrievedListitem.setListItemCode(updatedListItem.getListItemCode());
        retrievedListitem.setTitel(updatedListItem.getTitel());
        retrievedListitem.setUserId(updatedListItem.getUserId());
        retrievedListitem.setBeschrijving(updatedListItem.getBeschrijving());
        retrievedListitem.setStatus(updatedListItem.getStatus());
        listItemRepository.save(retrievedListitem);
        return retrievedListitem;
    }
    @DeleteMapping("/listitems/{code}")
    public ResponseEntity deleteListItem(@PathVariable String code){
        ListItem listItem = listItemRepository.findListItemByListItemCode(code);
        if(listItem!=null){
            listItemRepository.delete(listItem);
            return ResponseEntity.ok().build();

        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @PostConstruct
    public void fillDB(){
        if(listItemRepository.count()==0){
            listItemRepository.save(new ListItem("a1",1,"taak", "engels","doe dit", true));
        }
    }
}
