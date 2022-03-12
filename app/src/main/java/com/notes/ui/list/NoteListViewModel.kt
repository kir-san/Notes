package com.notes.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.notes.data.NoteDatabase
import javax.inject.Inject

class NoteListViewModel @Inject constructor(
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
}

data class NoteListItem(
    val id: Long,
    val title: String,
    val content: String,
)
