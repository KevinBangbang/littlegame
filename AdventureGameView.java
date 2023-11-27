package views;

import AdventureModel.AdventureGame;
import AdventureModel.AdventureObject;
import AdventureModel.Room;
import AdventureModel.Setting;
import Controller.MenuController;
import MovePackage.*;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.layout.*;
import javafx.scene.input.KeyEvent; //you will need these!
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.event.EventHandler; //you will need this too!
import javafx.scene.AccessibleRole;


import java.io.IOException;
import java.util.*;
import java.io.FileNotFoundException;

import java.io.File;

/**

 */
public class AdventureGameView {

    AdventureGame model; //model of the game
    Stage stage; //stage on which all is rendered
    Button saveButton, loadButton, helpButton; //buttons
    Boolean helpToggle = false; //is help on display?

    GridPane gridPane = new GridPane(); //to hold images and buttons
    Label roomDescLabel = new Label(); //to hold room description and/or instructions
    VBox objectsInRoom = new VBox(); //to hold room items
    VBox objectsInInventory = new VBox(); //to hold inventory items
    ImageView roomImageView; //to hold room image
    TextField inputTextField; //for user input
    Setting setting;
    AnchorPane root = new AnchorPane();
    Button player;
    ImageView floor;
    ImageView playerFront;
    ImageView playerBack;
    ImageView playerLeft;
    ImageView playerRight;
    MenuView menuView;
    MenuController menuController;

    Scene gameScene;
    Scene menuScene;


    private MediaPlayer mediaPlayer; //to play audio
    private boolean mediaPlaying; //to know if the audio is playing
    private AdventureGame currentGame; // Current game instance

    /**
     * Adventure Game View Constructor
     * __________________________
     * Initializes attributes
     */
    public AdventureGameView(AdventureGame model, Stage stage) throws IOException {
        this.model = model;
        this.stage = stage;
        this.currentGame = model;
        this.setting = new Setting(this.model.getDirectoryName());
        this.menuView = new MenuView(this.menuScene, this.model.getDirectoryName(), this.setting, this.stage, this);
        this.menuController = new MenuController(this.model, this.menuView);
        this.menuController.showMenu();
        this.stage.setResizable(false);
//        intiUI();
    }

    /**
     * Initialize the UI
     */
    public void intiUI() throws IOException {


        // setting up the stage
        this.stage.setTitle("group2's Adventure Game");
        this.gameScene = new Scene(root, this.setting.width, this.setting.height);
        this.playerFront = new ImageView(new Image(this.model.getDirectoryName()+"/player-images/character-front.png"));
        this.playerFront.setFitWidth(setting.playerWidth);
        this.playerFront.setFitHeight(setting.playerHeight);

        this.playerBack = new ImageView(new Image(this.model.getDirectoryName()+"/player-images/character-back.png"));
        this.playerBack.setFitWidth(setting.playerWidth);
        this.playerBack.setFitHeight(setting.playerHeight);

        this.playerLeft = new ImageView(new Image(this.model.getDirectoryName()+"/player-images/character-left.png"));
        this.playerLeft.setFitWidth(setting.playerWidth*0.5);
        this.playerLeft.setFitHeight(setting.playerHeight);

        this.playerRight = new ImageView(new Image(this.model.getDirectoryName()+"/player-images/character-right.png"));
        this.playerRight.setFitWidth(setting.playerWidth*0.5);
        this.playerRight.setFitHeight(setting.playerHeight);

        this.floor = new ImageView(new Image(this.model.getDirectoryName()+"/background/wood-floor.png"));
        this.floor.setFitHeight(this.setting.height);
        this.floor.setFitWidth(this.setting.width);
        this.root.getChildren().add(this.floor);
        this.player = new Button();
        this.player.setGraphic(this.playerFront);
        this.player.setPrefSize(setting.playerWidth, setting.playerHeight);
        this.player.setStyle("-fx-background-color: transparent;");
        this.player.setLayoutX(this.model.getPlayer().getCurrentPos()[0]);
        this.player.setLayoutY(this.model.getPlayer().getCurrentPos()[1]);
        this.root.getChildren().add(player);

        listenMove(this.gameScene);
        this.stage.setScene(this.gameScene);
        this.stage.show();
        //Inventory + Room items


    }

    public void listenMove(Scene scene){
        MoveExecution moveExecution = new MoveExecution();
        Move up = new MoveUp(this.model.getPlayer(), this.player);
        Move down = new MoveDown(this.model.getPlayer(), this.player);
        Move left = new MoveLeft(this.model.getPlayer(), this.player);
        Move right = new MoveRight(this.model.getPlayer(), this.player);




        ImageView frontImage = this.playerFront;
        ImageView backImage = this.playerBack;
        ImageView leftImage = this.playerLeft;
        ImageView rightImage = this.playerRight;
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode()==KeyCode.W)
                {
                    moveExecution.execute(up);
                    updatePlayer(backImage);

                }
                else if (keyEvent.getCode()==KeyCode.S)
                {
                    moveExecution.execute(down);
                    updatePlayer(frontImage);

                }
                if (keyEvent.getCode()==KeyCode.A)
                {
                    moveExecution.execute(left);
                    updatePlayer(leftImage);

                }
                if (keyEvent.getCode()==KeyCode.D)
                {
                    moveExecution.execute(right);
                    updatePlayer(rightImage);

                }
            }
        });
    }

    public void updatePlayer(ImageView image){

        this.player.setGraphic(image);
        this.player.setLayoutX(this.model.getPlayer().getCurrentPos()[0]);
        this.player.setLayoutY(this.model.getPlayer().getCurrentPos()[1]);

    }

    /**
     * makeButtonAccessible
     * __________________________
     * For information about ARIA standards, see
     * https://www.w3.org/WAI/standards-guidelines/aria/
     *
     * @param inputButton the button to add screenreader hooks to
     * @param name        ARIA name
     * @param shortString ARIA accessible text
     * @param longString  ARIA accessible help text
     */
    public static void makeButtonAccessible(Button inputButton, String name, String shortString, String longString) {
        inputButton.setAccessibleRole(AccessibleRole.BUTTON);
        inputButton.setAccessibleRoleDescription(name);
        inputButton.setAccessibleText(shortString);
        inputButton.setAccessibleHelp(longString);
        inputButton.setFocusTraversable(true);
    }

    /**
     * customizeButton
     * __________________________
     *
     * @param inputButton the button to make stylish :)
     * @param w           width
     * @param h           height
     */
    private void customizeButton(Button inputButton, int w, int h) {
        inputButton.setPrefSize(w, h);
        inputButton.setFont(new Font("Arial", 16));
        inputButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
    }

    /**
     * addTextHandlingEvent
     * __________________________
     * Add an event handler to the myTextField attribute
     * <p>
     * Your event handler should respond when users
     * hits the ENTER or TAB KEY. If the user hits
     * the ENTER Key, strip white space from the
     * input to myTextField and pass the stripped
     * string to submitEvent for processing.
     * <p>
     * If the user hits the TAB key, move the focus
     * of the scene onto any other node in the scene
     * graph by invoking requestFocus method.
     */
    private void addTextHandlingEvent() {
        inputTextField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String command = inputTextField.getText().trim();
                if (!command.isEmpty()) {
                    submitEvent(command);
                }
            } else if (event.getCode() == KeyCode.TAB) {
                gridPane.requestFocus(); // Move focus to the GridPane or any other node.
            }
        });
    }


    /**
     * submitEvent
     * __________________________
     *
     * @param text the command that needs to be processed
     */
    private void submitEvent(String text) {

        text = text.strip(); //get rid of white space
        stopArticulation(); //if speaking, stop

        if (text.equalsIgnoreCase("LOOK") || text.equalsIgnoreCase("L")) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescription();
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (!objectString.isEmpty()) roomDescLabel.setText(roomDesc + "\n\nObjects in this room:\n" + objectString);
            articulateRoomDescription(); //all we want, if we are looking, is to repeat description.
            return;
        } else if (text.equalsIgnoreCase("HELP") || text.equalsIgnoreCase("H")) {
            showInstructions();
            return;
        } else if (text.equalsIgnoreCase("COMMANDS") || text.equalsIgnoreCase("C")) {
            showCommands(); //this is new!  We did not have this command in A1
            return;
        }

        //try to move!
        String output = this.model.interpretAction(text); //process the command!

        if (output == null || (!output.equals("GAME OVER") && !output.equals("FORCED") && !output.equals("HELP"))) {
            updateScene(output);
            updateItems();
        } else if (output.equals("GAME OVER")) {
            updateScene("");
            updateItems();
            PauseTransition pause = new PauseTransition(Duration.seconds(10));
            pause.setOnFinished(event -> {
                Platform.exit();
            });
            pause.play();
        } else if (output.equals("FORCED")) {
            //write code here to handle "FORCED" events!
            //Your code will need to display the image in the
            //current room and pause, then transition to
            //the forced room.
            updateScene(""); // 更新显示以显示当前房间的图像
            updateItems(); // 更新物品

            PauseTransition delay = new PauseTransition(Duration.seconds(5)); // 暂停5秒
            delay.setOnFinished(event -> {
                // 在这里，您可以添加代码以在FORCED方向上过渡到房间
                String forcedDirection = model.getPlayer().getCurrentRoom().getMotionTable().getDirection().get(0).getDirection();
                model.movePlayer(forcedDirection);

                updateScene(""); // 移动到新房间后更新显示
                updateItems(); // 更新物品
            });
            delay.play();
        }
    }


    /**
     * showCommands
     * __________________________
     * <p>
     * update the text in the GUI (within roomDescLabel)
     * to show all the moves that are possible from the
     * current room.
     */
    private void showCommands() {
        List<String> exits = model.getPlayer().getCurrentRoom().getExits();
        Set<String> uniqueExits = new HashSet<>(exits); // 使用HashSet来去除重复项

        StringBuilder commandString = new StringBuilder("You can: ");
        for (String exit : uniqueExits) {
            commandString.append(exit).append(", ");
        }
        if (!uniqueExits.isEmpty()) {
            commandString.setLength(commandString.length() - 2); // 移除最后的逗号和空格
        }

        roomDescLabel.setText(commandString.toString());
    }


    /**
     * updateScene
     * __________________________
     * <p>
     * Show the current room, and print some text below it.
     * If the input parameter is not null, it will be displayed
     * below the image.
     * Otherwise, the current room description will be dispplayed
     * below the image.
     *
     * @param textToDisplay the text to display below the image.
     */
    public void updateScene(String textToDisplay) {

        getRoomImage(); //get the image of the current room
        formatText(textToDisplay); //format the text to display
        roomDescLabel.setPrefWidth(500);
        roomDescLabel.setPrefHeight(500);
        roomDescLabel.setTextOverrun(OverrunStyle.CLIP);
        roomDescLabel.setWrapText(true);
        VBox roomPane = new VBox(roomImageView, roomDescLabel);
        roomPane.setPadding(new Insets(10));
        roomPane.setAlignment(Pos.TOP_CENTER);
        roomPane.setStyle("-fx-background-color: #000000;");

        gridPane.add(roomPane, 1, 1);
        stage.sizeToScene();

        //finally, articulate the description
        if (textToDisplay == null || textToDisplay.isBlank()) articulateRoomDescription();
    }

    /**
     * formatText
     * __________________________
     * <p>
     * Format text for display.
     *
     * @param textToDisplay the text to be formatted for display.
     */
    private void formatText(String textToDisplay) {
        if (textToDisplay == null || textToDisplay.isBlank()) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescription() + "\n";
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (objectString != null && !objectString.isEmpty())
                roomDescLabel.setText(roomDesc + "\n\nObjects in this room:\n" + objectString);
            else roomDescLabel.setText(roomDesc);
        } else roomDescLabel.setText(textToDisplay);
        roomDescLabel.setStyle("-fx-text-fill: white;");
        roomDescLabel.setFont(new Font("Arial", 16));
        roomDescLabel.setAlignment(Pos.CENTER);
    }

    /**
     * getRoomImage
     * __________________________
     * <p>
     * Get the image for the current room and place
     * it in the roomImageView
     */
    private void getRoomImage() {

        int roomNumber = this.model.getPlayer().getCurrentRoom().getRoomNumber();
        String roomImage = this.model.getDirectoryName() + "/room-images/" + roomNumber + ".png";

        Image roomImageFile = new Image(roomImage);
        roomImageView = new ImageView(roomImageFile);
        roomImageView.setPreserveRatio(true);
        roomImageView.setFitWidth(400);
        roomImageView.setFitHeight(400);

        //set accessible text
        roomImageView.setAccessibleRole(AccessibleRole.IMAGE_VIEW);
        roomImageView.setAccessibleText(this.model.getPlayer().getCurrentRoom().getRoomDescription());
        roomImageView.setFocusTraversable(true);
    }

    /**
     * updateItems
     * __________________________
     * <p>
     * This method is partially completed, but you are asked to finish it off.
     * <p>
     * The method should populate the objectsInRoom and objectsInInventory Vboxes.
     * Each Vbox should contain a collection of nodes (Buttons, ImageViews, you can decide)
     * Each node represents a different object.
     * <p>
     * Images of each object are in the assets
     * folders of the given adventure game.
     */
    public void updateItems() {
        // Clear previous objects
        objectsInRoom.getChildren().clear();
        objectsInInventory.getChildren().clear();

        // Update objects in the current room
        Room currentRoom = model.getPlayer().getCurrentRoom();
        ArrayList<AdventureObject> roomObjects = currentRoom.objectsInRoom;
        for (AdventureObject obj : roomObjects) {
            String objectName = obj.getName();
            String imagePath = model.getDirectoryName() + "/objectImages/" + objectName + ".jpg";

            Button objectButton = new Button();
            objectButton.setGraphic(new ImageView(new Image(new File(imagePath).toURI().toString(), 100, 0, true, true)));
            makeButtonAccessible(objectButton, objectName, objectName, objectName);

            objectButton.setOnAction(event -> {
                String result = model.interpretAction("TAKE " + objectName);
                updateItems();
                roomDescLabel.setText(result);
            });

            objectsInRoom.getChildren().add(objectButton);
        }

        // Update objects in player's inventory
        ArrayList<String> playerInventory = model.getPlayer().getInventory();
        for (String objectName : playerInventory) {
            String imagePath = model.getDirectoryName() + "/objectImages/" + objectName + ".jpg";

            Button objectButton = new Button();
            objectButton.setGraphic(new ImageView(new Image(new File(imagePath).toURI().toString(), 100, 0, true, true)));
            makeButtonAccessible(objectButton, objectName, objectName, objectName);

            objectButton.setOnAction(event -> {
                String result = model.interpretAction("DROP " + objectName);
                updateItems();
                roomDescLabel.setText(result);
            });

            objectsInInventory.getChildren().add(objectButton);
        }
        //write some code here to add images of objects in a given room to the objectsInRoom Vbox
        //write some code here to add images of objects in a player's inventory room to the objectsInInventory Vbox
        //please use setAccessibleText to add "alt" descriptions to your images!
        //the path to the image of any is as follows:
        //this.model.getDirectoryName() + "/objectImages/" + objectName + ".jpg";

        ScrollPane scO = new ScrollPane(objectsInRoom);
        scO.setPadding(new Insets(10));
        scO.setStyle("-fx-background: #000000; -fx-background-color:transparent;");
        scO.setFitToWidth(true);
        gridPane.add(scO, 0, 1);

        ScrollPane scI = new ScrollPane(objectsInInventory);
        scI.setFitToWidth(true);
        scI.setStyle("-fx-background: #000000; -fx-background-color:transparent;");
        gridPane.add(scI, 2, 1);


    }

    /*
     * Show the game instructions.
     *
     * If helpToggle is FALSE:
     * -- display the help text in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- use whatever GUI elements to get the job done!
     * -- set the helpToggle to TRUE
     * -- REMOVE whatever nodes are within the cell beforehand!
     *
     * If helpToggle is TRUE:
     * -- redraw the room image in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- set the helpToggle to FALSE
     * -- Again, REMOVE whatever nodes are within the cell beforehand!
     */
    public void showInstructions() {
        if (!helpToggle) {
            try {
                String directoryName = this.model.getDirectoryName();
                // 读取 help.txt 文件的内容
                File file = new File(directoryName + "/help.txt");
                Scanner scanner = new Scanner(file);
                StringBuilder helpTextBuilder = new StringBuilder();
                while (scanner.hasNextLine()) {
                    helpTextBuilder.append(scanner.nextLine()).append("\n");
                }
                scanner.close();

                // 创建带有黑色背景的 VBox 来显示帮助文本
                VBox helpTextContainer = new VBox();
                helpTextContainer.setStyle("-fx-background-color: black;"); // 设置黑色背景
                Label helpTextLabel = new Label(helpTextBuilder.toString());
                helpTextLabel.setStyle("-fx-text-fill: white;");
                helpTextLabel.setFont(new Font("Arial", 16));
                helpTextLabel.setWrapText(true);
                helpTextContainer.getChildren().add(helpTextLabel);

                // 在 gridPane 中显示 VBox
                gridPane.add(helpTextContainer, 1, 1);

                helpToggle = true;
            } catch (FileNotFoundException e) {
                System.out.println("Error reading help.txt: " + e.getMessage());
            }
        } else {
            // 移除帮助文本并重新显示房间的图像和描述
            updateScene(null);
            helpToggle = false;
        }
    }

    /**
     * This method handles the event related to the
     * help button.
     */
    public void addInstructionEvent() {
        helpButton.setOnAction(e -> {
            stopArticulation(); //if speaking, stop
            showInstructions();
        });
    }

    /**
     * This method handles the event related to the
     * save button.
     */
    public void addSaveEvent() {
        saveButton.setOnAction(e -> {
            gridPane.requestFocus();
            SaveView saveView = new SaveView(this);
        });
    }

    /**
     * This method handles the event related to the
     * load button.
     */
    public void addLoadEvent() {
        loadButton.setOnAction(e -> {
            gridPane.requestFocus();
            LoadView loadView = new LoadView(this);
        });
    }


    /**
     * This method articulates Room Descriptions
     */
    public void articulateRoomDescription() {
        String musicFile;
        String adventureName = this.model.getDirectoryName();
        String roomName = this.model.getPlayer().getCurrentRoom().getRoomName();

        if (!this.model.getPlayer().getCurrentRoom().getVisited())
            musicFile = "./" + adventureName + "/sounds/" + roomName.toLowerCase() + "-long.mp3";
        else musicFile = "./" + adventureName + "/sounds/" + roomName.toLowerCase() + "-short.mp3";
        musicFile = musicFile.replace(" ", "-");

        Media sound = new Media(new File(musicFile).toURI().toString());

        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        mediaPlaying = true;

    }

    /**
     * This method stops articulations
     * (useful when transitioning to a new room or loading a new game)
     */
    public void stopArticulation() {
        if (mediaPlaying) {
            mediaPlayer.stop(); //shush!
            mediaPlaying = false;
        }
    }

    public void setCurrentGame(AdventureGame loadedGame, Label selectGameLabel) {
        try {
            // 尝试使用已加载的游戏设置当前游戏
            if (loadedGame == null) {
                throw new Exception("No game loaded.");
            }

            this.currentGame = loadedGame;
            this.model = loadedGame;

            updateUI();

            // 更新标签，通知用户游戏已成功加载
            selectGameLabel.setText("Successfully loaded the game.");

        } catch (Exception e) {
            // 如果加载游戏时出错，我们记录异常，并通过selectGameLabel通知用户
            selectGameLabel.setText("Failed to set the loaded game. Starting TinyGame instead...");

            // 开始一个叫做“TinyGame”的新游戏
            try {
                this.currentGame = new AdventureGame("TinyGame");
                // 假设AdventureGame的构造函数在出错时会抛出异常
            } catch (Exception ex) {
                // 处理与启动新的“TinyGame”相关的异常
                selectGameLabel.setText("Failed to start TinyGame. Please check the game files.");
                // 在此处，你也可能需要禁用与游戏相关的UI元素，直到有效的游戏被加载。
            }
        }
    }

    public AdventureGame getCurrentGame() {
        return this.currentGame;
    }

    public void updateUI() {
        updateScene(null); // 使用 null 更新场景，这将显示当前房间的描述
        updateItems();     // 更新房间和背包中的物品
        articulateRoomDescription(); // 播放关于当前房间的音频描述
    }

}
