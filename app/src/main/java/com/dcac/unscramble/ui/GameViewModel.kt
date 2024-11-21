package com.dcac.unscramble.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.dcac.unscramble.data.MAX_NO_OF_WORDS
import com.dcac.unscramble.data.SCORE_INCREASE
import com.dcac.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// ViewModel to manage game state and logic
class GameViewModel : ViewModel() {
    // Internal mutable state of the UI
    private val _uiState = MutableStateFlow(GameUiState())
    // State exposed as read-only for external observers
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
    // Variable to store the current word (to guess or mix)
    private lateinit var currentWord: String
    // Mutable set to track words already used
    private var usedWords: MutableSet<String> = mutableSetOf()
    // Holds the current guess entered by the user, initialized as an empty string.
    // Changes to this state trigger UI recomposition, and it can only be updated
    // within the ViewModel to ensure encapsulation.
    var userGuess by mutableStateOf("")
        private set

    // Initialization block executed when creating the ViewModel object
    init {
        resetGame() // Initialize the game by clearing the state and choosing a first shuffled word
    }

    /**
     * Selects a random word from a list (allWords) and shuffles it.
     * Ensures that the chosen word has not already been used.
     * @return A mixed word that has not yet been used.
     */
    private fun pickRandomWordAndShuffle(): String {
        // Choose a random word from the available words
        currentWord = allWords.random()
        // Check if the word has already been used
        if (usedWords.contains(currentWord)) {
            // If already used, try another word recursively
            return pickRandomWordAndShuffle()
        } else {
            // Adds the word to the list of used words
            usedWords.add(currentWord)
            // Return the mixed word
            return shuffleCurrentWord(currentWord)
        }
    }

    /**
     * Shuffles letters of a given word randomly.
     * Ensures the mixed word is different from the original word.
     * @param word The word to mix.
     * @return The mixed word.
     */
    private fun shuffleCurrentWord(word: String): String {
        // Convert the word to an array of characters to shuffle it
        val tempWord = word.toCharArray()
        // Mix the letters in the table
        tempWord.shuffle()
        // Ensures the mixed word is different from the original word
        while (String(tempWord) == word) {
            tempWord.shuffle()
        }
        // Return the mixed word as a string
        return String(tempWord)
    }

    // Updates the current user guess with the provided input string.
    // This function is the only way to modify `userGuess` to maintain controlled state changes.
    fun updateUserGuess(guessedWord: String){
        userGuess = guessedWord
    }

    // Compares the user's guess (`userGuess`) with the current word (`currentWord`).
    // If the guess is correct, further logic can be added here for success handling.
    // If the guess is incorrect, the state (`_uiState`) is updated to indicate an error.
    // Resets the user's guess to an empty string after the check.
    fun checkUserGuess() {

        if (userGuess.equals(currentWord, ignoreCase = true)) {
            // User's guess is correct, increase the score
            // and call updateGameState() to prepare the game for next round
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            updateGameState(updatedScore)
        } else {
            // User's guess is wrong, show an error
            _uiState.update { currentState ->
                currentState.copy(isGuessedWordWrong = true)
            }
        }
        // Reset user guess
        updateUserGuess("")
    }

    private fun updateGameState(updatedScore: Int) {
        if (usedWords.size == MAX_NO_OF_WORDS){
            //Last round in the game, update isGameOver to true, don't pick a new word
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    score = updatedScore,
                    isGameOver = true
                )
            }
        } else{
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    currentScrambledWord = pickRandomWordAndShuffle(),
                    score = updatedScore,
                    currentWordCount = currentState.currentWordCount.inc(),
                )
            }
        }
    }

    fun skipWord() {
        updateGameState(_uiState.value.score)
        // Reset user guess
        updateUserGuess("")
    }

    /**
     *Resets game state:
     * - Empties the list of words used.
     * - Sets a new state with a mixed word.
     */
    fun resetGame() {
        // Resets all words used
        usedWords.clear()

        // Updates the UI state with a new shuffled word
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }
}