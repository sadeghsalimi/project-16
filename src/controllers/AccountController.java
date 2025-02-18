package controllers;

import contracts.AccountContract;
import exception.AccountExistsException;
import exception.InvalidCredentialsException;
import models.Account;
import models.GameContents;
import view.MenuHandler;
import view.Notify;

import java.io.FileNotFoundException;
import java.io.IOException;

import static view.menuItems.MenuConstants.MAIN_MENU;

public class AccountController implements AccountContract.Controller {
    private AccountContract.View view;

    public AccountController(AccountContract.View view) {
        this.view = view;
        view.setController(this);
    }

    public AccountController(){

    }

    @Override
    public void loadLeaderboard() {
        GameContents.sortAccounts();
        view.showLeaderboard(GameContents.getAccounts());
    }

    @Override
    public void createAccount(String username, String password) throws AccountExistsException {
        Account account = GameContents.findAccount(username);
        if (account != null) {
            Notify.logError("An account with this username is already exist! Try another username.");
            throw new AccountExistsException();
        } else {
            GameContents.addAccount(new Account(username, password));
            Notify.logMessage("Good job! An account with name \"" + username + "\" created.");
        }
    }

    @Override
    public void loginAccount(String username, String password) throws InvalidCredentialsException {
        Account account = GameContents.findAccount(username);
        if (account == null || !account.getPassword().equals(password)) {
            Notify.logError("Invalid Credentials!");
            throw new InvalidCredentialsException();
        } else {
            GameContents.setCurrentAccount(account);
            GameContents.setSecondAccount(null);//phase 3: must change
            Notify.logMessage("Dear " + username + "!!! You logged in successfully!");
//            MenuHandler.goToSubMenu(MAIN_MENU);
        }
    }

    @Override
    public void saveGameData() {
        try {
            GameContents.saveAccount(GameContents.getCurrentAccount());
            Notify.logMessage("Your account saved successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadAccounts() {
        try {
            GameContents.loadAccounts();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
