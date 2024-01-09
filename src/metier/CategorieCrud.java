package metier;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.type.TypeReference;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import entity.Categorie;
import entity.CategorieResponse;
import entity.Produit;
import entity.ProduitResponse;

public class CategorieCrud {
	Client client=Client.create(new DefaultClientConfig());
	URI uri=UriBuilder.fromUri("http://localhost:8080/magasin/").build();
	public CategorieCrud() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void afficherAllCategorie() throws JsonParseException, JsonMappingException, IOException {
	   ClientResponse rest = client.resource(uri).path("api").path("/categories").get(ClientResponse.class);
	    String corerephttp = rest.getEntity(String.class);
	    ObjectMapper mapper = new ObjectMapper();
	    JsonNode rootNode = mapper.readTree(corerephttp);
	    try {
	  
	    	if(rootNode.has("categorie")) {
	    		
	    		JsonNode categorienode=rootNode.get("categorie");
	    		if(categorienode.isArray()) {
	    			CategorieResponse categorieResponse = mapper.readValue(corerephttp, CategorieResponse.class);
		    	 	List<Categorie> list=categorieResponse.getCategories();        
		    		if (list.isEmpty()) {
		            System.out.println("Aucun produit trouvé.");
		        } else {
		            System.out.println("\t\t\t\t\tTous les categories dans mon boutique");
		            displayTableHeader();
		            for (Categorie c : list) {
		                displayCategorie(c.getCodeCat(),c.getDesignationCat());
		            }
	    		}
	    		
	        }else {
	        	
	        	Categorie categorie = mapper.readValue(categorienode, Categorie.class);
	        	 System.out.println("\t\t\t\t\tTous les categories dans mon boutique");
		            displayTableHeader();
		            displayCategorie(categorie.getCodeCat(), categorie.getDesignationCat());
		            
	        }
	    }} catch (Exception e) {
	        System.out.print(e.getMessage());
	        
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

public void ajouterCategorie(int code, String designation) {
    WebResource rest = null;
    try {
        rest = client.resource(uri).path("api").path("/addcategorie");
        Categorie newCategorie = new Categorie();
        newCategorie.setCodeCat(code);
        newCategorie.setDesignationCat(designation);
     
        ObjectMapper mapper = new ObjectMapper();
        String jsonCategorie = mapper.writeValueAsString(newCategorie);

        ClientResponse response = rest.type("application/json").post(ClientResponse.class, jsonCategorie);
        
        if (response != null) {
            int status = response.getStatus();
            if (status == 200) {
                System.out.println("\t\t\t\t\tCategorie ajouté avec succès !");
            } else {
            	System.out.println("\t\t\t\t\tcette catégorie est deja exist avec ce code");
            }
            response.close(); 
        }
    } catch (Exception e) {
        e.printStackTrace();
        
    } 
    }
	
public void updateCategorie(int code, String designation) {
    WebResource rest = null;
    try {
        rest = client.resource(uri).path("api").path("/updatecategorie");
        Categorie newCategorie = new Categorie();
        newCategorie.setCodeCat(code);
        newCategorie.setDesignationCat(designation);

        ObjectMapper mapper = new ObjectMapper();
        String jsonCategorie = mapper.writeValueAsString(newCategorie);

        ClientResponse response = rest.type("application/json").put(ClientResponse.class, jsonCategorie);
        if (response != null) {
            int status = response.getStatus();
            if (status == 200) {
                System.out.println("\t\t\t\t\tCategorie mis à jour avec succès  !");
            } else {
                System.out.println("\t\t\t\t\tErreur lors de la mis à jour du Categorie : " + status);
            }
            response.close(); 
        }
    } catch (Exception e) {
        e.printStackTrace();
    } 
    }
	
public void chercherCategorie(String mc) throws JsonParseException, JsonMappingException, IOException {   
    ClientResponse rest = client.resource(uri).path("api").path("searchCategorie").path(String.valueOf(mc)).get(ClientResponse.class);
    String corerephttp = rest.getEntity(String.class);
    System.out.println(corerephttp);
    ObjectMapper mapper = new ObjectMapper();
}


public void supprimerCategorie(int code) {
    try {
    	WebResource webResource = client.resource(uri).path("api").path("deleteCategorie").path(String.valueOf(code));
        ClientResponse response = webResource.delete(ClientResponse.class);

        if (response.getStatus() == 200) {
            System.out.println("\t\t\t\t\tCategorie supprimé avec succès !");
        } else {
            System.out.println("\t\t\t\t\t ce code n'existe pas");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
public static void displayTableHeader() {
    System.out.println("\t\t\t\t\t|----------------------------------|");
    System.out.println("\t\t\t\t\t| Code       | Designation         |");
    System.out.println("\t\t\t\t\t|------------|---------------------|");
}
public static void displayCategorie(int code, String designation) {
    System.out.printf("\t\t\t\t\t| %-11d| %-20s|%n", code, designation);
}
}
