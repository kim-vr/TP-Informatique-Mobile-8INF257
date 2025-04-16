package ca.uqac.tp_informatique_mobile_8inf257.presentation.addcheatsheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.CheatSheet
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.cheatsheets.CheatSheetsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCheatSheetViewModel @Inject constructor(
    private val cheatSheetUseCases: CheatSheetsUseCases
) : ViewModel() {

    fun addCheatSheet(title: String, description: String, documentUri: String) {
        val newCheatSheet = CheatSheet(
            id = null, // ou auto-généré dans la base
            title = title,
            description = description,
            document = documentUri
        )
        viewModelScope.launch {
            cheatSheetUseCases.upsertCheatSheet(newCheatSheet)
        }
    }
}
