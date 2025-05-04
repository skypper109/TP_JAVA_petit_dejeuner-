package principal.otherClass;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OtherMore {

    public class GestionnaireRotation {
        private List<Agent> listeAgents;
        private int positionActuelle;
        private DayOfWeek jourDeRotation;
        private Set<LocalDate> joursFeries;
        private List<Historique> historique;

        public GestionnaireRotation(DayOfWeek jourDeRotation) {
            this.listeAgents = new ArrayList<>();
            this.positionActuelle = 0;
            this.jourDeRotation = jourDeRotation;
            this.joursFeries = new HashSet<>();
            this.historique = new ArrayList<>();
        }

        public void ajouterAgent(Agent agent) {
            listeAgents.add(agent);
        }

        public void retirerAgent(int id) {
            listeAgents.removeIf(agent -> agent.getIdAgent() == id);
        }

        public void ajouterJourFerie(LocalDate date) {
            joursFeries.add(date);
        }

        public void signalerIndisponibilite(int agentId, LocalDate date) {
            for (Agent agent : listeAgents) {
                if (agent.getIdAgent() == agentId) {
                    agent.ajouterIndisponiblilite(date);
                    break;
                }
            }
        }

        public LocalDate prochaineDateRotation(LocalDate reference) {
            LocalDate date = reference.with(TemporalAdjusters.nextOrSame(jourDeRotation));
            while (joursFeries.contains(date)) {
                date = date.plusDays(1);
                while (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    date = date.plusDays(1);
                }
            }
            return date;
        }

        public Agent trouverAgentDisponible(LocalDate date) {
            int tentatives = 0;
            int positionTemp = positionActuelle;
            while (tentatives < listeAgents.size()) {
                Agent agent = listeAgents.get(positionTemp);
                if (agent.estDisponible(date)) {
                    return agent;
                } else {
                    positionTemp = (positionTemp + 1) % listeAgents.size();
                    tentatives++;
                }
            }
            return null;
        }

        public void planifierRotation(LocalDate date) {
            Agent agentPrevu = listeAgents.get(positionActuelle);
            Agent agentDispo = trouverAgentDisponible(date);

            if (agentDispo == null) {
                System.out.println("Aucun agent disponible pour la date " + date);
                return;
            }

            String statut = agentDispo.equals(agentPrevu) ? "effectué" : "indisponible";
            String remplaçant = statut.equals("indisponible") ? agentDispo.getNomAgent() : null;

            historique.add(new Historique(date, agentPrevu.getNomAgent(), statut, remplaçant));

            positionActuelle = (listeAgents.indexOf(agentDispo) + 1) % listeAgents.size();
        }

        public void afficherRotationsAvenir(int nombre) {
            LocalDate base = LocalDate.now();
            int tempPosition = positionActuelle;
            for (int i = 0; i < nombre; i++) {
                LocalDate date = prochaineDateRotation(base.plusWeeks(i));
                Agent agent = trouverAgentDisponible(date);
                System.out.println(date + " → " + agent.getNomAgent());
            }
            positionActuelle = tempPosition;
        }

        public void afficherHistorique() {
            for (Historique record : historique) {
                System.out.println(record.getDateRotation() + " - " + record.getNomAgentPrevu()
                        + " (" + record.getStatut()
                        + (record.getNomRemplacant() != null ? ", remplacé par " + record.getNomRemplacant() : "")
                        + ")");
            }
        }
    }
}
