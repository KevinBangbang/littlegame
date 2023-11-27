package AdventureModel;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import AdventureModel.AdventureLoader;

public class Setting {
    public final double width = 1280;
    public final double height = 720;
    public final double playerWidth = 50;
    public final double playerHeight = 100;
    public final int moveSpeed = 20;
    public ImageView frontView;
    public ImageView backView;

    public ImageView leftView;

    public ImageView rightView;

    public Setting(String dir)
    {
        this.frontView = new ImageView(new Image(dir+"/player-images/character-front.png"));
        this.backView = new ImageView(new Image(dir+"/player-images/character-back.png"));

        this.rightView = new ImageView(new Image(dir+"/player-images/character-left.png"));

        this.leftView = new ImageView(new Image(dir+"/player-images/character-right.png"));

    }
    public Setting(){}
}
