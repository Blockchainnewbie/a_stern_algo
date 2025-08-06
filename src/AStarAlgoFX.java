import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;


public class AStarAlgoFX extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {


        primaryStage.setTitle("The A-Star-Visualisierer");

        Pane mainLayout = new Pane();
        // Das leere Fenster wird erstellt
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        // Es wird ein Canvas über das Fenster gelegt
        Canvas canvas = new Canvas(800, 600);
        mainLayout.getChildren().add(canvas);
        // Der Stift zum malen auf das Canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();
        // Zellen Größe anlegen
        int zellenGroeße = 40;
        // Anzahl der Zeilen und Spalten bestimmen
        int spalten = (int)(canvas.getWidth() / zellenGroeße);
        int zeilen = (int)(canvas.getHeight() / zellenGroeße);
        // Modell anlegen
        GitterModell modell = new GitterModell(zeilen, spalten);

        // Beispiel-Hindernisse
        modell.setZustand(new GitterPosition(2, 3), ZellenZustand.HINDERNIS);
        modell.setZustand(new GitterPosition(2, 4), ZellenZustand.HINDERNIS);
        modell.setZustand(new GitterPosition(2, 5), ZellenZustand.HINDERNIS);

        // Start- und Zielzelle
        modell.setZustand(new GitterPosition(1, 1), ZellenZustand.START);
        modell.setZustand(new GitterPosition(10, 15), ZellenZustand.ZIEL);

        // Senkrechte Linien Zeichnen
        for (int i = 0; i <= spalten; i++) {

            gc.strokeLine(i * zellenGroeße, 0, i * zellenGroeße, canvas.getHeight());
        }
        // waagrechte Linien Zeichnen
        for (int j = 0; j <= zeilen; j++) {

            gc.strokeLine(0, j * zellenGroeße, canvas.getWidth(), j * zellenGroeße);
        }


        // Zellen je nach Zustand füllen
        for (int i = 0; i < zeilen; i++) {
            for (int j = 0; j < spalten; j++) {
                GitterPosition pos = new GitterPosition(i, j);
                ZellenZustand zustand = modell.getZustand(pos);

                switch (zustand) {
                    case HINDERNIS:
                        gc.setFill(Color.DARKGRAY);
                        gc.fillRect(j * zellenGroeße, i * zellenGroeße, zellenGroeße, zellenGroeße);
                        break;
                    case START:
                        gc.setFill(Color.GREEN);
                        gc.fillRect(j * zellenGroeße, i * zellenGroeße, zellenGroeße, zellenGroeße);
                        break;
                    case ZIEL:
                        gc.setFill(Color.RED);
                        gc.fillRect(j * zellenGroeße, i * zellenGroeße, zellenGroeße, zellenGroeße);
                        break;
                    case WEG:
                        gc.setFill(Color.BLUE);
                        gc.fillRect(j * zellenGroeße, i * zellenGroeße, zellenGroeße, zellenGroeße);
                        break;
                    default:
                        // OFFEN: nichts füllen
                        break;
                }
            }
}
        primaryStage.show();
    }

    
    public static void main(String[] args)
    {
        launch(args);
    }

}


