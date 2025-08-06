// Diese Schnittstelle definiert genau eine Methode, die uns eine „optimistische“ 
// Schätzung der Entfernung von einer Position zur anderen liefert

public interface Heuristik {
    double berechne(GitterPosition start, GitterPosition ziel);
}

