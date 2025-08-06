import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class AStarAlgoFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        System.out.println("start() wurde aufgerufen");

        primaryStage.setTitle("A*-Visualisierer");

        // 1) Layout und Canvas anlegen
        Pane mainLayout = new Pane();
        Canvas canvas = new Canvas(800, 600);
        mainLayout.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // 2) Gitter-Parameter berechnen
        int zellenGroesse = 40;
        int spalten       = (int)(canvas.getWidth()  / zellenGroesse);
        int zeilen        = (int)(canvas.getHeight() / zellenGroesse);

        // 3) Modell erzeugen und initialisieren
        GitterModell modell = new GitterModell(zeilen, spalten);

        // 4) Feste Hindernisse setzen
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
        GitterPosition startPos = new GitterPosition(1, 1);
        GitterPosition zielPos  = new GitterPosition(10, 15);
        modell.setZustand(startPos, ZellenZustand.START);
        modell.setZustand(zielPos,  ZellenZustand.ZIEL);

        // 6) A*-Weg in einem neuen Thread berechnen
        new Thread(() -> {
            AStarAlgorithmus astar = new AStarAlgorithmus(modell, new ManhattanHeuristik());
            List<GitterPosition> weg = astar.findeWeg(startPos, zielPos);

            // UI-Update im JavaFX Application Thread durchführen
            javafx.application.Platform.runLater(() -> {
                if (weg != null && !weg.isEmpty()) {
                    for (GitterPosition p : weg) {
                        if (!p.equals(startPos) && !p.equals(zielPos)) {
                            modell.setZustand(p, ZellenZustand.WEG);
                        }
                    }
                } else {
                    // Optional: Gib eine Meldung aus, wenn kein Weg gefunden wurde
                    System.out.println("Kein Weg zum Ziel gefunden.");
                }
                // Zeichne das Gitter neu, um den Weg (oder das Fehlen davon) anzuzeigen
                zeichneGitter(gc, modell, zellenGroesse);
            });
        }).start();

        // Initial das leere Gitter zeichnen
        zeichneGitter(gc, modell, zellenGroesse);

        // 9) Scene setzen und Fenster zeigen
        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Zeichnet das Gitter und füllt die Zellen entsprechend dem Modellzustand.
     */
    private void zeichneGitter(GraphicsContext gc, GitterModell modell, int zellenGroesse) {
        int zeilen = modell.getZeilen();
        int spalten = modell.getSpalten();
        double width = gc.getCanvas().getWidth();
        double height = gc.getCanvas().getHeight();

        // Hintergrund löschen
        gc.clearRect(0, 0, width, height);

        // Zellen füllen
        for (int i = 0; i < zeilen; i++) {
            for (int j = 0; j < spalten; j++) {
                ZellenZustand zustand = modell.getZustand(new GitterPosition(i, j));
                switch (zustand) {
                    case HINDERNIS:
                        gc.setFill(Color.DARKGRAY);
                        break;
                    case START:
                        gc.setFill(Color.GREEN);
                        break;
                    case ZIEL:
                        gc.setFill(Color.RED);
                        break;
                    case WEG:
                        gc.setFill(Color.BLUE);
                        break;
                    default:
                        continue; // OFFEN: nichts füllen
                }
                gc.fillRect(j * zellenGroesse, i * zellenGroesse, zellenGroesse, zellenGroesse);
            }
        }

        // Gitterlinien zeichnen
        gc.setStroke(Color.BLACK);
        for (int i = 0; i <= spalten; i++) {
            gc.strokeLine(i * zellenGroesse, 0, i * zellenGroesse, height);
        }
        for (int j = 0; j <= zeilen; j++) {
            gc.strokeLine(0, j * zellenGroesse, width, j * zellenGroesse);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
