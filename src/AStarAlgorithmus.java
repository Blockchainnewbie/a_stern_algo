import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Implementiert den A*-Suchalgorithmus zur Wegfindung in einem Gitter.
 * Diese Klasse nutzt eine Heuristik, um den kürzesten Weg von einem Start- zu einem Zielpunkt zu finden.
 */
public class AStarAlgorithmus {
    private final GitterModell modell; // Das Gitter, in dem der Weg gefunden wird.
    private final Heuristik heuristik; // Die Heuristik zur Schätzung der Kosten bis zum Ziel.

    /**
     * Konstruktor für den A*-Algorithmus.
     * @param modell Das Gittermodell.
     * @param heuristik Die zu verwendende Heuristik.
     */
    public AStarAlgorithmus(GitterModell modell, Heuristik heuristik) {
        this.modell = modell;
        this.heuristik = heuristik;
    }

    /**
     * Findet den kürzesten Weg von einer Start- zu einer Zielposition.
     * @param start Die Startposition.
     * @param ziel Die Zielposition.
     * @return Eine Liste von GitterPositionen, die den Pfad repräsentieren, oder eine leere Liste, wenn kein Pfad gefunden wurde.
     */
    public List<GitterPosition> findeWeg(GitterPosition start, GitterPosition ziel) {
        // Die Menge der bereits besuchten und ausgewerteten Knoten.
        Set<GitterPosition> geschlosseneMenge = new HashSet<>();
        
        // Eine Prioritätswarteschlange für die zu besuchenden Knoten, sortiert nach den f-Kosten (g + h).
        PriorityQueue<KnotenEintrag> offeneListe = new PriorityQueue<>(
            (e1, e2) -> Double.compare(e1.getGKosten() + e1.getHKosten(),
                                      e2.getGKosten() + e2.getHKosten())
        );

        // Eine Map, die jeder Position den besten bisher gefundenen KnotenEintrag zuordnet.
        Map<GitterPosition, KnotenEintrag> positionZuEintrag = new HashMap<>();

        // Initialisierung des Startknotens.
        double startHeuristik = heuristik.berechne(start, ziel);
        KnotenEintrag startEintrag = new KnotenEintrag(start, 0, startHeuristik, null);
        offeneListe.add(startEintrag);
        positionZuEintrag.put(start, startEintrag);

        // Hauptschleife des Algorithmus.
        while (!offeneListe.isEmpty()) {
            // Wähle den Knoten mit den niedrigsten f-Kosten.
            KnotenEintrag aktuellerEintrag = offeneListe.poll();
            GitterPosition aktuellePosition = aktuellerEintrag.getPosition();

            // Wenn das Ziel erreicht ist, den Pfad rekonstruieren und zurückgeben.
            if (aktuellePosition.equals(ziel)) {
                return bauePfad(aktuellerEintrag);
            }

            // Füge die aktuelle Position zur geschlossenen Menge hinzu.
            geschlosseneMenge.add(aktuellePosition);

            // Untersuche alle Nachbarn der aktuellen Position.
            for (GitterPosition nachbar : modell.getNachbarn(aktuellePosition)) {
                // Überspringe Hindernisse und bereits besuchte Knoten.
                if (geschlosseneMenge.contains(nachbar)
                    || modell.getZustand(nachbar) == ZellenZustand.HINDERNIS) {
                    continue;
                }

                // Berechne die neuen g-Kosten für den Nachbarn.
                double gNeu = aktuellerEintrag.getGKosten() + 1; // Kantenkosten sind 1.
                KnotenEintrag vorhandenerEintrag = positionZuEintrag.get(nachbar);

                // Wenn der neue Weg zum Nachbarn kürzer ist oder der Nachbar noch nicht besucht wurde.
                if (vorhandenerEintrag == null || gNeu < vorhandenerEintrag.getGKosten()) {
                    double hNeu = heuristik.berechne(nachbar, ziel);
                    KnotenEintrag neuerEintrag = new KnotenEintrag(nachbar, gNeu, hNeu, aktuellerEintrag);

                    // Aktualisiere den Eintrag für den Nachbarn in der offenen Liste und der Map.
                    positionZuEintrag.put(nachbar, neuerEintrag);
                    offeneListe.remove(vorhandenerEintrag); // Entferne den alten, schlechteren Eintrag.
                    offeneListe.add(neuerEintrag);
                }
            }
        }

        // Wenn die offene Liste leer ist und das Ziel nicht erreicht wurde, gibt es keinen Pfad.
        return Collections.emptyList();
    }

    /**
     * Rekonstruiert den Pfad vom Zielknoten zurück zum Startknoten.
     * @param zielEintrag Der KnotenEintrag des Ziels.
     * @return Eine Liste von GitterPositionen, die den Pfad vom Start zum Ziel darstellt.
     */
    private List<GitterPosition> bauePfad(KnotenEintrag zielEintrag) {
        List<GitterPosition> pfad = new ArrayList<>();
        KnotenEintrag cursor = zielEintrag;
        while (cursor != null) {
            pfad.add(cursor.getPosition());
            cursor = cursor.getVorgaenger();
        }
        Collections.reverse(pfad); // Umkehren, um den Pfad vom Start zum Ziel zu erhalten.
        return pfad;
    }

    /**
     * Eine private Hilfsklasse, die einen Knoten im Suchgraphen darstellt.
     * Sie speichert die Position, die g- und h-Kosten sowie den Vorgängerknoten.
     */
    private static class KnotenEintrag {
        private final GitterPosition position; // Die Position des Knotens im Gitter.
        private final double gKosten; // Die Kosten vom Startknoten bis zu diesem Knoten.
        private final double hKosten; // Die geschätzten Kosten von diesem Knoten bis zum Ziel (Heuristik).
        private final KnotenEintrag vorgaenger; // Der Vorgängerknoten auf dem bisher besten Pfad.

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
