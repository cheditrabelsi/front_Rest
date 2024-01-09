package entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Categorie {
	private int codeCat;
	private String designationCat;
	public Categorie() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Categorie(int codeCat, String designationCat) {
		super();
		this.codeCat = codeCat;
		this.designationCat = designationCat;
	}
	public int getCodeCat() {
		return codeCat;
	}
	@Override
	public String toString() {
		return "Categorie [codeCat=" + codeCat + ", designationCat=" + designationCat + "]";
	}
	public void setCodeCat(int codeCat) {
		this.codeCat = codeCat;
	}
	public String getDesignationCat() {
		return designationCat;
	}
	public void setDesignationCat(String designationCat) {
		this.designationCat = designationCat;
	}
}
