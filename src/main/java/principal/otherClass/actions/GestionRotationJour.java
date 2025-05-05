package principal.otherClass.actions;

import principal.otherClass.GestionnaireRotation;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Scanner;

public class GestionRotationJour {
    private GestionnaireRotation admin;
    private DayOfWeek date;
    Scanner sc = new Scanner(System.in);
    public GestionRotationJour(GestionnaireRotation admin){
        this.date = admin.getJourRotation();
        this.admin = admin;
        this.choix();
    }

    private void choix(){
        int ch ;
        do{
            System.out.println("1.) Tapez 1 pour afficher les agents avec leurs jours de rotation");
            System.out.println("2.) Tapez 2 pour changer le jour de rotation");
            System.out.println("3.) Tapez 3 pour faire une nouvelle rotation");
            System.out.println("4.) Tapez 4 pour ajouter les jours Férier");
            System.out.println("5.) Tapez 0 pour retourner a l'accueil");
            System.out.print("Fait un choix : ");
            ch = sc.nextInt();
        }while (ch >3);
        switch (ch){
            case 0:
                System.out.println("Merci !!!");
                break;
            case 1:
                this.agentJourRotation();
                break;
            case 2:
                this.changerRotation();
                break;
            case 3:
                this.admin.planifierRotationGlobale();
                break;
            case 4:
                this.ajouterJoursFerier();
                break;
            default:
                System.out.println("Choix invalide . Fait un bon choix");
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
    private void agentJourRotation(){
        admin.afficherHistorique();
        this.menu();
    }
    private void ajouterJoursFerier(){
        System.out.print("Entrez la date du jour férié (aaaa-mm-jj) : ");
        String dateStr = sc.next();
        LocalDate dateFerie = LocalDate.parse(dateStr);
        admin.ajoutJourFerie(dateFerie);
        System.out.println("Jour férié ajouté.");
        this.menu();
    }
    private void changerRotation(){
        int jour ;
        do{
            System.out.println("1.) Tapez 1 pour Lundi");
            System.out.println("2.) Tapez 2 pour Mardi");
            System.out.println("3.) Tapez 3 pour Mercredi");
            System.out.println("4.) Tapez 4 pour Jeudi");
            System.out.println("5.) Tapez 5 pour Vendredi");
            System.out.print("Fait un choix : ");
            jour = sc.nextInt();
        }while (jour >5 || jour ==0);
        admin.setJourRotation(DayOfWeek.of(jour));
        System.out.println("Le jour de rotation changer avec succès le nouveau jour est "+DayOfWeek.of(jour));
        admin.planifierRotationGlobale();
        this.menu();

    }
}
