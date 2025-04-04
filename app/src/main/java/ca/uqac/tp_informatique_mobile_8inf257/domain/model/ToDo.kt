package ca.uqac.tp_informatique_mobile_8inf257.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todolist")
data class ToDo (
    @PrimaryKey(autoGenerate = true) val id : Int? = null,
    val description : String,
    val done : Boolean = false
)

