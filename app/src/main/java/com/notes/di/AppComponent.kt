package com.notes.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.notes.data.NoteDatabase
import com.notes.ui.list.ViewModelFactory
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
        ): AppComponent
    }

    fun getNoteDatabase(): NoteDatabase

    fun viewModelsFactory(): ViewModelFactory

}

@Module(
    includes = [
        AppModule.Binding::class
    ]
)
class AppModule {

    @Provides
    fun provideNoteDatabase(
        context: Context
    ) = Room.databaseBuilder(
        context,
        NoteDatabase::class.java, "database-note.db"
    ).createFromAsset("database-note.db")
        .build()

    @Module
    interface Binding {

        @Binds
        fun bindContext(application: Application): Context

    }

}
