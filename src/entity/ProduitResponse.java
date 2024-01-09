package entity;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ProduitResponse {
    @JsonProperty("produits")
    private List<Produit> produits;
    
    @JsonProperty("produit") // This field represents a single product
    private Produit produit;

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    @Override
    public String toString() {
        return "ProduitResponse [produits=" + produits + ", produit=" + produit + "]";
    }
}
