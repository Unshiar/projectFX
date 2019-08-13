package ch.sample;

import ch.sample.controller.LayoutRootController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;
    private Parent rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        primaryStage.setTitle("New journal");

        InitRootLayout();
    }

    public void InitRootLayout() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("resources/LayoutRoot.fxml"));
        rootLayout = loader.load();
        primaryStage.setScene(new Scene(rootLayout));
        primaryStage.show();

        LayoutRootController layoutRootController = loader.getController();
        layoutRootController.SetParentRoot(rootLayout);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
