package ca.uqac.tp_informatique_mobile_8inf257.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cheat_sheets")
data class CheatSheet(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String,
    val description: String,
    val document: String, //uri du document
)