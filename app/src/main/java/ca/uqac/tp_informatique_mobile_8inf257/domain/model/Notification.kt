package ca.uqac.tp_informatique_mobile_8inf257.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class Notification(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String,
    val selectedHour: String,
    val description: String,
    val days: String,
    val timeLeft: String,
    val active: Boolean
)
