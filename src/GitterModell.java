import java.util.ArrayList;
import java.util.List;

public class GitterModell {

    // Variablen
    private int spalten;
    private int zeilen;
    private ZellenZustand[][] gitter;


    // Konstruktor
    public GitterModell( int zeilen, int spalten)
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


    public ZellenZustand getZustand(GitterPosition pos) 
    {
        int zeile  = pos.getZeile();
        int spalte = pos.getSpalte();
        return gitter[zeile][spalte];
    }

    public void setZustand(GitterPosition pos, ZellenZustand zustand) 
    {
        int zeile  = pos.getZeile();
        int spalte = pos.getSpalte();
        gitter[zeile][spalte] = zustand;
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
