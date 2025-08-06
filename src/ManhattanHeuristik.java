public class ManhattanHeuristik implements Heuristik {

    @Override
    public double berechne(GitterPosition start, GitterPosition ziel) {
        int dx = Math.abs(start.getSpalte() - ziel.getSpalte());
        int dy = Math.abs(start.getZeile()   - ziel.getZeile());
        return dx + dy;
    }
}
