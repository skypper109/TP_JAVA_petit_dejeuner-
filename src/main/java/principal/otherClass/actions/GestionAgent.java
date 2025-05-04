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
        int ch;
        do {
            System.out.println("\nMENU - Gestion des agents :");
            System.out.println("1.) Ajouter un agent");
            System.out.println("2.) Lister les agents");
            System.out.println("3.) Retirer un agent");
            System.out.println("0.) Retour à l'accueil");
            System.out.print("Faites un choix : ");
            ch = sc.nextInt();

            switch (ch) {
                case 0:
                    System.out.println("Retour à l'accueil...");
                    break;

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

                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        } while (ch != 0);
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
            System.out.print("Saisir l'email de l'agent " + (i + 1) + " : ");
            String email = sc.next();
            Agent agent = new Agent(i + 1, nom, email);  // ID = i+1
            admin.ajouterAgent(agent);
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
