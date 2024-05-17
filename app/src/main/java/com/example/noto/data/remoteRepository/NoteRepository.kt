package com.example.noto.data.remoteRepository

import com.example.noto.data.dto.NoteData
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun getNotes(): Flow<List<NoteData>>
    suspend fun getNote(noteId: String): Flow<NoteData>
    suspend fun sendNote(note: NoteData)
    suspend fun updateNote(noteId: String, note: NoteData)
}