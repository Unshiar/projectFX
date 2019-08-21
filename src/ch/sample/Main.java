package ch.sample;

import ch.sample.controller.LayoutRootController;
import ch.sample.utils.HibernateSessionFactoryUtil;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    private Stage primaryStage;
    private Parent rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("New journal");

        InitRootLayout();
    }

    public void InitRootLayout() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("resources/LayoutRoot.fxml"));
        rootLayout = loader.load();
        primaryStage.setScene(new Scene(rootLayout));

        //При закрытие программы закрываем фабрику сессий hibernate
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                HibernateSessionFactoryUtil.CloseSessionFactory();
            }
        });

        primaryStage.show();

        LayoutRootController layoutRootController = loader.getController();
        layoutRootController.SetParentRoot(rootLayout);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
