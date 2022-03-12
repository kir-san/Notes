package com.notes.ui.details

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.notes.R
import com.notes.databinding.FragmentNoteDetailsBinding
import com.notes.di.DependencyManager
import com.notes.ui._base.ViewBindingFragment
import com.notes.ui.list.lazyViewModel

class NoteDetailsFragment : ViewBindingFragment<FragmentNoteDetailsBinding>(
    FragmentNoteDetailsBinding::inflate
) {

    private val viewModel: NoteDetailsViewModel by lazyViewModel {
        DependencyManager.noteDetailsViewModel().create(it)
    }

    private val activity by lazy { (requireActivity() as AppCompatActivity) }
    private val itemId by lazy { arguments?.getLong(KEY) ?: -1 }

    override fun onViewBindingCreated(
        viewBinding: FragmentNoteDetailsBinding,
        savedInstanceState: Bundle?
    ) {
        super.onViewBindingCreated(viewBinding, savedInstanceState)

        enableUpToActionBar(viewBinding)

        viewModel.setNoteId(itemId)

        viewModel.item.observe(viewLifecycleOwner) { item ->
            if (item != null) {
                viewBinding.noteBody.setText(item.content)
                viewBinding.noteTitle.setText(item.title)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.remove_note).isVisible = itemId > 0
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
                return true
            }
            R.id.save_note -> {
                viewModel.save(
                    viewBinding?.noteTitle?.text.toString(),
                    viewBinding?.noteBody?.text.toString(),
                )
                requireActivity().onBackPressed()
                return true
            }
            R.id.remove_note -> {
                viewModel.remove()
                requireActivity().onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun enableUpToActionBar(viewBinding: FragmentNoteDetailsBinding) {
        activity.setSupportActionBar(viewBinding.toolbar)
        activity.actionBar?.setDisplayHomeAsUpEnabled(true)

        if (itemId == -1L)
            activity.title = activity.getString(R.string.create_note)
        else
            activity.title = activity.getString(R.string.edit_note)

        setHasOptionsMenu(true)
    }

    companion object {
        const val KEY = "NoteDetailsItemKey"
    }
}
