/**
 * Repräsentiert eine Position im Gitter mit Zeilen- und Spaltenindex.
 */
public class GitterPosition {
    private final int zeile;
    private final int spalte;

    public GitterPosition(int zeile, int spalte) {
        this.zeile = zeile;
        this.spalte = spalte;
    }

    public int getZeile() {
        return zeile;
    }

    public int getSpalte() {
        return spalte;
    }
}
