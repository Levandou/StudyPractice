package com.velagissellint.domain.useCases.chart

class ChartUseCase(private val chartRepository: ChartRepository) {
    fun saveChart(
        profileId: String,
        argumentMo: Int,
        argumentTu: Int,
        argumentWe: Int,
        argumentTh: Int,
        argumentFr: Int
    ) = chartRepository.createChart(
        profileId,
        argumentMo,
        argumentTu,
        argumentWe,
        argumentTh,
        argumentFr
    )

    fun getNextChart(profileId: String) = chartRepository.getNextChart(profileId)

    fun getCurrentChart(profileId: String) = chartRepository.getCurrentChart(profileId)
}