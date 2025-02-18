package newView.SceneMakers;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import newView.GraphicalElements.BackgroundMaker;
import newView.GraphicalElements.MyScene;
import newView.GraphicalElements.ScaleTool;

import java.io.File;
import java.io.FileInputStream;

public class BattleSceneMaker extends SceneMaker {
    public static AudioClip battleBgSound = new AudioClip(new File("src/newView/resources/sounds/battle/battlebg.mp3").toURI().toString());

    public BattleSceneMaker(Stage primaryStage) {
        super(primaryStage);
        if (!battleBgSound.isPlaying())
            battleBgSound.play();

    }

    @Override
    public Scene makeScene() throws Exception {
        Pane pane = new Pane();

        BackgroundMaker.setBackgroundFor(pane, 1, "battleEntry");

        ImageView back = new ImageView(new Image(new FileInputStream("src/newView/resources/battleEntry/back.png")));
        ScaleTool.resizeImageView(back, 85, 85);
        back.setOnMouseClicked(event -> {
            new MainMenuSceneMaker(getPrimaryStage()).set();
            battleBgSound.stop();
        });


        ImageView storyMode = new ImageView(new Image(new FileInputStream("src/newView/resources/battleEntry/singlePlayer.png")));
        ImageView customGame = new ImageView(new Image(new FileInputStream("src/newView/resources/battleEntry/multiPlayer.png")));

        ScaleTool.resizeImageView(customGame, 100, 200);
        ScaleTool.resizeImageView(storyMode, 100, 203);

        ScaleTool.homothety(customGame, 3);
        ScaleTool.homothety(storyMode, 3);

        ScaleTool.relocate(customGame, 300, 200);
        ScaleTool.relocate(storyMode, 800, 200);

        customGame.setOnMouseClicked(event -> new GameModeSelectorSceneMaker(getPrimaryStage(), true).set());
        storyMode.setOnMouseClicked(event -> new GameModeSelectorSceneMaker(getPrimaryStage(), false).set());


        Text storyModeText = new Text();
        storyModeText.setText("STORY MODE");
        storyModeText.setFill(Color.WHITE);
        storyModeText.setStyle("-fx-font-size: 30");
        ScaleTool.relocate(storyModeText, 250, 520);

        Text customGameText = new Text();
        customGameText.setText("CUSTOM GAME");
        customGameText.setFill(Color.WHITE);
        customGameText.setStyle("-fx-font-size: 30");
        ScaleTool.relocate(customGameText, 750, 520);

        pane.getChildren().addAll(storyMode, customGame, customGameText, storyModeText, back);

        return new MyScene(pane);
    }
}
