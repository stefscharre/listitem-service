package fact.it.listitemservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection ="listitems")
public class ListItem {
    @Id
    private int id;
    private String listItemCode;
    private Integer userId;
    private String titel;
    private String listNaam;
    private String beschrijving;
    private Boolean status;


    public ListItem(String listItemCode, Integer userId, String titel, String listNaam, String beschrijving, Boolean status) {
        this.listItemCode = listItemCode;
        this.userId = userId;
        this.titel = titel;
        this.listNaam = listNaam;
        this.beschrijving = beschrijving;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getListItemCode() {
        return listItemCode;
    }

    public void setListItemCode(String listItemCode) {
        this.listItemCode = listItemCode;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getListNaam() {
        return listNaam;
    }

    public void setListNaam(String listNaam) {
        this.listNaam = listNaam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


}
