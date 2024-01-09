package metier;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.ws.rs.core.UriBuilder;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import entity.Categorie;
import entity.CategorieResponse;
import entity.Produit;
import entity.ProduitResponse;

public class ProduitCrud {
	Client client=Client.create(new DefaultClientConfig());
	URI uri=UriBuilder.fromUri("http://localhost:8080/magasin/").build();
	public ProduitCrud() {
		super();		
	}
	public void afficherAllProduit() throws JsonParseException, JsonMappingException, IOException {
	    ClientResponse rest = client.resource(uri).path("api").path("/produits").get(ClientResponse.class);
	    String corerephttp = rest.getEntity(String.class);
	    ObjectMapper mapper = new ObjectMapper();
	    JsonNode rootNode = mapper.readTree(corerephttp);
	    try {
	        if (rootNode.has("produit")) {
	            JsonNode produitNode = rootNode.get("produit");

	            if (produitNode.isArray()) {
	            	
	                List<Produit> produits = mapper.readValue(produitNode, new TypeReference<List<Produit>>() {});
	                if (produits.isEmpty()) {
	                    System.out.println("Aucun produit trouvé.");
	                } else {
	                    System.out.println("\t\t\t\t\tTous les produits dans mon boutique");
	                    displayTableHeader();
	                    for (Produit p : produits) {
	                        displayProduct(p.getCode(), p.getDesignation(), p.getPrix(), p.getQte(), p.getId_category());
	                    }
	                }
	            } else {
	            	Produit p = mapper.readValue(produitNode, Produit.class);
		        	 System.out.println("\t\t\t\t\tTous les produits dans mon boutique");
			            displayTableHeader();
			            displayProduct(p.getCode(), p.getDesignation(), p.getPrix(), p.getQte(), p.getId_category());
	            }
	        }
	    } catch (Exception e) {
	        System.out.print(e.getMessage());
	    }
	}


		
public Boolean existProduit(int ref) throws JsonParseException, JsonMappingException, IOException {	
    try {
        ClientResponse rest = client.resource(uri)
                .path("api")
                .path("/produit")
                .path(String.valueOf(ref))
                .get(ClientResponse.class);
        Status status = rest.getClientResponseStatus();
        
        if (status == Status.OK) {
            String corerephttp = rest.getEntity(String.class);
            return true;
        } else {
            return false;
        }
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    } 
    }

public void ajouterProduit(int code, String designation, Float prix, int qte,int id_category) {
    WebResource rest = null;
    try {
        rest = client.resource(uri).path("api").path("/addproduit");
        Produit newProduct = new Produit();
        newProduct.setCode(code);
        newProduct.setDesignation(designation);
        newProduct.setPrix(prix);
        newProduct.setQte(qte);
newProduct.setId_category(id_category);
        ObjectMapper mapper = new ObjectMapper();
        String jsonProduct = mapper.writeValueAsString(newProduct);
if(this.existCategorie(id_category)) {
        ClientResponse response = rest.type("application/json").post(ClientResponse.class, jsonProduct);
        

        if (response != null) {
            int status = response.getStatus();
            if (status == 200) {
                System.out.println("\t\t\t\t\tProduit ajouté avec succès !");
            } else {
            	System.out.println("\t\t\t\t\tce projet est deja exist avec ce code");
            }
            response.close(); 
        }}else {
        	System.out.println("\t\t\t\t\tcette catégorie n'existe pas !");
        }
    } catch (Exception e) {
        e.printStackTrace();
        
    } 
    }
	
public void updateProduit(int code, String designation, Float prix, int qte) {
    WebResource rest = null;
    try {
        rest = client.resource(uri).path("api").path("/updateproduit");
        Produit newProduct = new Produit();
        newProduct.setCode(code);
        newProduct.setDesignation(designation);
        newProduct.setPrix(prix);
        newProduct.setQte(qte);

        ObjectMapper mapper = new ObjectMapper();
        String jsonProduct = mapper.writeValueAsString(newProduct);

        ClientResponse response = rest.type("application/json").put(ClientResponse.class, jsonProduct);
        if (response != null) {
            int status = response.getStatus();
            if (status == 200) {
                System.out.println("\t\t\t\t\tProduit mis à jour avec succès  !");
            } else {
                System.out.println("\t\t\t\t\tErreur lors de la mis à jour du produit : " + status);
            }
            response.close(); 
        }
    } catch (Exception e) {
        e.printStackTrace();
    } 
    }
	
public void chercherProduit(String mc) throws JsonParseException, JsonMappingException, IOException {   
    ClientResponse rest = client.resource(uri).path("api").path("search").path(String.valueOf(mc)).get(ClientResponse.class);
    String corerephttp = rest.getEntity(String.class);
    ObjectMapper mapper = new ObjectMapper();
    JsonNode rootNode = mapper.readTree(corerephttp);
    try {
        if (rootNode.has("produit")) {
            JsonNode produitNode = rootNode.get("produit");

            if (produitNode.isArray()) {
            	
                List<Produit> produits = mapper.readValue(produitNode, new TypeReference<List<Produit>>() {});
                if (produits.isEmpty()) {
                    System.out.println("Aucun produit trouvé.");
                } else {
                    System.out.println("\t\t\t\t\tTous les produits dans mon boutique");
                    displayTableHeader();
                    for (Produit p : produits) {
                        displayProduct(p.getCode(), p.getDesignation(), p.getPrix(), p.getQte(), p.getId_category());
                    }
                }
            } else {
            	Produit p = mapper.readValue(produitNode, Produit.class);
	        	 System.out.println("\t\t\t\t\tTous les produits dans mon boutique");
		            displayTableHeader();
		            displayProduct(p.getCode(), p.getDesignation(), p.getPrix(), p.getQte(), p.getId_category());
            }
        }
    } catch (Exception e) {
        System.out.print(e.getMessage());
    }
    
    
}


public void supprimerProduit(int codeProduit) {
    try {
    	WebResource webResource = client.resource(uri).path("api").path("delete").path(String.valueOf(codeProduit));
        ClientResponse response = webResource.delete(ClientResponse.class);

        if (response.getStatus() == 200) {
            System.out.println("\t\t\t\t\tProduit supprimé avec succès !");
        } else {
            System.out.println("\t\t\t\t\t ce code n'existe pas");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
public Boolean existCategorie(int ref) throws JsonParseException, JsonMappingException, IOException {	
    try {
        ClientResponse rest = client.resource(uri)
                .path("api")
                .path("/existCategorie")
                .path(String.valueOf(ref))
                .get(ClientResponse.class);
        Status status = rest.getClientResponseStatus();
        
        if (status == Status.OK) {
            String corerephttp = rest.getEntity(String.class);
            return true;
        } else {
            return false;
        }
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    } 
    }
public void listerParCategory(int id) throws JsonParseException, JsonMappingException, IOException {   
    ClientResponse rest = client.resource(uri).path("api").path("listerParCategory").path(String.valueOf(id)).get(ClientResponse.class);
    String corerephttp = rest.getEntity(String.class);
    ObjectMapper mapper = new ObjectMapper();
    JsonNode rootNode = mapper.readTree(corerephttp);
    try {
    	 if(!rootNode.isContainerNode()) {
    		 System.out.println("\t\t\t\t\taucun produit avec cette categorie");
    	 }
    	 
        if (rootNode.has("produit")) {
            JsonNode produitNode = rootNode.get("produit");
           
            if (produitNode.isArray()) {
            	
                List<Produit> produits = mapper.readValue(produitNode, new TypeReference<List<Produit>>() {});
                	
                
                    System.out.println("\t\t\t\t\tTous les produits dans mon boutique");
                    displayTableHeader();
                    for (Produit p : produits) {
                        displayProduct(p.getCode(), p.getDesignation(), p.getPrix(), p.getQte(), p.getId_category());
                    }
                
            } else {
            	Produit p = mapper.readValue(produitNode, Produit.class);
	        	 System.out.println("\t\t\t\t\tTous les produits dans mon boutique");
		            displayTableHeader();
		            displayProduct(p.getCode(), p.getDesignation(), p.getPrix(), p.getQte(), p.getId_category());
            }
        }
    } catch (Exception e) {
        System.out.print(e.getMessage());
    }
}
public static void displayTableHeader() {
    System.out.println("\t\t\t|------------------------------------------------------------------------|");
    System.out.println("\t\t\t| Code       | Designation         | Prix   | Quantité    |Categorie     |");
    System.out.println("\t\t\t|------------|---------------------|------- |-------------|--------------|");
}
public static void displayProduct(int code, String designation, float prix, int qte,int id_category) {
    System.out.printf("\t\t\t| %-11d| %-20s| %-7.2f| %-12d| %-11d  |%n", code, designation, prix, qte,id_category);
}

}
