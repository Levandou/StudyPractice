package com.velagissellint.presentation.diplom.task_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velagissellint.domain.models.diplom.Profile
import com.velagissellint.domain.models.diplom.Task
import com.velagissellint.domain.network.RequestResult
import com.velagissellint.domain.useCases.chart.ChartUseCase
import com.velagissellint.domain.useCases.statement.StatementUseCase
import com.velagissellint.domain.useCases.task.TaskUseCase
import com.velagissellint.domain.useCases.user.ProfileUseCase
import com.velagissellint.domain.useCases.user.UserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class TaskListViewModel(
    private val userUseCase: UserUseCase,
    private val profileUseCase: ProfileUseCase,
    private val taskUseCase: TaskUseCase
) : ViewModel() {
    private val profileId: String by lazy {
        userUseCase.getUser()?.id ?: throw Throwable("ProfileId is Null")
    }
    val taskListFlow = MutableStateFlow<List<Task>>(emptyList())

    private val _taskListWithEmptyNames = MutableStateFlow<List<Task>>(emptyList())

    private val _taskListWithNames = MutableStateFlow<RequestResult<List<Task>>>(RequestResult.Loading)
    val taskListWithNames: MutableStateFlow<RequestResult<List<Task>>> = _taskListWithNames

    fun getTaskLisdasda() {
        viewModelScope.launch(Dispatchers.IO) {
            val result: MutableStateFlow<RequestResult<List<Task>>> =
                taskUseCase.getTaskList(profileId)
            result.collectLatest { listTaskResult ->
                if (listTaskResult !is RequestResult.Success) return@collectLatest

                taskListFlow.value = listTaskResult.data
            }
        }
    }

    fun getWithNames(listTask: List<Task>){
        viewModelScope.launch(Dispatchers.IO) {
            val taskWithNamesList = mutableListOf<Task>()
            listTask.forEach { task ->
                taskWithNamesList.add(task)
            }
            _taskListWithEmptyNames.value = taskWithNamesList
            taskWithNamesList.forEachIndexed { index, taskWithNames ->
                combine(
                    profileUseCase.getProfileName(taskWithNames.authorId),
                    profileUseCase.getProfileName(taskWithNames.assignedId),
                    profileUseCase.getProfileName(taskWithNames.reviewerId)
                ) { authorResult, assignedResult, reviewerResult ->
                    if (authorResult is RequestResult.Success &&
                        assignedResult is RequestResult.Success &&
                        reviewerResult is RequestResult.Success
                    ) {
                        taskWithNamesList[index] = taskWithNames.copy(
                            authorName = authorResult.data.second,
                            assignedName = assignedResult.data.second,
                            reviewerName = reviewerResult.data.second
                        )
                        if (index == taskWithNamesList.lastIndex) {
                            _taskListWithNames.value = RequestResult.Success(taskWithNamesList)
                        }
                    }
                }.collect{

                }
            }
        }
    }
}