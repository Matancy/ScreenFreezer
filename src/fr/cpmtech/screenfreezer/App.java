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
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class App extends Stage {

    private VBox root = new VBox();
    private HBox zoneText = new HBox();
    private HBox zoneBtn = new HBox();

    public App() {
        Scene scene = new Scene(content());
        this.setMinHeight(300);
        this.setMinWidth(300);
        this.setScene(scene);
        this.setAlwaysOnTop(true);
    }

    Parent content() {
        try {
            // Define some vars for screen
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice[] screens = ge.getScreenDevices();

            // To contain list of buttons
            zoneBtn.setSpacing(10);
            zoneBtn.setAlignment(Pos.CENTER);

            Label title = new Label("Ecran disponibles :");
            zoneText.getChildren().add(title);

            // Robot
            Robot r = new Robot();

            // List of min x for each screen
            ArrayList<Integer> listX = new ArrayList<Integer>();

            // Get each min x
            for (GraphicsDevice screen : screens) {
                Rectangle tmp = screen.getDefaultConfiguration().getBounds();
                listX.add((int) tmp.getMinX());
            }

            // Sort list of min x
            Collections.sort(listX);

            // For each screen, create a button (screen sorted by order)
            for (int el : listX) {
                for (GraphicsDevice screen : screens) {
                    int index;
                    Rectangle tmp = screen.getDefaultConfiguration().getBounds();
                    index = listX.indexOf((int) tmp.getMinX());

                    Button btn = new Button(index + 1 + "");
                    btn.setPadding(new Insets(10, 10, 10, 10));

                    btn.setOnAction(e -> {
                        Rectangle rect = new Rectangle((int) tmp.getMinX(), 0, tmp.width, tmp.height);
                        BufferedImage image = r.createScreenCapture(rect);
                        Image img = SwingFXUtils.toFXImage(image, null);

                        new Display(img, (int) tmp.getMinX(), 0).show();
                    });
                    if (el == tmp.getMinX()) {
                        zoneBtn.getChildren().add(btn);
                    }
                }
            }

            root.getChildren().addAll(zoneText, zoneBtn);
        }
        catch (AWTException ex) {
            System.out.println(ex);
        }
        return root;
    }
}
