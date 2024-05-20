package com.example.noto.data.remoteRepository

import com.example.noto.data.service.NoteService
import com.example.noto.data.dto.NoteData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
class NoteRepositoryImpl(private val noteService: NoteService): NoteRepository {
     override suspend fun getNotes(): Flow<MutableList<NoteData>> = flow {
         emit(noteService.getAllNotes())
     }
    override suspend fun getNote(noteId: String): Flow<NoteData> = flow {
        emit(noteService.getNote(noteId))
    }
    override suspend fun sendNote(note: NoteData) {
        noteService.sendNote(note)
    }
    override suspend fun updateNote(noteId: String, note: NoteData) {
        noteService.updateNote(noteId, note)
    }
}