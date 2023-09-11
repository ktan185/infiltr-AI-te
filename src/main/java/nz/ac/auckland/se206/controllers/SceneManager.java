package nz.ac.auckland.se206.controllers;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Parent;

public class SceneManager {

  public enum AppUI {
    START,
    TITLE,
    WATCH,
    MAIN,
    LEFT,
    RIGHT,
    LOCKER,
    KEYPAD,
    BOOKSHELF,
    POPUP,
    BLACKBOARD
  }

  private static Map<AppUI, Parent> sceneMap = new HashMap<>();

  public static void addUserInterface(AppUI appUserInterface, Parent root) {
    sceneMap.put(appUserInterface, root);
  }

  public static Parent getuserInterface(AppUI ui) {
    return sceneMap.get(ui);
  }
}
