package principal;

import principal.otherClass.Agent;
import principal.otherClass.GestionnaireRotation;
import principal.otherClass.actions.GestionAgent;
import principal.otherClass.actions.GestionRotationJour;

import java.lang.reflect.Array;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean b = true;
        Scanner sc = new Scanner(System.in);

        String adminNom = "Admin";
        int adminPwd = 12345678;

        System.out.print("Veuillez Entrer USERNAME : ");
        String username = sc.next();
        System.out.print("Tapez le mot de passe : ");
        int password = sc.nextInt();

        GestionnaireRotation admin = new GestionnaireRotation(DayOfWeek.FRIDAY);
        String quitter = "n";
        while (!quitter.equals("Q")){

            boolean c = true;
            while (b) {
                if (username.equals(adminNom) && password == adminPwd) {
                    System.out.println("\nBienvenue l'Admin du système GesCo !!!");
                    while (c) {
                        System.out.println("1.) Tapez 1 pour la gestion des agents");
                        System.out.println("2.) Tapez 2 pour la gestion de rotation et de jour");
                        System.out.println("7.) Tapez 7 pour afficher les prochaines rotations");
                        System.out.println("0.) Tapez 0 pour quitter");
                        System.out.print("\nFaites un choix : ");
                        int choix = sc.nextInt();

                        switch (choix) {
                            case 1:
                                new GestionAgent(admin);
                                break;

                            case 2:
                                new GestionRotationJour(admin);
                                break;
                            case 7:
                                System.out.print("Combien de prochaines rotations voulez-vous afficher ? : ");
                                int nombre = sc.nextInt();
                                admin.afficherRotationsAvenir(nombre);
                                break;

                            case 0:
                                System.out.println("Déconnexion...");
                                c = false;
                                break;

                            default:
                                System.out.println("Choix invalide, veuillez réessayer.");
                        }
                    }
                    String choix = "Oui";
                    System.out.println("Voulez vous vraiment quitter ? (Oui/Non).");
                    choix = sc.next();
                    if (choix.equals("Oui") || choix.equals("O") || choix.equals("o") || choix.equals("oui")){
                        b=false;
                    }else {
                        c=true;
                    }
                } else {   // Tentative de connexion en tant qu'agent

                    List <Agent> listeAgent = admin.getListeAgent();
                    List<Agent> connected = new ArrayList<>();

                    for (Agent agent:listeAgent){
                        if (agent.getEmail().equals(username) && password == 1234){
                            connected.clear();
                            connected.add(agent);
                            Agent agentConnected = new Agent(agent.getIdAgent(),agent.getNomAgent(), agent.getEmail());

                            System.out.println("\nBienvenue Agent " + connected.get(0).getNomAgent() + " !");
                            agentConnected.voirTour(agentConnected.getIdAgent(),admin);
                            break;
                        }
                    }
                    if (!connected.isEmpty()) {

                        int ch;
                        do {

                            System.out.println("1.) Signaler une indisponibilité");
                            System.out.println("2.) Voir Historiques");
                            System.out.println("0.) Se deconnecter");
                            System.out.print("\nFaites un choix : ");
                            ch = sc.nextInt();

                            switch (ch) {
                                case 0:
                                    System.out.println("Retour à l'accueil...");
                                    break;

                                case 1:
                                    System.out.print("Entrez la date d'indisponibilité (aaaa-mm-jj) : ");
                                    String dateStr = sc.next();
                                    LocalDate indispo = LocalDate.parse(dateStr);
                                    admin.signalerIndisponibilite(connected.get(0).getIdAgent(), indispo);
                                    System.out.println("Indisponibilité enregistrée.");
                                    break;

                                case 2:
                                    admin.afficherDatesDeRotation();
                                    break;
                                default:
                                    System.out.println("Choix invalide. Veuillez réessayer.");
                            }
                        } while (ch != 0);
                    } else {
                        System.out.println("\nConnectez vous !!!");
                    }
                    System.out.print("Veuillez Entrer USERNAME : ");
                    username = sc.next();
                    System.out.print("Tapez le mot de passe : ");
                    password = sc.nextInt();
                }
            }

            //Pour Quitter le programme meme :
            System.out.print("Voulez vous quitter l'application ? taper (Q)");
            quitter = sc.next();
            b=true;
            username="";
        }
        System.out.println("Les Agents enregistrés sont : ");
        for (Agent ag : admin.getListeAgent()) {
            System.out.println("- " + ag.getIdAgent() + " | " + ag.getNomAgent() + " | " + ag.getEmail());
        }


    }
}