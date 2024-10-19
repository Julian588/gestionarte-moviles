package co.com.example.gestionarte;

public class ListElement {
    public String nombreCard;
    public String descriptionCard;
    public String priceCard;
    public String dateCard;

    public ListElement(String nombreCard, String descriptionCard, String priceCard, String dateCard) {
        this.nombreCard = nombreCard;
        this.descriptionCard = descriptionCard;
        this.priceCard = priceCard;
        this.dateCard = dateCard;
    }

    public String getNombreCard() {
        return nombreCard;
    }

    public void setNombreCard(String nombreCard) {
        this.nombreCard = nombreCard;
    }

    public String getDescriptionCard() {
        return descriptionCard;
    }

    public void setDescriptionCard(String descriptionCard) {
        this.descriptionCard = descriptionCard;
    }

    public String getPriceCard() {
        return priceCard;
    }

    public void setPriceCard(String priceCard) {
        this.priceCard = priceCard;
    }

    public String getDateCard() {
        return dateCard;
    }
    public void setDateCard(String dateCard) {
        this.dateCard = dateCard;
    }
}
