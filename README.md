# SOFTENG 206 - EscAIpe Room

## To setup OpenAI's API

- add in the root of the project (i.e., the same level where `pom.xml` is located) a file named `apiproxy.config`
- put inside the credentials that you received from no-reply@digitaledu.ac.nz (put the quotes "")

  ```
  email: "upi123@aucklanduni.ac.nz"
  apiKey: "YOUR_KEY"
  ```
  these are your credentials to invoke the OpenAI GPT APIs

## To setup codestyle's API

- add in the root of the project (i.e., the same level where `pom.xml` is located) a file named `codestyle.config`
- put inside the credentials that you received from gradestyle@digitaledu.ac.nz (put the quotes "")

  ```
  email: "upi123@aucklanduni.ac.nz"
  accessToken: "YOUR_KEY"
  ```

 these are your credentials to invoke GradeStyle

## To run the game

`./mvnw clean javafx:run`

## To debug the game

`./mvnw clean javafx:run@debug` then in VS Code "Run & Debug", then run "Debug JavaFX"

## To run codestyle

`./mvnw clean compile exec:java@style`

## Art Attribution

- All art used in this project was generated by Midjourney, licensed under Attribution-NonCommercial 4.0 International.

## Sound Attributions

- All music is used for educational purposes and is not intended for commercial use.

- **Policenauts - Opening Theme**: "egg1.mp3" Original music by Konami Kukeiha Club, directed by Hideo Kojima. 
- **Never Gonna Give You Up**: "egg2.mp3" Performed by Rick Astley, written by Mike Stock, Matt Aitken, and Pete Waterman.


