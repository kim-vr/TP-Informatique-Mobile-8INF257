package ca.uqac.tp_informatique_mobile_8inf257.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import ca.uqac.tp_informatique_mobile_8inf257.domain.model.*

@Database(
    entities = [Quiz::class, Question::class, Answer::class],
    version = 2
)
abstract class QuizzesDatabase : RoomDatabase() {
    abstract val dao: QuizzesDao

    companion object {
        const val DATABASE_NAME = "quiz.db"
    }
}
