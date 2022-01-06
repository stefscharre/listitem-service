package fact.it.listitemservice.repository;

import fact.it.listitemservice.model.ListItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListItemRepository extends MongoRepository <ListItem, String> {
    List<ListItem> findListItemsByUserId(Integer userid);
    List<ListItem> findListItemsByListNaam(String listnaam);
    List<ListItem> findListItemsByUserIdAndListNaam(Integer userid, String listnaam);

    ListItem findListItemByListItemCode(String code);

}
