package com.velagissellint.domain.models

data class FoldingRooms(
    var name: String,
    var volume: Int
) {
    constructor() : this("", 0)
}

