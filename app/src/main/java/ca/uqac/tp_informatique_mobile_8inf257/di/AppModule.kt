package ca.uqac.tp_informatique_mobile_8inf257.di

import android.app.Application
import androidx.room.Room
import ca.uqac.tp_informatique_mobile_8inf257.data.source.NotificationsDatabase
import ca.uqac.tp_informatique_mobile_8inf257.data.source.ToDoListDatabase
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.DeleteNotificationUseCase
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.DeleteToDoUseCase
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.GetNotificationUseCase
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.GetNotificationsUseCase
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.GetToDoListUseCase
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.GetToDoUseCase
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.NotificationsUseCases
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.ToDoListUseCases
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.UpsertNotificationUseCase
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.UpsertToDoUseCase
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
}
