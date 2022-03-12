package com.notes.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.notes.databinding.FragmentNoteListBinding
import com.notes.databinding.ListItemNoteBinding
import com.notes.di.DependencyManager
import com.notes.ui._base.FragmentNavigator
import com.notes.ui._base.ViewBindingFragment
import com.notes.ui._base.findImplementationOrThrow
import com.notes.ui.details.NoteDetailsFragment

class NoteListFragment : ViewBindingFragment<FragmentNoteListBinding>(
    FragmentNoteListBinding::inflate
) {

    private val viewModel: NoteListViewModel by viewModels { DependencyManager.noteViewModelFactory() }

    private val recyclerViewAdapter = RecyclerViewAdapter { itemId ->
        val arguments = bundleOf(NoteDetailsFragment.KEY to itemId)
        findImplementationOrThrow<FragmentNavigator>()
            .navigateTo(NoteDetailsFragment::class.java, arguments)
    }

    override fun onViewBindingCreated(
        viewBinding: FragmentNoteListBinding,
        savedInstanceState: Bundle?
    ) {
        super.onViewBindingCreated(viewBinding, savedInstanceState)

        viewBinding.list.adapter = recyclerViewAdapter
        viewBinding.list.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayout.VERTICAL
            )
        )
        viewBinding.createNoteButton.setOnClickListener {
            findImplementationOrThrow<FragmentNavigator>()
                .navigateTo(NoteDetailsFragment::class.java)
        }

        viewModel.notes.observe(viewLifecycleOwner) {
            Log.v("observe", "$it")
            if (it != null) {
                recyclerViewAdapter.setItems(it)
            }
        }
    }

    private class RecyclerViewAdapter(
        private val onItemClick: (Long) -> Unit,
    ) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
        private val differ = AsyncListDiffer(this, DiffCallback())

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ) = ViewHolder(
            ListItemNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClick
        )

        override fun onBindViewHolder(
            holder: ViewHolder,
            position: Int
        ) {
            holder.bind( differ.currentList[position])
        }

        override fun getItemCount() = differ.currentList.count()

        fun setItems(
            items: List<NoteListItem>
        ) {
            differ.submitList(items)
        }

        private class ViewHolder(
            private val binding: ListItemNoteBinding,
            private val onItemClick: (Long) -> Unit,
        ) : RecyclerView.ViewHolder(
            binding.root
        ) {

            fun bind(
                note: NoteListItem
            ) {
                binding.root.setOnClickListener {
                    onItemClick(note.id)
                }
                binding.titleLabel.text = note.title
                binding.contentLabel.text = note.content
            }

        }

        class DiffCallback : DiffUtil.ItemCallback<NoteListItem>() {
            override fun areItemsTheSame(oldItem: NoteListItem, newItem: NoteListItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NoteListItem, newItem: NoteListItem): Boolean {
                return oldItem == newItem
            }
        }

    }

}


