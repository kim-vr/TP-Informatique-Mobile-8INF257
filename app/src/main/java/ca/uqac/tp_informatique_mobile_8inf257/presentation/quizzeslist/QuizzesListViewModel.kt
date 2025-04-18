package ca.uqac.tp_informatique_mobile_8inf257.presentation.quizzeslist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.quizzes.QuizzesUseCases
import ca.uqac.tp_informatique_mobile_8inf257.presentation.QuizVM
import ca.uqac.tp_informatique_mobile_8inf257.presentation.toEntity
import ca.uqac.tp_informatique_mobile_8inf257.presentation.toEntityFull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizzesListViewModel @Inject constructor(
    private val quizzesUseCases: QuizzesUseCases
) : ViewModel() {

    private val _quizzes: MutableState<List<QuizVM>> = mutableStateOf(emptyList())
    val quizzes: State<List<QuizVM>> = _quizzes

    private var job: Job? = null

    init {
        loadQuizzes()
    }

    private fun loadQuizzes() {
        job?.cancel()
        job = quizzesUseCases.getQuizzes().onEach { list ->
            _quizzes.value = list.map { QuizVM.fromEntity(it) }
        }.launchIn(viewModelScope)
    }

    private fun upsertQuiz(vm: QuizVM) {
        viewModelScope.launch {
            quizzesUseCases.upsertQuiz(vm.toEntityFull())
        }
    }


    private fun deleteQuiz(vm: QuizVM) {
        viewModelScope.launch {
            quizzesUseCases.deleteQuiz(vm.toEntity())
        }
    }
}