package com.example.noto

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.noto.data.dto.NoteData
import com.example.noto.domain.useCase.UpdateNoteUseCase
import com.example.noto.domain.useCase.GetAllNotesUseCase
import com.example.noto.domain.useCase.GetNoteUseCase
import com.example.noto.domain.useCase.SendNoteUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class NoteViewModel(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val getNoteUseCase: GetNoteUseCase,
    private val sendNoteUseCase: SendNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase
): ViewModel() {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    fun getAllNotes() {
        ioScope.launch {
            try {
                getAllNotesUseCase.invoke().catch {
                    it.printStackTrace()
                    return@catch
                }.collect{
                    Log.d("Notes", it.toString())
                    return@collect
                }

            } catch (e: Exception) {
                e.printStackTrace()
                return@launch
            }
        }
    }
    fun getNote(noteId: String): NoteData {
        ioScope.launch {
            try {
                getNoteUseCase.invoke(noteId).catch {
                    it.printStackTrace()
                    return@catch
                }.collect {
                    Log.d("Notes", it.toString())
                    return@collect
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return@launch
            }
        }
        return NoteData(0, "", "")
    }
    fun sendNote(note: NoteData) {
        ioScope.launch {
            try {
                sendNoteUseCase.invoke(note).catch {
                    it.printStackTrace()
                }.collect {
                    Log.d("Notes", it.toString())

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun updateNote(noteId: String, note: NoteData) {
        ioScope.launch {
            try {
                updateNoteUseCase.invoke(noteId, note).catch {
                    it.printStackTrace()
                }.collect {
                    Log.d("Notes", it.toString())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}