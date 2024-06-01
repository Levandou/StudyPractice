package com.velagissellint.domain.useCases.chart

import com.velagissellint.domain.models.diplom.Chart
import com.velagissellint.domain.network.RequestResult
import kotlinx.coroutines.flow.MutableStateFlow

interface ChartRepository {
    fun createChart(
        profileId: String,
        mo: Int,
        tu: Int,
        we: Int,
        th: Int,
        fr: Int
    )

    fun getCurrentChart(profileId: String): MutableStateFlow<RequestResult<Chart>>

    fun getNextChart(profileId: String): MutableStateFlow<RequestResult<Chart>>

    fun isNextChartSelected(profileId: String): MutableStateFlow<RequestResult<Boolean>>
}