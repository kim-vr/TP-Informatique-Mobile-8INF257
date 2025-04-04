package ca.uqac.tp_informatique_mobile_8inf257.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.ToDo
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoListDao {

    @Query("SELECT * FROM todolist")
    fun getToDoList() : Flow<List<ToDo>>

    @Query("SELECT * FROM todolist WHERE ID = :id")
    fun getToDo(id: Int) : ToDo?

    @Upsert
    suspend fun upsertToDo(toDo: ToDo)

    @Delete
    suspend fun deleteToDo(toDo: ToDo)
}