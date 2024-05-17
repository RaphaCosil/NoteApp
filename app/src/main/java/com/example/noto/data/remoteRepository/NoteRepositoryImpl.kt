package com.example.noto.data.remoteRepository

import com.example.noto.RetrofitClient
import com.example.noto.data.api.NoteService
import com.example.noto.data.dto.NoteData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
class NoteRepositoryImpl: NoteRepository {
     override suspend fun getNotes(): Flow<List<NoteData>> = flow{
         val retrofitClient = RetrofitClient.getRetrofitInstance("http:")
         val service = retrofitClient.create(NoteService::class.java)
         val response = service.getAllNotes()
         emit(response)
     }
    override suspend fun getNote(noteId: String): Flow<NoteData> = flow {
        val retrofitClient = RetrofitClient.getRetrofitInstance("http:")
        val service = retrofitClient.create(NoteService::class.java)
        val response = service.getNote(noteId)
        emit(response)
    }
    override suspend fun sendNote(note: NoteData) {
        val retrofitClient = RetrofitClient.getRetrofitInstance("http:")
        val service = retrofitClient.create(NoteService::class.java)
        service.sendNote(note)
    }
    override suspend fun updateNote(noteId: String, note: NoteData) {
        val retrofitClient = RetrofitClient.getRetrofitInstance("http:")
        val service = retrofitClient.create(NoteService::class.java)
        service.updateNote(noteId, note)
    }
}