package com.notes.ui.details

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.notes.databinding.FragmentNoteDetailsBinding
import com.notes.ui._base.ViewBindingFragment

class NoteDetailsFragment : ViewBindingFragment<FragmentNoteDetailsBinding>(
    FragmentNoteDetailsBinding::inflate
) {
    private val activity by lazy { (requireActivity() as AppCompatActivity) }

    override fun onViewBindingCreated(
        viewBinding: FragmentNoteDetailsBinding,
        savedInstanceState: Bundle?
    ) {
        super.onViewBindingCreated(viewBinding, savedInstanceState)

        enableUpToActionBar(viewBinding)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun enableUpToActionBar(viewBinding: FragmentNoteDetailsBinding) {
        activity.setSupportActionBar(viewBinding.toolbar)
        activity.actionBar?.setDisplayHomeAsUpEnabled(true)

        setHasOptionsMenu(true)
    }
}
