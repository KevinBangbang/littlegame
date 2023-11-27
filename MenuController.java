package Controller;

import AdventureModel.AdventureGame;
import Button.GameButton;
import Button.StartButton;
import javafx.geometry.Pos;
import views.MenuView;

public class MenuController {

    AdventureGame gameModel;
    MenuView menuView;

    public MenuController(AdventureGame gameModel, MenuView menuView)
    {
        this.gameModel = gameModel;
        this.menuView = menuView;
    }

    public void showStart()
    {

        GameButton startButton = new StartButton(200, 100, this.menuView);

        this.menuView.getStackPane().getChildren().add(startButton);
    }
    public void showInstruction()
    {

    }
    public void showSetting()
    {

    }
    public void showMenu()
    {
        this.menuView.show();
        this.showStart();
        this.showInstruction();
        this.showSetting();
    }
}
