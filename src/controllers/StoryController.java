package controllers;

import contracts.StoryContract;
import models.*;
import models.artificialIntelligence.AIAccount;
import view.Notify;

public class StoryController implements StoryContract.Controller {
    private StoryContract.View view;

    public StoryController(StoryContract.View view) {
        this.view = view;
        view.setController(this);
    }

    @Override
    public void loadLevel(int levelNumber) {
        GameLevel gameLevel = GameContents.getGameLevel(levelNumber);

        if (gameLevel == null) {
            Notify.logError("This level doesn't exist!");
            return;
        }

        Deck oppDeck = gameLevel.getDeck();

        GameMode gameMode = MultiPlayerController.getGameMode(gameLevel.getMode());

        Account currentAccount = GameContents.getCurrentAccount();
        Account AIAccount = new AIAccount("AI", "123", oppDeck.getCopy());

        Game newGame = new Game(currentAccount, AIAccount, gameLevel.getPrize(), gameMode, gameLevel.getNumberOfFlags());
        GameContents.setCurrentGame(newGame);
//        view.goToLevelInGame();
    }

    @Override
    public void loadAllLevels() {
        view.showAllLevels(GameContents.getGameLevels());
    }
}
