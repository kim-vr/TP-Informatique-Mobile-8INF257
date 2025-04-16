package ca.uqac.tp_informatique_mobile_8inf257.presentation.cheatsheet

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.cheatsheets.CheatSheetsUseCases
import ca.uqac.tp_informatique_mobile_8inf257.presentation.CheatSheetVM
import ca.uqac.tp_informatique_mobile_8inf257.presentation.cheatsheetslist.CheatSheetsListEvents
import ca.uqac.tp_informatique_mobile_8inf257.presentation.toEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheatSheetsListViewModel @Inject constructor(
    private val cheatSheetUseCases: CheatSheetsUseCases
) : ViewModel() {

    private val _cheatSheets: MutableState<List<CheatSheetVM>> = mutableStateOf(emptyList())
    val cheatSheets: State<List<CheatSheetVM>> = _cheatSheets

    private var job: Job? = null

    init {
        loadCheatSheets()
    }

    private fun loadCheatSheets() {
        job?.cancel()
        job = cheatSheetUseCases.getCheatSheets().onEach { list ->
            _cheatSheets.value = list.map { CheatSheetVM.fromEntity(it) }
        }.launchIn(viewModelScope)
    }

    /*fun onEvent(event: CheatSheetsListEvents) {
        when (event) {
            is CheatSheetsListEvents.AddOrUpdate -> {
                upsertCheatSheet(event.cheatSheet)
            }

            is CheatSheetsListEvents.Delete -> {
                deleteCheatSheet(event.cheatSheet)
            }
        }
    }*/

    private fun upsertCheatSheet(vm: CheatSheetVM) {
        viewModelScope.launch {
            cheatSheetUseCases.upsertCheatSheet(vm.toEntity())
        }
    }

    private fun deleteCheatSheet(vm: CheatSheetVM) {
        viewModelScope.launch {
            cheatSheetUseCases.deleteCheatSheet(vm.toEntity())
        }
    }
}
