import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GUI extends Application {

    private Label successLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();

        primaryStage.setTitle("Login Application");
        primaryStage.setWidth(350);
        primaryStage.setHeight(200);

        Label userLabel = new Label("User ID:");
        userLabel.setLayoutX(10);
        userLabel.setLayoutY(20);
        pane.getChildren().add(userLabel);

        TextField userTextField = new TextField();
        userTextField.setLayoutX(100);
        userTextField.setLayoutY(20);
        userTextField.setPrefWidth(165);
        pane.getChildren().add(userTextField);

        Button loginButton = new Button("Login");
        loginButton.setLayoutX(10);
        loginButton.setLayoutY(80);
        pane.getChildren().add(loginButton);

        successLabel = new Label("");
        successLabel.setLayoutX(10);
        successLabel.setLayoutY(110);
        pane.getChildren().add(successLabel);

        loginButton.setOnAction(event -> {
            String userId = userTextField.getText();
            String regex = "^[EAH]\\d{3}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(userId);

            if (matcher.matches()) {
                if (userId.startsWith("E")) {
                    openGUIEmployee(primaryStage);
                } else if (userId.startsWith("A")) {
                    openGUIAdmin(primaryStage);
                } else if (userId.startsWith("H")) {
                    openGUIHR(primaryStage);
                }
            } else {
                successLabel.setText("Invalid User ID");
            }
        });

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openGUIEmployee(Stage primaryStage) {
        GUIEmployee guiEmployee = new GUIEmployee();
        Scene employeeScene = guiEmployee.getScene();
        primaryStage.setScene(employeeScene);
        primaryStage.show();
    }

    private void openGUIAdmin(Stage primaryStage) {
        GUIAdmin guiAdmin = new GUIAdmin();
        Scene adminScene = guiAdmin.getScene();
        primaryStage.setScene(adminScene);
        primaryStage.show();
    }

    private void openGUIHR(Stage primaryStage) {
        GUIHR guiHR = new GUIHR();
        Scene hrScene = guiHR.getScene();
        primaryStage.setScene(hrScene);
        primaryStage.show();
    }
}
