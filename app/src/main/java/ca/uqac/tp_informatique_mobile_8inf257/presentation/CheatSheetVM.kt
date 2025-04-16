package ca.uqac.tp_informatique_mobile_8inf257.presentation

import ca.uqac.tp_informatique_mobile_8inf257.domain.model.CheatSheet
import kotlin.random.Random

data class CheatSheetVM(
    val id: Int = Random.nextInt(),
    val title: String = "",
    val description: String = "",
    val document: String = "",
) {
    companion object {
        fun fromEntity(entity: CheatSheet): CheatSheetVM {
            return CheatSheetVM(
                id = entity.id!!,
                title = entity.title,
                description = entity.description,
                document = entity.document
            )
        }
    }
}

fun CheatSheetVM.toEntity() : CheatSheet {
    val id = if (this.id == -1) null else this.id
    return CheatSheet(
        id = id,
        title = this.title,
        description = this.description,
        document = this.document
    )
}
