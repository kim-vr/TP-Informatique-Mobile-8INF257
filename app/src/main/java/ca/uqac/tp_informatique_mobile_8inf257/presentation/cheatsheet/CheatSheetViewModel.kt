package ca.uqac.tp_informatique_mobile_8inf257.presentation.cheatsheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.cheatsheets.CheatSheetsUseCases
import ca.uqac.tp_informatique_mobile_8inf257.presentation.CheatSheetVM
import ca.uqac.tp_informatique_mobile_8inf257.presentation.toEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheatSheetViewModel @Inject constructor(
    private val useCases: CheatSheetsUseCases
) : ViewModel() {

    private val _cheatSheet = MutableStateFlow<CheatSheetVM?>(null)
    val cheatSheet: StateFlow<CheatSheetVM?> = _cheatSheet

    private var job: Job? = null

    fun loadCheatSheet(id: Int) {
        job?.cancel()

        job = viewModelScope.launch {
            val cs = useCases.getCheatSheet(id)
            _cheatSheet.value = cs?.let { CheatSheetVM.fromEntity(it) }
        }
    }

    fun deleteCheatSheet(vm: CheatSheetVM) {
        viewModelScope.launch {
            useCases.deleteCheatSheet(vm.toEntity())
        }
    }
}