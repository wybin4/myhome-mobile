package com.example.myhome.meter.storages

import com.example.myhome.meter.dtos.MeterGetDto

interface MeterStorage {
    suspend fun listMeter(): List<MeterGetDto>
}