package principal.otherClass;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class GestionnaireRotation {
    private List<Agent> listeAgent;
    private int positionActuelle;
    private DayOfWeek jourRotation;
    private Set<LocalDate> jourFerier;
    private List<Historique> historique;

    // Pour le constructeur de la classe


    public GestionnaireRotation(DayOfWeek jourRotation) {
        this.listeAgent = new ArrayList<>();
        this.positionActuelle = 0;
        this.jourRotation = jourRotation;
        this.jourFerier = new HashSet<>();
        this.historique = new ArrayList<>();
    }

    public List<Historique> getHistorique() {
        return historique;
    }

    public Historique myHistorique(String nom){
        for (Historique hist: historique
             ) {
            if (hist.getNomAgentPrevu().equals(nom)){
                return hist;
            }
        }
        return null;
    }
    public DayOfWeek getJourRotation() {
        return jourRotation;
    }

    public void setListeAgent(List<Agent> listeAgent) {
        this.listeAgent = listeAgent;
    }

    public void setJourRotation(DayOfWeek jourRotation) {
        this.jourRotation = jourRotation;
    }

    public boolean emailEstValide(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
    public boolean emailExisteDeja(String email) {
        for (Agent agent : listeAgent) {
            if (agent.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    //Methode pour Ajouter un agent
    private int compteurId =1;
    public void ajouterAgent(String nom, String email) {
        Agent agent = new Agent(compteurId++, nom, email);
        listeAgent.add(agent);
    }


    public List<Agent> getListeAgent() {
        return listeAgent;
    }

    public void setJourFerier(LocalDate jourF) {
        jourFerier.add(jourF);
    }

    //Methode pour retire un agent
    public void retireAgent (String emailAgent) {
        //Avec une fonction conditionnelle(Lambda) qui agit en supprimmant tout élément x qui vérifie la condition x -> ...
        boolean retirer = listeAgent.removeIf(agent -> agent.getEmail().equalsIgnoreCase(emailAgent));
        if (retirer) {
            System.out.println("L'agent a été retiré avec succès.");
        } else {
            System.out.println("Aucun agent trouvé avec l'email : " + emailAgent);
        }
    }
    //Avec les surcharges pour le retrait des agents :
    public void retireAgent(int idAgent){
        listeAgent.removeIf(agent -> agent.getIdAgent() == idAgent);
    }
    public void retireAgent(String cordAgent,int id){
        listeAgent.removeIf(agent -> (agent.getNomAgent() == cordAgent || agent.getEmail() == cordAgent));
    }

    //Pour la methode ajoutJourFerié :
    public  void ajoutJourFerie(LocalDate date){
        jourFerier.add(date);

        System.out.println("Le jour "+ date
                + " ajouter aux jours feriés avec succès!!");
    }

    //Methode pour afficher les jours feriers :
    public void afficherJoursFeries() {
        if (jourFerier.isEmpty()) {
            System.out.println("Aucun jour férié enregistré.");
            return;
        }

        System.out.println("\nListe des jours fériés enregistrés :");
        List<LocalDate> feriesTries = new ArrayList<>(jourFerier);
        Collections.sort(feriesTries);

        for (LocalDate date : feriesTries) {
            System.out.println("→ " + date);
        }
    }

    // Pour la methode prochaineDateRotation des agents :

    public LocalDate prochaineDateRotation(LocalDate reference){

        // La recuperation du jour de rotation suivant dans la semaine suivante avec une méthode utilitaire de Java :
        LocalDate date = reference.with(TemporalAdjusters.nextOrSame(jourRotation));
        //Verifier si le jour trouvé n'est pas dans la liste des jours fériés :
        while (jourFerier.contains(date)){
            // Augmentons la date de plus 1 jour genre si c'est Jeudi la date va prendre Vendredi
            date.plusDays(1);
            //On verifie si la date n'est pas dans le WEEKEND
            while (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY){
                // Augmentons la date de plus 1 jour genre si c'est Jeudi la date va prendre Vendredi
                date.plusDays(1);
            }
        }
        return date;
    }

    //Pour la methode trouverAgentDisponible :

    public Agent trouverAgentDisponible(LocalDate date){
        int tentative = 0;
        int pT = positionActuelle;
        while ( tentative < listeAgent.size()){
            //On recupere l'agent qui est a la position pT
            Agent agent = listeAgent.get(pT);
            if (agent.estDisponible(date)){
                return agent;
            }
            else {
                // Place a la rotation circulaire avec le modulo
                pT = (pT + 1)% listeAgent.size();
                tentative++;
            }
        }
        return null;
    }

    //Pour la methode planifieRotation :
    public void planifieRotation(LocalDate date){
        //On recupere l'agent qui est prevu pour le tour suivant en fonction de la positionActuelle de rotation:
        Agent agentPrevu = listeAgent.get(positionActuelle);
        // On recupere aussi l'agent disponible en fonction de la date et de la methode trouverAgentDisponible:
        Agent agentDispo = trouverAgentDisponible(date);

        //On verifie s'il n'y pas d'agent qui est disponible
        if (agentDispo == null){
            System.out.println("Aucun agent n'est disponible !!!");
            return;
        }
        //Pour la creation dans l'historique avec une verification a travers  des opérateurs ternaires
        String statut = agentDispo.equals(agentPrevu) ? "Effectué" : "Indisponible";
        String remplaçant = statut.equals("Indisponible") ? agentDispo.getNomAgent() : null;
        historique.add(new Historique(date,agentPrevu.getNomAgent(),statut,remplaçant));
        //Pour mettre a jour la position de rotation c'est a dire l'attribut positionActuelle avec une rotation circulaire:
        positionActuelle = (listeAgent.indexOf(agentDispo) + 1) % listeAgent.size();
    }

    //Pour faire la rotation globale :
    public void planifierRotationGlobale() {
        LocalDate dateRotation = prochaineDateRotation(LocalDate.now());

        for (int i = 0; i < listeAgent.size(); i++) {
            // L’agent qui devait normalement faire le tour
            Agent agentPrevu = listeAgent.get(positionActuelle);

            // Trouver un agent disponible pour cette date
            Agent agentDispo = trouverAgentDisponible(dateRotation);

            if (agentDispo == null) {
                System.out.println("Aucun agent n'est disponible pour la date " + dateRotation + " !");
                break;
            }

            // Déterminer le statut du tour
            String statut = agentDispo.equals(agentPrevu) ? "En cours" : "Indisponible";
            String remplacant = statut.equals("Indisponible") ? agentDispo.getNomAgent() : null;

            // Ajouter à l’historique
            historique.add(new Historique(dateRotation, agentPrevu.getNomAgent(), statut, remplacant));

            // Avancer dans la rotation circulaire
            positionActuelle = (listeAgent.indexOf(agentDispo) + 1) % listeAgent.size();

            // Passer à la prochaine date valide
            dateRotation = prochaineDateRotation(dateRotation.plusDays(1));
        }

        System.out.println("Assignation terminée avec succès !");
    }


    //Pour la methode afficherRotation :
    public void afficherRotationsAvenir(int nbSemaine){
        //On recupere la date actuelle du systeme
        LocalDate actuelDate = LocalDate.now();
        int position = positionActuelle;
        //On boucle sur le nombre de semaine données
        for (int i = 0 ; i < nbSemaine;i++){
            //On recupere la date de rotation de la semaine suivante
            LocalDate date = prochaineDateRotation(actuelDate.plusWeeks(1));

            //On trouve un agent en fonction de la date trouvée
            Agent agent = trouverAgentDisponible(date);

            System.out.println(date + " → " + agent.getNomAgent());
        }
        positionActuelle = position;
    }

    //Pour la methode afficherHistorique :
    public void afficherHistorique() {
        if (historique.isEmpty()) {
            System.out.println(" Aucun historique disponible pour le moment.");
            return;
        }

        System.out.println("\n HISTORIQUE DES ROTATIONS\n");
        //On utilise des spécificateurs de format pour organiser les colonnes a l'affichage
        // % debut du format; - aligner a gauche; 15 nombre de caractere; s le type de contenu
        System.out.printf("%-15s | %-20s | %-15s | %-20s\n", " Date", " Agent Prévu", " Statut", " Remplaçant");
        System.out.println("--------------------------------------------------------------------------------------");

        for (Historique h : historique) {
            String date = h.getDateRotation().toString();
            String agent = h.getNomAgentPrevu();
            String statut = h.getStatut();
            String remplaçant = (h.getNomRemplacant() != null) ? h.getNomRemplacant() : "-";

            System.out.printf("%-15s | %-20s | %-15s | %-20s\n", date, agent, statut, remplaçant);
        }
    }


    public void afficherDatesDeRotation() {
        if (historique.isEmpty()) {
            System.out.println("Aucune rotation planifiée pour le moment.");
            return;
        }

        // Créer une map pour regrouper les dates par agent
        Map<String, List<LocalDate>> rotationsParAgent = new HashMap<>();

        for (Historique h: historique) {
            String nom = h.getNomAgentPrevu();
            rotationsParAgent.putIfAbsent(nom, new ArrayList<>());
            rotationsParAgent.get(nom).add(h.getDateRotation());
        }

        // Affichage propre
        System.out.println("Dates de rotation par agent :\n");
        for (Map.Entry<String, List<LocalDate>> entry : rotationsParAgent.entrySet()) {
            System.out.println("***** " + entry.getKey() + " :");
            for (LocalDate date : entry.getValue()) {
                System.out.println("   ---- " + date);
            }
        }
    }


}
