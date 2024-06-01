package com.velagissellint.domain.useCases.task

class TaskUseCase(private val taskRepository: TaskRepository) {
    fun getTaskList(profileId: String) = taskRepository.getTaskList(profileId)

    fun getTask(taskId: Int) = taskRepository.getTask(taskId)
}