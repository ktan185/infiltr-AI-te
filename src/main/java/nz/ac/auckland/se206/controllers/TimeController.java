package nz.ac.auckland.se206.controllers;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import nz.ac.auckland.se206.Dialogue;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.Sound;
import nz.ac.auckland.se206.TimerClass;
import nz.ac.auckland.se206.controllers.SceneManager.AppUi;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;

public class TimeController {

  private static final int TIME_INCREMENT = 2;
  private static final int MAX_GAME_TIME = 6;
  private static final int MIN_GAME_TIME = 2;

  @FXML private ImageView watch;
  @FXML private Label diff;
  @FXML private Label time;
  @FXML private Button increase;
  @FXML private Button decrease;
  @FXML private Button set;
  @FXML private Button back;
  private int gameTime;

  public void initialize() {

    gameTime = 4;
    bindDifficulty();
    updateTime();
    initialiseButtons();
  }

  private void initialiseButtons() {
    increase.setOnAction(event -> adjustGameTime(TIME_INCREMENT));
    decrease.setOnAction(event -> adjustGameTime(-TIME_INCREMENT));
    back.setOnAction(event -> navigateBack());
  }

  // Bind the difficulty label to the difficulty property so once user has chosen difficulty, it
  // will show on watch when user selects time
  private void bindDifficulty() {
    diff.textProperty()
        .bind(
            Bindings.createStringBinding(
                () -> {
                  return GameState.difficulty.get() == 1
                      ? "EASY"
                      : GameState.difficulty.get() == 2 ? "MEDIUM" : "HARD";
                },
                GameState.difficulty));
  }

  private void adjustGameTime(int delta) {
    // Trigger the sound for clicking increase/decrease button and update time
    Sound.getInstance().playClickMinor();
    gameTime += delta;

    boolean shouldDisableIncrease = false;
    boolean shouldDisableDecrease = false;

    // Set limits on the time user can select, so button disables if they try to go above 6 mins, or
    // below 2 mins
    if (gameTime >= MAX_GAME_TIME) {
      gameTime = MAX_GAME_TIME;
      shouldDisableIncrease = true;
    } else if (gameTime <= MIN_GAME_TIME) {
      gameTime = MIN_GAME_TIME;
      shouldDisableDecrease = true;
    }

    // Check logic changes if button is disabled/enabled and set it to that state
    setButtonState(increase, shouldDisableIncrease);
    setButtonState(decrease, shouldDisableDecrease);

    if (!shouldDisableIncrease && !shouldDisableDecrease) {
      setButtonState(increase, false);
      setButtonState(decrease, false);
    }

    updateTime();
  }

  private void setButtonState(Button button, boolean isDisabled) {
    button.setDisable(isDisabled);
  }

  private void navigateBack() {
    Sound.getInstance().playClickMinor();
    Scene currentScene = back.getScene();
    currentScene.setRoot(SceneManager.getuserInterface(AppUi.TITLE));
  }

  @FXML
  private void onHover(MouseEvent event) {
    Sound.getInstance().playHover();
  }

  @FXML
  private void onClick(MouseEvent event) throws Exception {
    Button rectangle = (Button) event.getSource();
    Scene currentScene = rectangle.getScene();
    Sound.getInstance().playClickMajor();
    CommanderController instance = CommanderController.getInstance();
    // Check if Easy, medium or hard and update prompt accordingly.
    String prompt = null;
    if (GameState.difficulty.get() == 1) {
      prompt = GptPromptEngineering.getEasyPrompt(GameState.puzzleWord);
    } else if (GameState.difficulty.get() == 2) {
      prompt = GptPromptEngineering.getMediumPrompt(GameState.puzzleWord, 5);
    } else {
      prompt = GptPromptEngineering.getHardPrompt(GameState.puzzleWord);
    }
    instance.updateGpt(prompt);

    TimerClass timer = TimerClass.getInstance();
    timer.start(gameTime);

    // Update the scene to the main game.
    currentScene.setRoot(SceneManager.getuserInterface(AppUi.MAIN));

    // Reset the scene.
    resetTime();

    Platform.runLater(
        () -> {
          instance.updateDialogueBox(Dialogue.INITIAL.toString());
        });
  }

  private void updateTime() {
    time.setText(String.format("%d:00", gameTime));
  }

  // Reset the scene to default state for replaying.
  private void resetTime() {
    gameTime = 4;
    updateTime();
    increase.setDisable(false);
    decrease.setDisable(false);
  }
}
