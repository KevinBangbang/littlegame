package MovePackage;

import AdventureModel.Player;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class MoveRight implements Move{
    Player player;
    Button button;
    public MoveRight(Player player, Button button){
        this.player = player;
        this.button = button;
    }
    public void execute(){
        if (this.player.getCurrentPos()[0]+setting.moveSpeed<setting.width- setting.moveSpeed){
        this.player.getCurrentPos()[0] += setting.moveSpeed;
        transition.setByX(setting.moveSpeed);
        transition.play();}
    }
}
