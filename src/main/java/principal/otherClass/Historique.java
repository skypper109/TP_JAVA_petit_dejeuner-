package principal.otherClass;

import java.time.LocalDate;

public class Historique {
    private LocalDate dateRotation;
    private String nomAgentPrevu;
    private String statut;
    private String nomRemplacant;

    // Pour le constructeur
    public Historique(LocalDate dateRotation, String nomAgentPrevu, String statut, String nomRemplacant) {
        this.dateRotation = dateRotation;
        this.nomAgentPrevu = nomAgentPrevu;
        this.statut = statut;
        this.nomRemplacant = nomRemplacant;
    }

    // Pour les getter


    public LocalDate getDateRotation() {
        return dateRotation;
    }

    public String getNomAgentPrevu() {
        return nomAgentPrevu;
    }

    public String getStatut() {
        return statut;
    }

    public String getNomRemplacant() {
        return nomRemplacant;
    }
}
