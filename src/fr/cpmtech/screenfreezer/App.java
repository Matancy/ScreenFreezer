package fr.cpmtech.screenfreezer;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class App extends Stage {

    private VBox root = new VBox();
    private HBox zoneText = new HBox();
    private HBox zoneBtn = new HBox();
    private HBox zoneActualisation = new HBox();

    public App() {
        Scene scene = new Scene(content());
        root.setStyle("-fx-background-color: #34495e;");
        this.setMinWidth(350);
        this.setMinHeight(200);
        this.setScene(scene);
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setTitle("CPM Tech pour 7030.fr®");
    }

    Parent content() {
        try {
            // Define some vars for screen
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment(); // Contains the graphical environment of the local machine
            GraphicsDevice[] screens = ge.getScreenDevices(); // List of all the screens

            // Update style of zoneBtn
            zoneBtn.setSpacing(10);
            zoneBtn.setAlignment(Pos.CENTER);

            // Add title at the top of the window
            Label title = new Label("Ecrans disponibles");
            zoneText.setAlignment(Pos.CENTER);
            title.setPadding(new Insets(30, 0, 30, 0));
            title.setFont(Font.font("Nunito", FontWeight.BOLD, FontPosture.REGULAR, 20));
            title.setTextFill(Paint.valueOf("#f5f5f5"));
            zoneText.getChildren().add(title);

            // Add actualisation button at the bottom of the window
            Button actualisation = new Button("Actualiser");
            actualisation.setOnAction(event -> {
                new App().show();
                this.close();
            });
            zoneActualisation.getChildren().add(actualisation);
            zoneActualisation.setAlignment(Pos.CENTER);
            zoneActualisation.setPadding(new Insets(30, 0, 30, 0));

            // Create robot to get all the screens
            Robot r = new Robot();

            // Create list with the min X and the width of a screen
            ArrayList<Integer[]> listX = new ArrayList<Integer[]>();

            // Create list with the min X
            ArrayList<Integer> listXbis = new ArrayList<Integer>();

            // Get each min x and screen width
            for (GraphicsDevice screen : screens) {
                Rectangle tmp = screen.getDefaultConfiguration().getBounds();
                listX.add(new Integer[]{(int) tmp.getMinX(), tmp.width});
                listXbis.add((int) tmp.getMinX());
            }

            // Sort list of min x and screen width
            Collections.sort(listX, (a, b) -> a[0].compareTo(b[0]));
            Collections.sort(listXbis);

            // Var to contain base X
            int baseX = 0;
            if (listX.get(0)[0] < 0) {
                baseX = 0 - listX.get(0)[1];
            } else {
                baseX = listX.get(0)[0];
            }

            // For each screen, create a button (screen sorted by order)
            for (Integer[] el : listX) {
                for (GraphicsDevice screen : screens) {
                    int index;
                    Rectangle tmp = screen.getDefaultConfiguration().getBounds();
                    index = listXbis.indexOf((int) tmp.getMinX());

                    Button btn = new Button(index + 1 + "");
                    btn.setPadding(new Insets(7, 10, 7, 10));

                    int finalBaseX = baseX;
                    btn.setOnAction(e -> {
                        Rectangle rect = new Rectangle((int) tmp.getMinX(), 0, tmp.width, tmp.height);
                        BufferedImage image = r.createScreenCapture(rect);
                        Image img = SwingFXUtils.toFXImage(image, null);

                        new Display(img, (int) finalBaseX, 0).show();
                    });

                    if (el[0] == tmp.getMinX()) {
                        zoneBtn.getChildren().add(btn);
                        baseX += tmp.width;
                    }
                }
            }

            // Add all the elements to the root
            root.getChildren().addAll(zoneText, zoneBtn, zoneActualisation);
            root.setAlignment(Pos.CENTER);
        }
        catch (AWTException ex) {
            System.out.println(ex);
        }
        return root;
    }
}
