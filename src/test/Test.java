package test;

import java.io.IOException;
import java.net.URI;
import java.sql.ClientInfoStatus;
import java.util.Scanner;

import javax.ws.rs.core.UriBuilder;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.sun.jersey.api.client.config.*;

import entity.Produit;
import entity.ProduitResponse;
import metier.CategorieCrud;
import metier.ProduitCrud;

import com.sun.jersey.api.client.*;

public class Test {
public static void main(String args []) throws JsonParseException, JsonMappingException, IOException {
	Scanner scanner = new Scanner(System.in);
    ProduitCrud crud = new ProduitCrud();
    CategorieCrud crudCa=new CategorieCrud();
    while (true) {
        int choixGlobal = afficherMenuPrincipalGlobal(scanner);
        if (choixGlobal == 3) {
            System.out.println("Vous avez choisi de quitter. Au revoir !");
            break;
        } else if (choixGlobal == 1) {
        	while(true) {
            int choixProduit = afficherMenuPrincipalProduit(scanner, crud);
            if (choixProduit == 7) {
                break; 
            }
            traiterOption(choixProduit, scanner, crud);
        }} else if (choixGlobal == 2) {
        	while(true) {
            int choixCategorie = afficherMenuPrincipalCategorie(scanner, crudCa);
            if (choixCategorie == 6) {
                break;
            }
            traiterOptionCategorie(choixCategorie, scanner, crudCa);
        } }else {
            System.out.println("Veuillez entrer un nombre valide !");
        }
    }}


public static int afficherMenuPrincipalProduit(Scanner scanner, ProduitCrud crud) {
    System.out.println("Choisissez votre option :");
    System.out.println("1. Afficher les produits");
    System.out.println("2. Ajouter un produit");
    System.out.println("3. Mettre à jour un produit");
    System.out.println("4. Supprimer un produit");
    System.out.println("5. Rechercher un produit");
    System.out.println("6. Rechercher par Catégorie");
    System.out.println("7. Quitter");
    System.out.println("Entrez un nombre entre 1 et 7 pour continuer :");
    while (!scanner.hasNextInt()) {
        System.out.println("Veuillez entrer un nombre.");
        scanner.next();
    }
    int choixProduit = scanner.nextInt();
    if (choixProduit < 1 || choixProduit > 7) {
        System.out.println("Veuillez entrer un nombre valide !");
        return afficherMenuPrincipalProduit(scanner, crud);
    }
    return choixProduit;
}

public static int afficherMenuPrincipalCategorie(Scanner scanner, CategorieCrud crudCa) {
    System.out.println("Choisissez votre option :");
    System.out.println("1. Afficher les catégories");
    System.out.println("2. Ajouter une catégorie");
    System.out.println("3. Mettre à jour une catégorie");
    System.out.println("4. Supprimer une catégorie");
    System.out.println("5. Rechercher une catégorie");
    System.out.println("6. Quitter");
    System.out.println("Entrez un nombre entre 1 et 6 pour continuer :");

    while (!scanner.hasNextInt()) {
        System.out.println("Veuillez entrer un nombre.");
        scanner.next();
    }
    int choixCategorie = scanner.nextInt();
    if (choixCategorie < 1 || choixCategorie > 6) {
        System.out.println("Veuillez entrer un nombre valide !");
        return afficherMenuPrincipalCategorie(scanner, crudCa);
    }
    return choixCategorie;
}

public static int afficherMenuPrincipalGlobal(Scanner scanner) {
	System.out.println("Choisissez une option :");
    System.out.println("1. Produit");
    System.out.println("2. Catégorie");
    System.out.println("3. Quitter");
    System.out.println("Entrez un nombre entre 1 et 3 :");
    
    while (!scanner.hasNextInt()) {
        System.out.println("Veuillez entrer un nombre.");
        scanner.next();
    }
    int choixGlobal = scanner.nextInt();
    if (choixGlobal < 1 || choixGlobal > 3) {
        System.out.println("Veuillez entrer un nombre valide !");
        return afficherMenuPrincipalGlobal(scanner);
    }
    return choixGlobal;
}
	
public static void traiterOption(int choix, Scanner scanner, ProduitCrud crud) throws IOException {
    switch (choix) {
        case 1:
            crud.afficherAllProduit();
        	
            break;
        case 2:
        	System.out.println("\t\t\tAjouter un produit:");
            System.out.print("Donnez le code du produit: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Veuillez entrer un nombre.");
                scanner.next();
            }
            int code = scanner.nextInt();
            scanner.nextLine(); 
            System.out.print("Donnez la désignation du produit: ");
            String designation = scanner.nextLine();
            System.out.print("Donnez le prix du produit: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Veuillez entrer un nombre.");
                scanner.next();
            }
            Float prix = scanner.nextFloat();
            scanner.nextLine();

            System.out.print("Donnez la quantité du produit: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Veuillez entrer un nombre.");
                scanner.next();
            }
            int qte = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Donnez la catégorie du produit: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Veuillez entrer un nombre.");
                scanner.next();
            }
            int id_category = scanner.nextInt();
            crud.ajouterProduit(code, designation, prix, qte,id_category);
            break;
           
        case 3:
        	System.out.println("Vous avez choisi Mettre à jour un produit");
            System.out.print("Entrez le code du produit à mettre à jour : ");
            while (!scanner.hasNextInt()) {
                System.out.println("Veuillez entrer un nombre.");
                scanner.next();
            }
            code = scanner.nextInt();
            scanner.nextLine();
            boolean p = crud.existProduit(code);
          if(p==true) {
       	   System.out.print("Donnez la désignation du produit: ");
               designation = scanner.nextLine();

              System.out.print("Donnez le prix du produit: ");
              while (!scanner.hasNextInt()) {
                  System.out.println("Veuillez entrer un nombre.");
                  scanner.next();
              }
               prix = scanner.nextFloat();
              scanner.nextLine();

              System.out.print("Donnez la quantité du produit: ");
              while (!scanner.hasNextInt()) {
                  System.out.println("Veuillez entrer un nombre.");
                  scanner.next();
              }
               qte = scanner.nextInt();
              crud.updateProduit(code, designation, prix, qte);
          }else {
System.out.println("\t\t\t\t le produit n'existe pas");
          }
            break;
        case 4:
       	 System.out.println("Vous avez choisi Supprimer un produit");
       	    System.out.print("Entrez le code du produit à supprimer : ");
       	 while (!scanner.hasNextInt()) {
             System.out.println("Veuillez entrer un nombre.");
             scanner.next();
         }
       	    int codeASupprimer = scanner.nextInt();
       	    crud.supprimerProduit(codeASupprimer);
       	    break;
        case 5:
            System.out.println("Vous avez choisi Rechercher un produit");
            System.out.print("Entrez le code ou le prix ou la designation du produit à rechercher : ");
            if (scanner.hasNextInt()) {
            	//System.out.print(scanner.nextInt());
                int codeARechercher = scanner.nextInt();
                crud.chercherProduit(String.valueOf(codeARechercher));
            } else {
                String mc = scanner.next();
                crud.chercherProduit(mc);
            }
            break;
        case 6:
        	System.out.println("Vous avez choisi lister par category");
            System.out.print("Entrez le code du categorie : ");
            while (!scanner.hasNextInt()) {
                System.out.println("Veuillez entrer un nombre.");
                scanner.next();
            }
            int id = scanner.nextInt();
       	    crud.listerParCategory(id);
            
            break;
        default:
            break;
    }
	}
public static void traiterOptionCategorie(int choix, Scanner scanner, CategorieCrud crud) throws IOException {
    switch (choix) {
        case 1:
        	crud.afficherAllCategorie();
            break;
        case 2:
        	System.out.println("\t\t\tAjouter une catégorie:");
            System.out.print("Donnez le code du catégorie:");
            while (!scanner.hasNextInt()) {
                System.out.println("Veuillez entrer un nombre.");
                scanner.next();
            }
            int code = scanner.nextInt();
            scanner.nextLine(); 
            System.out.print("Donnez la désignation du produit: ");
            String designation = scanner.nextLine();
            crud.ajouterCategorie(code, designation);
            break;
        case 3:
        	System.out.println("Vous avez choisi Mettre à jour un catégorie");
            System.out.print("Entrez le code du catégorie à mettre à jour : ");
            while (!scanner.hasNextInt()) {
                System.out.println("Veuillez entrer un nombre.");
                scanner.next();
            }
            code = scanner.nextInt();
            scanner.nextLine();
            boolean p = crud.existCategorie(code);
          if(p==true) {
       	   System.out.print("Donnez la désignation du catégorie: ");
               designation = scanner.nextLine();

             
              crud.updateCategorie(code, designation);
          }else {
System.out.println("\t\t\t\t la categorie n'existe pas");
          }
            break;
        case 4:
       	 System.out.println("Vous avez choisi Supprimer une catégorie");
       	    System.out.print("Entrez le code du catégorie à supprimer : ");
       	 while (!scanner.hasNextInt()) {
             System.out.println("Veuillez entrer un nombre.");
             scanner.next();
         }
       	    int codeASupprimer = scanner.nextInt();
       	    crud.supprimerCategorie(codeASupprimer);
       	    break;
        case 5:
            System.out.println("Vous avez choisi Rechercher une catégorie");
            System.out.print("Entrez le code la designation du catégorie à rechercher : ");
            if (scanner.hasNextInt()) {
            	//System.out.print(scanner.nextInt());
                int codeARechercher = scanner.nextInt();
                crud.chercherCategorie(String.valueOf(codeARechercher));
            } else {
                String mc = scanner.next();
                crud.chercherCategorie(mc);
            }
            break;
        default:
            break;
    }
	}
}
