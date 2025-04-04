package ca.uqac.tp_informatique_mobile_8inf257.presentation.notifications

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.GetNotificationsUseCase
import ca.uqac.tp_informatique_mobile_8inf257.domain.usecases.NotificationsUseCases
import ca.uqac.tp_informatique_mobile_8inf257.presentation.NotificationVM
import ca.uqac.tp_informatique_mobile_8inf257.presentation.ToDoVM
import ca.uqac.tp_informatique_mobile_8inf257.presentation.toEntity
import ca.uqac.tp_informatique_mobile_8inf257.utils.addNotification
import ca.uqac.tp_informatique_mobile_8inf257.utils.getReminders
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationScreenViewModel @Inject constructor
    (private val notificationsUseCases: NotificationsUseCases) : ViewModel() {
    private val _notificationsList = mutableStateOf<List<NotificationVM>>(emptyList())
    val notificationsList: State<List<NotificationVM>> = _notificationsList

    var job: Job? = null

    init {
        loadNotifications()
    }

    private fun loadNotifications(){
        job?.cancel()

        job = notificationsUseCases.getNotifications().onEach { notifications ->
            _notificationsList.value = notifications.map {
                NotificationVM.fromEntity(it)
            }
        }.launchIn(viewModelScope)
    }


    fun onEvent(event: NotificationsEvent) {
        when (event) {
            is NotificationsEvent.ChangeIsActive -> {
                activateNotification(event.notification)
            }

            is NotificationsEvent.Modify -> TODO()
            is NotificationsEvent.AddNotification -> {
                addNotification(event.notification)
            }
        }
    }

    private fun addNotification(notification: NotificationVM) {
        viewModelScope.launch {
            notificationsUseCases.upsertNotification(notification.toEntity())
        }
    }

    private fun activateNotification(notification: NotificationVM) {
        notification.active.value = !notification.active.value
        viewModelScope.launch {
            notificationsUseCases.upsertNotification(notification.toEntity())
        }
    }

}