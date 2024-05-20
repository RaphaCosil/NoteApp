package com.example.noto.data.service

import com.example.noto.data.dto.NoteData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
interface NoteService {
    @GET("note/getAll")
    suspend fun getAllNotes() : MutableList<NoteData>
    @GET("note/get/{noteId}")
    suspend fun getNote(@Path("noteId") noteId: String) : NoteData
    @POST("note/send")
    suspend fun sendNote(@Body note: NoteData)
    @PUT("note/update/{noteId}")
    suspend fun updateNote(@Path("noteId") noteId: String, @Body note: NoteData)
}