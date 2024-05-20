package com.example.noto.view.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.noto.data.dto.NoteData
import com.example.noto.domain.useCase.UpdateNoteUseCase
import com.example.noto.domain.useCase.GetAllNotesUseCase
import com.example.noto.domain.useCase.GetNoteUseCase
import com.example.noto.domain.useCase.SendNoteUseCase
import com.example.noto.view.activitys.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
// View Model to provide data to UI
class NoteViewModel(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val getNoteUseCase: GetNoteUseCase,
    private val sendNoteUseCase: SendNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase
): ViewModel() {
    private val ioScope = CoroutineScope(Dispatchers.IO)
    // Observing LiveData from ViewModel
    private val _noteLiveData = MutableLiveData<NoteData>()
    val noteLiveData: LiveData<NoteData> get() = _noteLiveData
    // Observing LiveData from ViewModel

    private val _noteListLiveData = MutableLiveData<List<NoteData>>()
    val noteListLiveData: LiveData<List<NoteData>> get() = _noteListLiveData

    // Functions to call use cases
    fun getAllNotes() {
        ioScope.launch {
            try {
                getAllNotesUseCase.invoke().catch {
                    it.printStackTrace()
                }.collect{
                    Log.d("Notes", it.toString())
                    Toast.makeText(MainActivity(), it.toString(), Toast.LENGTH_SHORT).show()
                    _noteListLiveData.postValue(it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun getNote(noteId: String) {
        ioScope.launch {
            try {
                getNoteUseCase.invoke(noteId).catch {
                    it.printStackTrace()
                }.collect {
                    Log.d("Notes", it.toString())
                    Toast.makeText(MainActivity(), it.toString(), Toast.LENGTH_SHORT).show()
                    _noteLiveData.postValue(it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun sendNote(note: NoteData) {
        ioScope.launch {
            try {
                sendNoteUseCase.invoke(note).catch {
                    it.printStackTrace()
                }.collect {
                    Log.d("Notes", it.toString())
                    Toast.makeText(MainActivity(), it.toString(), Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(MainActivity(), it.toString(), Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}