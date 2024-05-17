package com.example.noto.domain.useCase

import com.example.noto.data.dto.NoteData
import com.example.noto.data.remoteRepository.NoteRepository
import kotlinx.coroutines.flow.flow

class SendNoteUseCase(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(note: NoteData) = flow{
        emit(noteRepository.sendNote(note))
    }
}