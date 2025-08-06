import java.util.ArrayList;
import java.util.List;

public class GitterModell {

    // Variablen
    private int spalten;
    private int zeilen;
    private ZellenZustand[][] gitter;


    // Konstruktor
    public GitterModell( int spalten, int zeilen)
    {
        this.spalten = spalten;
        this.zeilen = zeilen;

        this.gitter = new ZellenZustand[zeilen][spalten];
        for ( int i = 0; i < zeilen; i++)
        {
            for ( int j = 0; j < spalten; j++)
            {
                gitter[i][j] = ZellenZustand.OFFEN;
            }
        }
    }

    // Prüfung der 4 Nachbar Zellen Oben, Unten, Rechts, Links
    public List<GitterPosition> getNachbarn(GitterPosition pos) {
        List<GitterPosition> nachbarn = new ArrayList<>();

        int i = pos.getZeile();
        int j = pos.getSpalte();

        // Oben
        if (i > 0) {
            nachbarn.add(new GitterPosition(i - 1, j));
        }

        // Unten
        if (i + 1 < getZeilen()) {
            nachbarn.add(new GitterPosition(i + 1, j));
        }

        // Links
        if (j > 0) {
            nachbarn.add(new GitterPosition(i, j - 1));
        }

        // Rechts
        if (j + 1 < getSpalten()) {
            nachbarn.add(new GitterPosition(i, j + 1));
        }

        return nachbarn;
    }

    // Getter und Setter
    /**
     * Liefert die Anzahl der Zeilen im Gitter.
     */
    public int getZeilen() {
        return zeilen;
    }

    /**
     * Liefert die Anzahl der Spalten im Gitter.
     */
    public int getSpalten() {
        return spalten;
    }

}
