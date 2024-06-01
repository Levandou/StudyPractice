package com.velagissellint.domain.useCases.task

import com.velagissellint.domain.models.diplom.Task
import com.velagissellint.domain.network.RequestResult
import kotlinx.coroutines.flow.MutableStateFlow

interface TaskRepository {
    fun getTaskList(profileId: String): MutableStateFlow<RequestResult<List<Task>>>
    fun getTask(taskId: Int): MutableStateFlow<RequestResult<Task>>
}