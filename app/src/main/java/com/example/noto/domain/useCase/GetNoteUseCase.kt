package com.example.noto.domain.useCase

import com.example.noto.data.dto.NoteData
import com.example.noto.data.remoteRepository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNoteUseCase(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(noteId: String): Flow<NoteData> = flow {
        noteRepository.getNote(noteId).collect { emit(it) }
    }
}