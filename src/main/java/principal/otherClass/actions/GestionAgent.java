package principal.otherClass.actions;

import principal.otherClass.Agent;
import principal.otherClass.GestionnaireRotation;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestionAgent {
    private DayOfWeek date;
    private GestionnaireRotation admin;
    private Scanner sc= new Scanner(System.in);
    public GestionAgent(GestionnaireRotation admin){
        this.date = admin.getJourRotation();
        this.admin = admin;
        this.choix();
    }
    private void choix() {
        int ch = -1;
        while (ch != 0) {
            System.out.println("\n--- MENU - Gestion des agents ---");
            System.out.println("1.) Ajouter un agent");
            System.out.println("2.) Lister les agents");
            System.out.println("3.) Retirer un agent");
            System.out.println("0.) Retour à l'accueil");
            System.out.print("Faites un choix : ");

            while (!sc.hasNextInt()) {
                System.out.print(" Saisie invalide. Entrez un nombre : ");
                sc.next();  // vider la mauvaise saisie
            }
            ch = sc.nextInt();

            switch (ch) {
                case 1:
                    this.ajoutAgent();
                    break;
                case 2:
                    this.listAgent();
                    break;
                case 3:
                    System.out.print("Entrer l'email de l'agent à retirer : ");
                    String email = sc.next();
                    admin.retireAgent(email);
                    break;
                case 0:
                    System.out.println(" Retour à l'accueil...");
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
    }



    private void menu(){
        int ch ;
        do{
            System.out.print("______Tapez 0 pour retourner en arriere : ");
            ch = sc.nextInt();
        }while (ch !=0);

        switch (ch){
            case 0:
                this.choix();
                break;
        }
    }
    private void ajoutAgent() {
        System.out.print("Combien d’agents voulez-vous enregistrer ? : ");
        int nbAgent = sc.nextInt();

        for (int i = 0; i < nbAgent; i++) {
            System.out.print("Saisir le nom de l'agent " + (i + 1) + " : ");
            sc.nextLine();
            String nom = sc.nextLine();
            String email;
            do{
                System.out.print("Saisir l'email de l'agent " + (i + 1) + " : ");
                email = sc.next();
            }while (!admin.emailEstValide(email) || admin.emailExisteDeja(email));
            admin.ajouterAgent(nom,email);
        }

        System.out.println(nbAgent + (nbAgent > 1 ? " agents ont été ajoutés avec succès !" : " agent a été ajouté avec succès !"));
        admin.planifierRotationGlobale();
    }

    private void listAgent(){
        System.out.println("\nListe des agents :");
        System.out.println("____________________________________________________________________");
        for (Agent ag : admin.getListeAgent()) {
            System.out.println("- " + ag.getIdAgent() + " | " + ag.getNomAgent() + " | " + ag.getEmail());
            System.out.println("____________________________________________________________________");
        }
        this.menu();
    }

}
