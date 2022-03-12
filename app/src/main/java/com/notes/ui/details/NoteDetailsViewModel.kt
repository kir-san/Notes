package com.notes.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notes.data.NoteDatabase
import com.notes.data.NoteDbo
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class NoteDetailsViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val noteDatabase: NoteDatabase
) : ViewModel() {

    private val _item = MutableLiveData(NoteDbo())
    val item: LiveData<NoteDbo> = _item

    fun setNoteId(id: Long) = viewModelScope.launch(Dispatchers.IO) {
        if (id > 0) {
            _item.postValue(
                noteDatabase
                    .noteDao()
                    .getItemById(id)
            )
        }
    }

    fun save(
        title: String,
        content: String,
    ) = viewModelScope.launch(Dispatchers.IO) {
        item.value?.let { currentNote ->
            val notes = currentNote.copy(
                title = title,
                content = content,
                modifiedAt = LocalDateTime.now()
            )
            if (currentNote.id > 0) {
                noteDatabase.noteDao().updateAll(notes)
            } else {
                noteDatabase.noteDao().insertAll(notes)
            }
        }
    }

    fun remove() = viewModelScope.launch(Dispatchers.IO) {
        item.value?.let { currentNote ->
            noteDatabase.noteDao().deleteAll(currentNote)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): NoteDetailsViewModel
    }
}
