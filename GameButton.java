package Button;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class GameButton extends Button {
    String text;
    String textColor;
    ImageView imageView;
    ImageView imageViewHover;
    int width;
    int height;
    public GameButton(String text, int width, int height)
    {
        this.text = text;
        this.width = width;
        this.height = height;
        this.setGraphic(this.imageView);
        this.setWidth(width);
        this.setHeight(height);
    }

}
