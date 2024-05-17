package com.example.noto.domain.useCase

import com.example.noto.data.dto.NoteData
import com.example.noto.data.remoteRepository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllNotesUseCase(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(): Flow<List<NoteData>> = flow {
        noteRepository.getNotes().collect { emit(it) }
    }

}