package fact.it.listitemservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.listitemservice.model.ListItem;
import fact.it.listitemservice.repository.ListItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class ListitemControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ListItemRepository listItemRepository;
    private ListItem listItemUser1List1 = new ListItem("0001",001,"taak engels", "school","afmaken", false);
    private ListItem listItemUser2List1 = new ListItem("0002",002,"taak frans", "school","frans afmaken", false);
    private ListItem listItemUser2List2 = new ListItem("0003",002,"haag snoeien", "tuin","7 uur", false);
    private ListItem listItemToBeDeleted= new ListItem("0004",999,"mails sturen", "werk","afmaken", false);
    private ListItem listItemUser1List1Todo2 = new ListItem("0005",001,"taak angular", "school","angular afmaken", false);


    @BeforeEach
    public void beforeAllTest(){
        listItemRepository.deleteAll();
        listItemRepository.save(listItemUser1List1);
        listItemRepository.save(listItemUser2List1);
        listItemRepository.save(listItemUser2List2);
        listItemRepository.save(listItemUser1List1Todo2);
        listItemRepository.save(listItemToBeDeleted);
    }
    @AfterEach
    public void afterAllTests(){
        listItemRepository.deleteAll();
    }
    private ObjectMapper mapper = new ObjectMapper();
    @Test
    public void givenlistitem_whengetListitemsByUserIdAndNaam_thenReturnJsonListitem() throws Exception{
        List<ListItem> listItemList = new ArrayList<>();
        listItemList.add(listItemUser1List1);
        listItemList.add(listItemUser1List1Todo2);
        mockMvc.perform(get("/listitems/user/{userId}/list/naam/{naam}", 001, "school"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((jsonPath("$", hasSize(2))))
                .andExpect(jsonPath("$[0].listItemCode", is("0001")))
                .andExpect(jsonPath("$[0].userId", is(001)))
                .andExpect(jsonPath("$[0].titel", is("taak engels")))
                .andExpect(jsonPath("$[0].listNaam", is("school")))
                .andExpect(jsonPath("$[0].beschrijving", is("afmaken")))
                .andExpect(jsonPath("$[0].status", is(false)))
                .andExpect(jsonPath("$[1].listItemCode", is("0005")))
                .andExpect(jsonPath("$[1].userId", is(001)))
                .andExpect(jsonPath("$[1].titel", is("taak angular")))
                .andExpect(jsonPath("$[1].listNaam", is("school")))
                .andExpect(jsonPath("$[1].beschrijving", is("angular afmaken")))
                .andExpect(jsonPath("$[1].status", is(false)));

    }
    @Test
    public void givenlistitem_whenGetListitemsByNaam_thenReturnJsonListitems() throws Exception{
        List<ListItem> listItemList = new ArrayList<>();
        listItemList.add(listItemUser1List1);
        listItemList.add(listItemUser2List1);
        listItemList.add(listItemUser1List1Todo2);
        mockMvc.perform(get("/listitems/list/naam/{naam}", "school"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((jsonPath("$", hasSize(3))))
                .andExpect(jsonPath("$[0].listItemCode", is("0001")))
                .andExpect(jsonPath("$[0].userId", is(001)))
                .andExpect(jsonPath("$[0].titel", is("taak engels")))
                .andExpect(jsonPath("$[0].listNaam", is("school")))
                .andExpect(jsonPath("$[0].beschrijving", is("afmaken")))
                .andExpect(jsonPath("$[0].status", is(false)))
                .andExpect(jsonPath("$[1].listItemCode", is("0002")))
                .andExpect(jsonPath("$[1].userId", is(002)))
                .andExpect(jsonPath("$[1].titel", is("taak frans")))
                .andExpect(jsonPath("$[1].listNaam", is("school")))
                .andExpect(jsonPath("$[1].beschrijving", is("frans afmaken")))
                .andExpect(jsonPath("$[1].status", is(false)))
                .andExpect(jsonPath("$[2].listItemCode", is("0005")))
                .andExpect(jsonPath("$[2].userId", is(001)))
                .andExpect(jsonPath("$[2].titel", is("taak angular")))
                .andExpect(jsonPath("$[2].listNaam", is("school")))
                .andExpect(jsonPath("$[2].beschrijving", is("angular afmaken")))
                .andExpect(jsonPath("$[2].status", is(false)));

    }
    @Test
    public void givenlistitem_whenGetListitemByCode_thenReturnJsonListitem() throws Exception{
        mockMvc.perform(get("/listitems/{code}", "0001"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.listItemCode", is("0001")))
                .andExpect(jsonPath("$.userId", is(001)))
                .andExpect(jsonPath("$.titel", is("taak engels")))
                .andExpect(jsonPath("$.listNaam", is("school")))
                .andExpect(jsonPath("$.beschrijving", is("afmaken")))
                .andExpect(jsonPath("$.status", is(false)));

    }
    @Test
    public void  givenlistitem_whenGetListItemsByUserId_thenReturnJsonListitems() throws Exception {

        List<ListItem> listItemList = new ArrayList<>();
        listItemList.add(listItemUser2List1);
        listItemList.add(listItemUser2List2);


        mockMvc.perform(get("/listitems/user/{userId}", 002))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].listItemCode", is("0002")))
                .andExpect(jsonPath("$[0].userId", is(002)))
                .andExpect(jsonPath("$[0].titel", is("taak frans")))
                .andExpect(jsonPath("$[0].listNaam", is("school")))
                .andExpect(jsonPath("$[0].beschrijving", is("frans afmaken")))
                .andExpect(jsonPath("$[0].status", is(false)))
                .andExpect(jsonPath("$[1].listItemCode", is("0003")))
                .andExpect(jsonPath("$[1].userId", is(002)))
                .andExpect(jsonPath("$[1].titel", is("haag snoeien")))
                .andExpect(jsonPath("$[1].listNaam", is("tuin")))
                .andExpect(jsonPath("$[1].beschrijving", is("7 uur")))
                .andExpect(jsonPath("$[1].status", is(false)));
    }





    @Test
    public void whenPostListitem_thenReturnJsonListitem() throws Exception {
        ListItem listItemUser3List1 = new ListItem("0006",003,"taak react", "school","afmaken", false);

        mockMvc.perform(post("/listitems")
                        .content(mapper.writeValueAsString(listItemUser3List1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.listItemCode", is("0006")))
                .andExpect(jsonPath("$.userId", is(003)))
                .andExpect(jsonPath("$.titel", is("taak react")))
                .andExpect(jsonPath("$.listNaam", is("school")))
                .andExpect(jsonPath("$.beschrijving", is("afmaken")))
                .andExpect(jsonPath("$.status", is(false)));
    }

    @Test
    public void whenPutListitem_thenReturnJsonListitem() throws Exception {

        ListItem updatedListitem = new ListItem("0001",001,"taak engels", "school","afmaken", true);

        mockMvc.perform(put("/listitems")
                        .content(mapper.writeValueAsString(updatedListitem))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.listItemCode", is("0001")))
                .andExpect(jsonPath("$.userId", is(001)))
                .andExpect(jsonPath("$.titel", is("taak engels")))
                .andExpect(jsonPath("$.listNaam", is("school")))
                .andExpect(jsonPath("$.beschrijving", is("afmaken")))
                .andExpect(jsonPath("$.status", is(true)));
    }

    @Test
    public void givenlistitem_whenDeleteListitem_thenStatusOk() throws Exception {

        mockMvc.perform(delete("/listitems/{code}", "0004")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void  givenlistitem_whenDeleteListitem_thenStatusNotFound() throws Exception {

        mockMvc.perform(delete("/listitems/{code}", "0008")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
