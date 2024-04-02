package com.example.myhome.features.appeal.repositories

import android.graphics.Bitmap

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myhome.features.appeal.AppealAddMeterAddRequest
import com.example.myhome.features.appeal.AppealAddRequest
import com.example.myhome.features.appeal.AppealApiService
import com.example.myhome.features.appeal.AppealClaimAddRequest
import com.example.myhome.features.appeal.AppealListItemResponse
import com.example.myhome.features.appeal.AppealPagingSourceFactory
import com.example.myhome.features.appeal.AppealProblemAddRequest
import com.example.myhome.features.appeal.AppealVerifyMeterAddRequest
import com.example.myhome.models.FilterListItemRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream

class AppealRepositoryImpl(
    private val appealApiService: AppealApiService,
    private val appealPagingSourceFactory: AppealPagingSourceFactory,
    private val pageConfig: PagingConfig
): AppealRepository {
    private fun createPartFromString(stringData: String): RequestBody {
        return RequestBody.create(MediaType.parse("text/plain"), stringData)
    }

    private fun Bitmap.toByteArray(): ByteArray {
        val outputStream = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    private fun createFilePart(file: Bitmap): MultipartBody.Part {
        val imageBytes = file.toByteArray()
        val fileRequestBody = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes)
        return MultipartBody.Part.createFormData("file", "attachment.jpg", fileRequestBody)
    }

    private fun addValuesToMap(appeal: AppealAddRequest, map: MutableMap<String, RequestBody>) {
        map["managementCompanyId"] = createPartFromString(appeal.managementCompanyId.toString())
        map["subscriberId"] = createPartFromString(appeal.subscriberId.toString())
        map["typeOfAppeal"] = createPartFromString(appeal.typeOfAppeal.toString())
    }

    override fun addMeter(appeal: AppealAddMeterAddRequest, file: Bitmap): Flow<Boolean> = flow {
        val filePart = createFilePart(file)

        val map: MutableMap<String, RequestBody> = mutableMapOf()
        addValuesToMap(appeal, map)
        map["typeOfServiceId"] = createPartFromString(appeal.data.typeOfServiceId.toString())
        map["apartmentId"] = createPartFromString(appeal.data.apartmentId.toString())
        map["factoryNumber"] = createPartFromString(appeal.data.factoryNumber)
        map["issuedAt"] = createPartFromString(appeal.data.issuedAt)
        map["verifiedAt"] = createPartFromString(appeal.data.verifiedAt)

        appealApiService.addAppealWithFile(filePart, map)
        emit(true)
    }

    override fun updateMeter(appeal: AppealVerifyMeterAddRequest, file: Bitmap): Flow<Boolean> = flow {
        val filePart = createFilePart(file)

        val map: MutableMap<String, RequestBody> = mutableMapOf()
        addValuesToMap(appeal, map)
        map["meterId"] = createPartFromString(appeal.data.meterId.toString())
        map["issuedAt"] = createPartFromString(appeal.data.issuedAt)
        map["verifiedAt"] = createPartFromString(appeal.data.verifiedAt)

        appealApiService.addAppealWithFile(filePart, map)
        emit(true)
    }

    override fun problem(appeal: AppealProblemAddRequest): Flow<Boolean> = flow {
        val map: MutableMap<String, RequestBody> = mutableMapOf()
        addValuesToMap(appeal, map)
        map["text"] = createPartFromString(appeal.data.text)

        appealApiService.addAppeal(map)
        emit(true)
    }

    override fun claim(appeal: AppealClaimAddRequest): Flow<Boolean> = flow {
        val map: MutableMap<String, RequestBody> = mutableMapOf()
        map["text"] = createPartFromString(appeal.data.text)

        appealApiService.addAppeal(map)
        emit(true)
    }

    override fun listAppeal(filters: List<FilterListItemRequest>?): Flow<PagingData<AppealListItemResponse>> {
        return Pager(
            config = pageConfig,
            pagingSourceFactory = { appealPagingSourceFactory.create(filters) }
        ).flow
    }

}