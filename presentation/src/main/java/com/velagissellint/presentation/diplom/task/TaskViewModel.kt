package com.velagissellint.presentation.diplom.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velagissellint.domain.models.diplom.Profile
import com.velagissellint.domain.models.diplom.Task
import com.velagissellint.domain.network.RequestResult
import com.velagissellint.domain.useCases.task.TaskUseCase
import com.velagissellint.domain.useCases.user.ProfileUseCase
import com.velagissellint.domain.useCases.user.UserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class TaskViewModel(
    private val userUseCase: UserUseCase,
    private val profileUseCase: ProfileUseCase,
    private val taskUseCase: TaskUseCase
) : ViewModel() {
    private val profileId: String by lazy {
        userUseCase.getUser()?.id ?: throw Throwable("ProfileId is Null")
    }
    val taskFlow = MutableStateFlow<Task?>(null)

    private val _taskWithNames = MutableStateFlow<RequestResult<Task>>(RequestResult.Loading)
    val taskWithNames: MutableStateFlow<RequestResult<Task>> = _taskWithNames

    fun getTask(taskId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result: MutableStateFlow<RequestResult<Task>> = taskUseCase.getTask(taskId)
            result.collectLatest { listTaskResult ->
                if (listTaskResult !is RequestResult.Success) return@collectLatest

                taskFlow.value = listTaskResult.data
            }
        }
    }

    fun getWithNames(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            var newTask = task
            combine(
                profileUseCase.getProfileName(task.authorId),
                profileUseCase.getProfileName(task.assignedId),
                profileUseCase.getProfileName(task.reviewerId)
            ) { authorResult, assignedResult, reviewerResult ->
                if (authorResult is RequestResult.Success &&
                    assignedResult is RequestResult.Success &&
                    reviewerResult is RequestResult.Success
                ) {
                    newTask = task.copy(
                        authorName = authorResult.data.second,
                        assignedName = assignedResult.data.second,
                        reviewerName = reviewerResult.data.second
                    )
                    _taskWithNames.value = RequestResult.Success(newTask)
                }
            }.collect {

            }
        }
    }
}