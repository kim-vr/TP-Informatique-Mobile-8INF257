package ca.uqac.tp_informatique_mobile_8inf257.di

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ca.uqac.tp_informatique_mobile_8inf257.data.source.CheatSheetsDatabase
import ca.uqac.tp_informatique_mobile_8inf257.data.source.NotificationsDatabase
import ca.uqac.tp_informatique_mobile_8inf257.data.source.QuizzesDatabase
import ca.uqac.tp_informatique_mobile_8inf257.data.source.ToDoListDatabase
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.cheatsheets.CheatSheetsUseCases
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.cheatsheets.DeleteCheatSheetUseCase
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.cheatsheets.GetCheatSheetUseCase
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.cheatsheets.GetCheatSheetsUseCase
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.cheatsheets.UpsertCheatSheetUseCase
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.notifications.DeleteNotificationUseCase
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.todolist.DeleteToDoUseCase
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.notifications.GetNotificationUseCase
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.notifications.GetNotificationsUseCase
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.todolist.GetToDoListUseCase
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.todolist.GetToDoUseCase
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.notifications.NotificationsUseCases
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.todolist.ToDoListUseCases
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.notifications.UpsertNotificationUseCase
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.quizzes.DeleteQuizUseCase
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.quizzes.GetQuizUseCase
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.quizzes.GetQuizzesUseCase
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.quizzes.QuizzesUseCases
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.quizzes.UpsertQuizUseCase
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.todolist.UpsertToDoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideToDoListDatabase(context: Application): ToDoListDatabase {
        return Room.databaseBuilder(
            context,
            ToDoListDatabase::class.java,
            ToDoListDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideToDoListUseCases(db: ToDoListDatabase): ToDoListUseCases {
        return ToDoListUseCases(
            getToDoList = GetToDoListUseCase(db.dao),
            getToDo = GetToDoUseCase(db.dao),
            upsertToDo = UpsertToDoUseCase(db.dao),
            deleteToDo = DeleteToDoUseCase(db.dao)
        )
    }

    @Provides
    @Singleton
    fun provideNotificationsDatabase(context: Application): NotificationsDatabase {
        return Room.databaseBuilder(
            context,
            NotificationsDatabase::class.java,
            NotificationsDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNotificationsUseCases(db: NotificationsDatabase) : NotificationsUseCases {
        return NotificationsUseCases(
            getNotifications = GetNotificationsUseCase(db.dao),
            getNotification = GetNotificationUseCase(db.dao),
            upsertNotification = UpsertNotificationUseCase(db.dao),
            deleteNotification = DeleteNotificationUseCase(db.dao)
        )
    }

    @Provides
    @Singleton
    fun provideCheatSheetsDatabase(context: Application): CheatSheetsDatabase {
        return Room.databaseBuilder(
            context,
            CheatSheetsDatabase::class.java,
            CheatSheetsDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideCheatSheetsUseCases(db: CheatSheetsDatabase) : CheatSheetsUseCases {
        return CheatSheetsUseCases(
            getCheatSheets = GetCheatSheetsUseCase(db.dao),
            getCheatSheet = GetCheatSheetUseCase(db.dao),
            upsertCheatSheet = UpsertCheatSheetUseCase(db.dao),
            deleteCheatSheet = DeleteCheatSheetUseCase(db.dao)
        )
    }

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "ALTER TABLE questions ADD COLUMN correctAnswerIndex INTEGER NOT NULL DEFAULT 0"
            )
        }
    }

    @Provides
    @Singleton
    fun provideQuizDatabase(context: Application): QuizzesDatabase {
        return Room.databaseBuilder(
            context,
            QuizzesDatabase::class.java,
            QuizzesDatabase.DATABASE_NAME
        ).addMigrations(MIGRATION_1_2).build()
    }

    @Provides
    @Singleton
    fun provideQuizUseCases(db: QuizzesDatabase): QuizzesUseCases {
        return QuizzesUseCases(
            getQuizzes = GetQuizzesUseCase(db.dao),
            getQuiz = GetQuizUseCase(db.dao),
            upsertQuiz = UpsertQuizUseCase(db.dao),
            deleteQuiz = DeleteQuizUseCase(db.dao)
        )
    }
}
