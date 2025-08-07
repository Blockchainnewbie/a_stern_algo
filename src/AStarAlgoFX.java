/**
 * Diese Klasse visualisiert den A*-Algorithmus mithilfe von JavaFX.
 * Sie erstellt ein Gitter, platziert Hindernisse, einen Start- und einen Zielpunkt
 * und lässt den A*-Algorithmus den kürzesten Weg finden und anzeigen.
 */
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class AStarAlgoFX extends Application {

    /**
     * Die Hauptmethode, die die JavaFX-Anwendung startet.
     * @param primaryStage Die primäre Stage für diese Anwendung.
     */
    @Override
    public void start(Stage primaryStage) {
        System.out.println("start() wurde aufgerufen");

        primaryStage.setTitle("A*-Visualisierer");

        // 1) Layout und Canvas anlegen
        // Ein Pane als Hauptcontainer und ein Canvas zum Zeichnen des Gitters.
        Pane mainLayout = new Pane();
        Canvas canvas = new Canvas(800, 600);
        mainLayout.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // 2) Gitter-Parameter berechnen
        // Die Größe der Zellen und die Anzahl der Zeilen und Spalten werden basierend auf der Canvas-Größe berechnet.
        int zellenGroesse = 40;
        int spalten       = (int)(canvas.getWidth()  / zellenGroesse);
        int zeilen        = (int)(canvas.getHeight() / zellenGroesse);

        // 3) Modell erzeugen und initialisieren
        // Das GitterModell speichert den Zustand jeder Zelle (offen, Hindernis, etc.).
        GitterModell modell = new GitterModell(zeilen, spalten);

        // 4) Feste Hindernisse setzen
        // Hier werden manuell verschiedene Hindernisse im Gitter platziert, um eine komplexe Umgebung zu schaffen.
        // Horizontale Wand
        modell.setZustand(new GitterPosition(2, 3), ZellenZustand.HINDERNIS);
        modell.setZustand(new GitterPosition(2, 4), ZellenZustand.HINDERNIS);
        modell.setZustand(new GitterPosition(2, 5), ZellenZustand.HINDERNIS);
        modell.setZustand(new GitterPosition(2, 6), ZellenZustand.HINDERNIS);
        
        // Vertikale Wand
        modell.setZustand(new GitterPosition(3, 6), ZellenZustand.HINDERNIS);
        modell.setZustand(new GitterPosition(4, 6), ZellenZustand.HINDERNIS);
        modell.setZustand(new GitterPosition(5, 6), ZellenZustand.HINDERNIS);
        
        // Zusätzliche Hindernisse
        modell.setZustand(new GitterPosition(4, 3), ZellenZustand.HINDERNIS);
        modell.setZustand(new GitterPosition(4, 4), ZellenZustand.HINDERNIS);
        modell.setZustand(new GitterPosition(5, 1), ZellenZustand.HINDERNIS);
        modell.setZustand(new GitterPosition(5, 2), ZellenZustand.HINDERNIS);
        
        // Engpass
        modell.setZustand(new GitterPosition(1, 7), ZellenZustand.HINDERNIS);
        modell.setZustand(new GitterPosition(2, 7), ZellenZustand.HINDERNIS);
        modell.setZustand(new GitterPosition(3, 7), ZellenZustand.HINDERNIS);
        modell.setZustand(new GitterPosition(4, 7), ZellenZustand.HINDERNIS);
        
        // Neues Labyrinth im rechten Bereich
        for (int i = 7; i <= 12; i++) {
            modell.setZustand(new GitterPosition(i, 10), ZellenZustand.HINDERNIS);
        }
        modell.setZustand(new GitterPosition(7, 11), ZellenZustand.HINDERNIS);
        modell.setZustand(new GitterPosition(7, 12), ZellenZustand.HINDERNIS);
        modell.setZustand(new GitterPosition(9, 11), ZellenZustand.HINDERNIS);
        modell.setZustand(new GitterPosition(9, 12), ZellenZustand.HINDERNIS);
        modell.setZustand(new GitterPosition(11, 11), ZellenZustand.HINDERNIS);
        modell.setZustand(new GitterPosition(11, 12), ZellenZustand.HINDERNIS);
        
        // Unterer Bereich
        for (int j = 3; j <= 15; j += 2) {
            modell.setZustand(new GitterPosition(10, j), ZellenZustand.HINDERNIS);
        }
        
        // Diagonales Muster
        for (int i = 0; i < 6; i++) {
            modell.setZustand(new GitterPosition(i + 8, i + 2), ZellenZustand.HINDERNIS);
        }
        
        // Zufällige Hindernisse im oberen rechten Quadranten
        modell.setZustand(new GitterPosition(2, 12), ZellenZustand.HINDERNIS);
        modell.setZustand(new GitterPosition(2, 14), ZellenZustand.HINDERNIS);
        modell.setZustand(new GitterPosition(3, 11), ZellenZustand.HINDERNIS);
        modell.setZustand(new GitterPosition(3, 15), ZellenZustand.HINDERNIS);
        modell.setZustand(new GitterPosition(4, 13), ZellenZustand.HINDERNIS);

        // 5) Start- und Zielpunkt festlegen
        // Die Start- und Zielpositionen werden definiert und im Modell markiert.
        GitterPosition startPos = new GitterPosition(1, 1);
        GitterPosition zielPos  = new GitterPosition(10, 15);
        modell.setZustand(startPos, ZellenZustand.START);
        modell.setZustand(zielPos,  ZellenZustand.ZIEL);

        // 6) A*-Weg in einem neuen Thread berechnen
        // Die Wegfindung wird in einem separaten Thread ausgeführt, um die UI nicht zu blockieren.
        new Thread(() -> {
            // Instanz des A*-Algorithmus mit dem Gittermodell und einer Heuristik (Manhattan-Distanz) erstellen.
            AStarAlgorithmus astar = new AStarAlgorithmus(modell, new ManhattanHeuristik());
            // Den Weg vom Start- zum Zielpunkt finden.
            List<GitterPosition> weg = astar.findeWeg(startPos, zielPos);

            // UI-Update im JavaFX Application Thread durchführen, da UI-Operationen nur hier sicher sind.
            javafx.application.Platform.runLater(() -> {
                if (weg != null && !weg.isEmpty()) {
                    // Wenn ein Weg gefunden wurde, werden die entsprechenden Zellen im Modell als WEG markiert.
                    for (GitterPosition p : weg) {
                        if (!p.equals(startPos) && !p.equals(zielPos)) {
                            modell.setZustand(p, ZellenZustand.WEG);
                        }
                    }
                } else {
                    // Optional: Gib eine Meldung aus, wenn kein Weg gefunden wurde.
                    System.out.println("Kein Weg zum Ziel gefunden.");
                }
                // Zeichne das Gitter neu, um den gefundenen Weg (oder das Fehlen davon) anzuzeigen.
                zeichneGitter(gc, modell, zellenGroesse);
            });
        }).start();

        // Initial das Gitter mit Start, Ziel und Hindernissen zeichnen.
        zeichneGitter(gc, modell, zellenGroesse);

        // 9) Scene setzen und Fenster zeigen
        // Die Szene wird erstellt, der Stage zugewiesen und das Fenster angezeigt.
        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Zeichnet das gesamte Gitter auf dem Canvas.
     * Die Farbe jeder Zelle wird durch ihren Zustand im GitterModell bestimmt.
     * @param gc Der GraphicsContext des Canvas, auf dem gezeichnet wird.
     * @param modell Das GitterModell, das die Zustände der Zellen enthält.
     * @param zellenGroesse Die Größe jeder Zelle in Pixel.
     */
    private void zeichneGitter(GraphicsContext gc, GitterModell modell, int zellenGroesse) {
        int zeilen = modell.getZeilen();
        int spalten = modell.getSpalten();
        double width = gc.getCanvas().getWidth();
        double height = gc.getCanvas().getHeight();

        // Löscht den vorherigen Inhalt des Canvas, um neu zu zeichnen.
        gc.clearRect(0, 0, width, height);

        // Iteriert durch jede Zelle des Gitters und zeichnet sie entsprechend ihrem Zustand.
        for (int i = 0; i < zeilen; i++) {
            for (int j = 0; j < spalten; j++) {
                ZellenZustand zustand = modell.getZustand(new GitterPosition(i, j));
                switch (zustand) {
                    case HINDERNIS:
                        gc.setFill(Color.DARKGRAY); // Hindernisse sind dunkelgrau.
                        break;
                    case START:
                        gc.setFill(Color.GREEN); // Der Startpunkt ist grün.
                        break;
                    case ZIEL:
                        gc.setFill(Color.RED); // Der Zielpunkt ist rot.
                        break;
                    case WEG:
                        gc.setFill(Color.BLUE); // Der gefundene Weg ist blau.
                        break;
                    default:
                        continue; // OFFEN-Zellen bleiben leer (Standardhintergrund).
                }
                // Zeichnet das Rechteck für die Zelle.
                gc.fillRect(j * zellenGroesse, i * zellenGroesse, zellenGroesse, zellenGroesse);
            }
        }

        // Zeichnet die Gitterlinien zur besseren visuellen Trennung der Zellen.
        gc.setStroke(Color.BLACK);
        for (int i = 0; i <= spalten; i++) {
            gc.strokeLine(i * zellenGroesse, 0, i * zellenGroesse, height);
        }
        for (int j = 0; j <= zeilen; j++) {
            gc.strokeLine(0, j * zellenGroesse, width, j * zellenGroesse);
        }
    }

    /**
     * Der Einstiegspunkt der Anwendung.
     * @param args Kommandozeilenargumente.
     */
    public static void main(String[] args) {
        // Startet die JavaFX-Anwendung.
        launch(args);
    }
}