package com.notes.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.notes.databinding.ActivityRootBinding
import com.notes.ui._base.FragmentNavigator
import com.notes.ui.list.NoteListFragment

class RootActivity : AppCompatActivity(), FragmentNavigator {

    private val viewBinding by lazy {
        ActivityRootBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(viewBinding.root)

        navigateTo(NoteListFragment())
    }

    override fun navigateTo(
        fragment: Fragment
    ) {
        supportFragmentManager.commit {
            replace(viewBinding.container.id, fragment)
            addToBackStack(fragment.javaClass.name)
            setReorderingAllowed(true)
        }
    }

}
