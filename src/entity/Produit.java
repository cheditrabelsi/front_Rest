package entity;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;

public class Produit {
	@JsonProperty("code")
	private int code;
	@JsonProperty("designation")
	private String designation;
	@JsonProperty("qte")
	private int qte;
	@JsonProperty("prix")
	private float prix;
	@JsonProperty("id_category")
	private int id_category;
	public Produit() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Produit(int code, String designation, int qte, float prix, int id_category) {
		super();
		this.code = code;
		this.designation = designation;
		this.qte = qte;
		this.prix = prix;
		this.id_category = id_category;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public int getQte() {
		return qte;
	}
	public void setQte(int qte) {
		this.qte = qte;
	}
	public float getPrix() {
		return prix;
	}
	public void setPrix(float prix) {
		this.prix = prix;
	}
	public int getId_category() {
		return id_category;
	}
	public void setId_category(int id_category) {
		this.id_category = id_category;
	}
	@Override
	public String toString() {
		return "Produit [code=" + code + ", designation=" + designation + ", qte=" + qte + ", prix=" + prix
				+ ", id_category=" + id_category + "]";
	}
	

}
