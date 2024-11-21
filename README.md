# Unscramble Game App 🎮
**Unscramble Game App** is a fun and interactive Android application built with Jetpack Compose. The goal of the game is to unscramble shuffled words and guess the correct ones to earn points. With a sleek and dynamic UI, the app provides a smooth user experience while demonstrating state management and Compose best practices.

## 📋 Features

### Core Gameplay
   - 📝 **Scrambled Words**: Players are presented with randomly shuffled words.
   - 🔡 **User Input**: Enter your guess in the text field.
   - ✅ **Validation**: Check if your guess matches the original word.
   - 🔄 **Skip Words**: Skip the current word without penalty.

### Scoring System
   - 🏆 **Earn Points**: Gain points for each correct guess.
   - 📈 **Track Progress**: The app keeps track of your current score and the number of words guessed.

### Game Rounds
   - 🚀 **Max Words**: Play up to a set number of words (default: 10).
   - 🛑 **Game Over**: End the game when all rounds are complete, with a final score display.

### User Feedback
   - ⚠️ **Error Handling**: Displays a red border and error message for incorrect guesses.
   - 🎉 **Game Completion Dialog**: A congratulatory dialog shows your total score and allows you to restart the game.


## 🛠️ Tech Stack

### Frameworks & Libraries
   - **Jetpack Compose**: Modern toolkit for building native UIs.
   - **Kotlin**: Programming language for concise and safe code.

### Architecture
**MVVM Pattern**:
   - **ViewModel**: Manages game state and logic.
   - **StateFlow** & **mutableStateOf**: For reactive and observable state management.

### Other Components
   - **Material Design 3**: Implements Material You UI components for a clean and modern interface.

## 📦 Project Structure
**Packages**:
1. **data/**:
   - Contains word data and game constants (e.g., maximum rounds, score increment).
2. **ui/**:
   - Handles the UI with composable functions for screens and layouts.
3. **MainActivity**:
   - Hosts the main entry point and sets up the app theme.

**Key Files**:
   - **GameViewModel.kt**: Implements the game logic and state management.
   - **GameUiState.kt**: Defines the immutable state used for UI updates.
   - **GameScreen.kt**: Main composable displaying the game UI and connecting to the ViewModel.

## 🚀 How to Play
1. Launch the App: Start the game from your device or emulator.
2. Unscramble Words: Enter the correct word in the text field.
3. Submit Your Guess: Click "Submit" or press "Done" on the keyboard.
4. Skip if Stuck: Use the "Skip" button to move to the next word.
5. Track Your Score: Watch your score increase with each correct answer.
6. Finish the Game: Complete all rounds to see your final score.