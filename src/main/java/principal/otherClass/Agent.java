package principal.otherClass;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Agent {
    private int idAgent;
    private String nomAgent;
    private String email;
    private List<LocalDate> dateIndisponible;

    // POur le constructeur de la class otherClass.Agent :

    public Agent(int idAgent, String nomAgent, String email){
        this.idAgent = idAgent;
        this.nomAgent = nomAgent;
        this.email = email;
        this.dateIndisponible = new ArrayList<>();
    }

    // Maintenant place aux getter et setter des methodes privées:


    public int getIdAgent() {
        return idAgent;
    }

    public void setIdAgent(int idAgent) {
        this.idAgent = idAgent;
    }

    public String getNomAgent() {
        return nomAgent;
    }

    public void setNomAgent(String nomAgent) {
        this.nomAgent = nomAgent;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<LocalDate> getDateIndisponible() {
        return dateIndisponible;
    }

    public void setDateIndisponible(List<LocalDate> dateIndisponible) {
        this.dateIndisponible = dateIndisponible;
    }

    //    Pour les methodes maintenant
    public void ajouterIndisponiblilite(LocalDate date){
        dateIndisponible.add(date);
    }
     public boolean estDisponible(LocalDate date){
        return !dateIndisponible.contains(date);
     }


    //Pour methode pour signalerIndisponibilite des agents :
    public void signalerIndisponibilite(int idAgent, LocalDate date,GestionnaireRotation admin){
        for (Agent agent: admin.getListeAgent()){
            if (agent.getIdAgent() == idAgent){
                agent.ajouterIndisponiblilite(date);
                System.out.println("Indisponibilité ajoutée pour le " + date);
                admin.planifieRotation(date);
                break;
            }
        }
    }
    public void voirTour(GestionnaireRotation admin) {
        List<Historique> historique = admin.getHistorique();
        boolean trouve = false;

        System.out.println("\n📅 Vos dates de rotation prévues :");
        for (Historique h : historique) {
            if (h.getNomAgentPrevu().equals(this.nomAgent)) {
                System.out.println("→ " + h.getDateRotation() + " (" + h.getStatut() + ")");
                trouve = true;
            }
        }

        if (!trouve) {
            System.out.println("Aucune rotation prévue pour vous actuellement.");
        }
    }
    public void rappelSiProcheTour(GestionnaireRotation admin) {
        LocalDate dansDeuxJours = LocalDate.now().plusDays(2);

        for (Historique h : admin.getHistorique()) {
            if (h.getNomAgentPrevu().equals(this.nomAgent) && h.getDateRotation().equals(dansDeuxJours)) {
                System.out.println("\nRappel : Vous êtes prévu pour le petit-déjeuner dans 2 jours (" + dansDeuxJours + ").");
                return;
            }
        }

        // Aucun rappel
        System.out.println("Aucun rappel pour vous aujourd’hui.");
    }


}
