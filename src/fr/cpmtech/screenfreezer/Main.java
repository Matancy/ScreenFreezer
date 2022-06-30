package fr.cpmtech.screenfreezer;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public void start(Stage primaryStage){
        primaryStage = new App();
        primaryStage.show();
    }
    public static void main(String[] args) {
        Application.launch();
    }
}
