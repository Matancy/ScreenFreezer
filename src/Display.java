import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Display extends Stage {

    private AnchorPane root = new AnchorPane();
    private ImageView imgView = new ImageView();

    Display (Image img, int x, int y) {
        Scene scene = new Scene(content(img));
        this.initStyle(StageStyle.UNDECORATED);
        this.setX(x);
        this.setY(y);
        this.setAlwaysOnTop(true);
        this.setScene(scene);
    }

    Parent content(Image img) {
        imgView.setImage(img);
        imgView.setOnMouseClicked(e-> {
            this.close();
        });
        root.getChildren().addAll(imgView);
        return root;
    }

}
