package entity;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class CategorieResponse {
	 @JsonProperty("categorie")
	    private List<Categorie> categories;

	public List<Categorie> getCategories() {
		return categories;
	}

	public void setCategories(List<Categorie> categories) {
		this.categories = categories;
	}
	 
}
