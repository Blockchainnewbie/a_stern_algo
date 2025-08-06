import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
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
        
        // Senkrechte Linien Zeichnen
        for (int i = 0; i <= spalten; i++) {

            gc.strokeLine(i * zellenGroeße, 0, i * zellenGroeße, canvas.getHeight());
        }
        // waagrechte Linien Zeichnen
        for (int j = 0; j <= zeilen; j++) {

            gc.strokeLine(0, j * zellenGroeße, canvas.getWidth(), j * zellenGroeße);
        }

        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }

}


