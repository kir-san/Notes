package com.notes.ui.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.notes.data.NoteDatabase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.map

class NoteListViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    noteDatabase: NoteDatabase
) : ViewModel() {
    val notes = noteDatabase
        .noteDao()
        .getAll()
        .map { items ->
            items.map { item ->
                NoteListItem(
                    id = item.id,
                    title = item.title,
                    content = item.content,
                )
            }
        }

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): NoteListViewModel
    }
}

data class NoteListItem(
    val id: Long,
    val title: String,
    val content: String,
)
