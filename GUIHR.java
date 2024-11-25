import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class GUIHR {

    public Scene getScene() {
        Pane pane = new Pane();
        Label label = new Label("Welcome to the Human Resource Dashboard");
        label.setLayoutX(50);
        label.setLayoutY(50);
        pane.getChildren().add(label);

        return new Scene(pane, 350, 200);
    }
}


