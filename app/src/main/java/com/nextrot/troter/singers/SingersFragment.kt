package com.nextrot.troter.singers

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nextrot.troter.songs.SongsViewModel
import com.nextrot.troter.data.Singer
import com.nextrot.troter.databinding.SingersFragmentBinding
import com.nextrot.troter.songs.SongsActivity
import org.koin.android.ext.android.inject

class SingersFragment : Fragment() {
    private val songsViewModel: SongsViewModel by inject()
    private lateinit var singersFragmentBinding: SingersFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        singersFragmentBinding = SingersFragmentBinding.inflate(inflater, container, false).apply {
            viewmodel = songsViewModel
        }
        songsViewModel.getSingers()
        return singersFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        singersFragmentBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListView()
    }

    private fun setupListView() {
        val viewmodel = singersFragmentBinding.viewmodel
        if (viewmodel != null) {
            singersFragmentBinding.list.adapter = SingersListAdapter(viewmodel, this)
        }
    }

    fun onClickItem(singer: Singer) {
        val intent = Intent(context, SongsActivity::class.java).apply {
            putExtra(SongsActivity.BUNDLE_SONGS_TITLE, singer.name)
            putExtra(SongsActivity.BUNDLE_SINGER_ID, singer.id)
        }
        startActivity(intent)
    }
}