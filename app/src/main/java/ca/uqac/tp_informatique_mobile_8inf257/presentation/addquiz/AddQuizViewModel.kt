package ca.uqac.tp_informatique_mobile_8inf257.presentation.addquiz

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.quizzes.QuizzesUseCases
import ca.uqac.tp_informatique_mobile_8inf257.presentation.AnswerVM
import ca.uqac.tp_informatique_mobile_8inf257.presentation.QuestionWithAnswersVM
import ca.uqac.tp_informatique_mobile_8inf257.presentation.QuizVM
import ca.uqac.tp_informatique_mobile_8inf257.presentation.toEntity
import ca.uqac.tp_informatique_mobile_8inf257.presentation.toEntityFull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddQuizViewModel @Inject constructor(
    private val quizzesUseCases: QuizzesUseCases
) : ViewModel() {

    private val _quizTitle = MutableStateFlow("")
    var quizTitle: StateFlow<String> = _quizTitle

    private val _questions = MutableStateFlow<List<QuestionWithAnswersVM>>(emptyList())
    val questions: StateFlow<List<QuestionWithAnswersVM>> = _questions

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    fun updateQuizTitle(title: String) {
        _quizTitle.value = title
    }

    fun addQuestion() {
        val updated = _questions.value.toMutableList()
        updated.add(QuestionWithAnswersVM())
        _questions.value = updated
    }

    fun updateQuestionText(index: Int, text: String) {
        val updated = _questions.value.toMutableList()
        updated[index] = updated[index].copy(text = text)
        _questions.value = updated
    }

    fun addAnswer(questionIndex: Int) {
        val updated = _questions.value.toMutableList()
        val question = updated[questionIndex]
        val newAnswers = question.answers + AnswerVM(text = "")
        updated[questionIndex] = question.copy(answers = newAnswers)
        _questions.value = updated
    }

    fun updateAnswerText(questionIndex: Int, answerIndex: Int, text: String) {
        val updated = _questions.value.toMutableList()
        val question = updated[questionIndex]
        val answers = question.answers.toMutableList()
        answers[answerIndex] = answers[answerIndex].copy(text = text)
        updated[questionIndex] = question.copy(answers = answers)
        _questions.value = updated
    }

    fun setCorrectAnswer(questionIndex: Int, answerIndex: Int) {
        val updated = _questions.value.toMutableList()
        val question = updated[questionIndex]
        val updatedAnswers = question.answers.mapIndexed { index, answer ->
            answer.copy(isCorrect = index == answerIndex)
        }
        updated[questionIndex] = question.copy(
            answers = updatedAnswers,
            correctAnswerIndex = answerIndex
        )

        _questions.value = updated
    }

    fun saveQuiz(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val quiz = QuizVM(
                    title = _quizTitle.value,
                    questions = _questions.value
                )
                quizzesUseCases.upsertQuiz(quiz.toEntityFull())
                onSuccess()
            } catch (e: Exception) {
                _error.value = e.message ?: "Erreur inconnue"
                onError(_error.value)
            }
        }
    }
}
