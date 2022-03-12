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

        if (savedInstanceState == null)
            navigateTo(NoteListFragment::class.java)
    }

    override fun navigateTo(
        fragment: Class<out Fragment>, arguments: Bundle?
    ) {
        supportFragmentManager.commit {
            replace(viewBinding.container.id, fragment, arguments)
            addToBackStack(fragment.javaClass.name)
            setReorderingAllowed(true)
        }
    }

}
