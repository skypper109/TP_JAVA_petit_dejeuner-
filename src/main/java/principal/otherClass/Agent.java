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
    public void voirTour(int id,GestionnaireRotation admin){

        for (Agent agent:admin.getListeAgent()) {

            if (agent.getIdAgent() == id){
               List<Historique> hist =  new ArrayList<>();
               hist.add(admin.myHistorique(agent.getNomAgent()));
                System.out.println("Votre Prochain Tour est prevu pour le : "+hist.get(0).getDateRotation());
            }
        }
    }

}
