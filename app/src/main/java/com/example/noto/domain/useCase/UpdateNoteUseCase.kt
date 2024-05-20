package com.example.noto.domain.useCase

import com.example.noto.data.dto.NoteData
import com.example.noto.data.remoteRepository.NoteRepository
import kotlinx.coroutines.flow.flow

class UpdateNoteUseCase(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(noteId: String, note: NoteData) = flow{
        emit(noteRepository.updateNote(noteId, note))
    }
}