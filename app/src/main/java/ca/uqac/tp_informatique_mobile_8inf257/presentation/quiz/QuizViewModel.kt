package ca.uqac.tp_informatique_mobile_8inf257.presentation.quiz

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.quizzes.GetQuizUseCase
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.quizzes.QuizzesUseCases
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.todolist.ToDoListUseCases
import ca.uqac.tp_informatique_mobile_8inf257.presentation.QuizVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class QuizUiState(
    val quiz: QuizVM? = null,
    val userAnswers: List<Int?> = emptyList(),
    val showCorrection: Boolean = false,
    val correctAnswersCount: Int = 0
)

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizzesUseCases: QuizzesUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState

    fun loadQuiz(id: Int) {
        viewModelScope.launch {
            val quiz = quizzesUseCases.getQuiz(id)
            if (quiz != null) {
                _uiState.value = QuizUiState(
                    quiz = quiz?.let { QuizVM.fromEntity(it) },
                    userAnswers = List(quiz.questions.size) { null }
                )
            }
        }
    }

    fun selectAnswer(questionIndex: Int, answerIndex: Int) {
        val answers = _uiState.value.userAnswers.toMutableList()
        answers[questionIndex] = answerIndex
        _uiState.value = _uiState.value.copy(userAnswers = answers)
    }

    fun resetQuiz() {
        _uiState.value = QuizUiState(
            quiz = _uiState.value.quiz,
            userAnswers = List(_uiState.value.quiz?.questions?.size ?: 0) { null },
            showCorrection = false
        )
    }


    fun showCorrection() {
        val quiz = _uiState.value.quiz ?: return
        val userAnswers = _uiState.value.userAnswers
        var correct = 0

        quiz.questions.forEachIndexed { index, question ->
            if (userAnswers[index] == question.correctAnswerIndex) {
                correct++
            }
        }

        _uiState.value = _uiState.value.copy(
            showCorrection = true,
            correctAnswersCount = correct
        )
    }
}
