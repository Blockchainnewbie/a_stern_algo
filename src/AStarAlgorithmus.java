import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Einfach lesbare Implementierung des A*-Algorithmus.
 */
public class AStarAlgorithmus {
    private final GitterModell modell;
    private final Heuristik heuristik;

    public AStarAlgorithmus(GitterModell modell, Heuristik heuristik) {
        this.modell = modell;
        this.heuristik = heuristik;
    }

    /**
     * Findet den kürzesten Weg von start bis ziel.
     * @return Liste der Positionen im Weg oder leer, wenn kein Weg gefunden.
     */
    public List<GitterPosition> findeWeg(GitterPosition start, GitterPosition ziel) {
        // Menge der bereits untersuchten Knoten
        Set<GitterPosition> geschlosseneMenge = new HashSet<>();
        
        // Prioritätswarteschlange sortiert nach f = g + h
        PriorityQueue<KnotenEintrag> offeneListe = new PriorityQueue<>(
            (e1, e2) -> Double.compare(e1.getGKosten() + e1.getHKosten(),
                                      e2.getGKosten() + e2.getHKosten())
        );

        // Map speichert für jede Position den besten bekannten Eintrag
        Map<GitterPosition, KnotenEintrag> positionZuEintrag = new HashMap<>();

        // Startknoten anlegen (g=0, h=heuristik(start,ziel))
        double startHeuristik = heuristik.berechne(start, ziel);
        KnotenEintrag startEintrag = new KnotenEintrag(start, 0, startHeuristik, null);
        offeneListe.add(startEintrag);
        positionZuEintrag.put(start, startEintrag);

        while (!offeneListe.isEmpty()) {
            KnotenEintrag aktuellerEintrag = offeneListe.poll();
            GitterPosition aktuellePosition = aktuellerEintrag.getPosition();

            // Ziel erreicht?
            if (aktuellePosition.equals(ziel)) {
                return bauePfad(aktuellerEintrag);
            }

            geschlosseneMenge.add(aktuellePosition);

            // Durchlaufe alle Nachbarn
            for (GitterPosition nachbar : modell.getNachbarn(aktuellePosition)) {
                // Hindernisse und schon bearbeitete Positionen überspringen
                if (geschlosseneMenge.contains(nachbar)
                    || modell.getZustand(nachbar) == ZellenZustand.HINDERNIS) {
                    continue;
                }

                // Kosten bis Nachbar (alle Kanten haben Kosten 1)
                double gNeu = aktuellerEintrag.getGKosten() + 1;
                KnotenEintrag vorhandenerEintrag = positionZuEintrag.get(nachbar);

                // Wenn neuer Weg besser ist oder Position noch nicht besucht
                if (vorhandenerEintrag == null || gNeu < vorhandenerEintrag.getGKosten()) {
                    double hNeu = heuristik.berechne(nachbar, ziel);
                    KnotenEintrag neuerEintrag = new KnotenEintrag(nachbar, gNeu, hNeu, aktuellerEintrag);

                    // Update Positionseintrag
                    positionZuEintrag.put(nachbar, neuerEintrag);
                    offeneListe.remove(vorhandenerEintrag);
                    offeneListe.add(neuerEintrag);
                }
            }
        }

        // Kein Weg gefunden
        return Collections.emptyList();
    }

    /**
     * Rekonstruiert den Weg, indem es Vorgänger rückwärts verfolgt.
     */
    private List<GitterPosition> bauePfad(KnotenEintrag zielEintrag) {
        List<GitterPosition> pfad = new ArrayList<>();
        KnotenEintrag cursor = zielEintrag;
        while (cursor != null) {
            pfad.add(cursor.getPosition());
            cursor = cursor.getVorgaenger();
        }
        Collections.reverse(pfad);
        return pfad;
    }

    /**
     * Hilfsklasse für Einträge in der A*-Suchliste.
     */
    private static class KnotenEintrag {
        private final GitterPosition position;
        private final double gKosten;
        private final double hKosten;
        private final KnotenEintrag vorgaenger;

        KnotenEintrag(GitterPosition position,
                     double gKosten,
                     double hKosten,
                     KnotenEintrag vorgaenger) {
            this.position = position;
            this.gKosten = gKosten;
            this.hKosten = hKosten;
            this.vorgaenger = vorgaenger;
        }

        public GitterPosition getPosition() {
            return position;
        }

        public double getGKosten() {
            return gKosten;
        }

        public double getHKosten() {
            return hKosten;
        }

        public KnotenEintrag getVorgaenger() {
            return vorgaenger;
        }
    }
}
