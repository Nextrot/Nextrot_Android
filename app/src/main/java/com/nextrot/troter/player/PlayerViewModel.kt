package com.nextrot.troter.songs

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextrot.troter.data.Song
import com.nextrot.troter.data.VideoRepository
import kotlinx.coroutines.launch

class PlayerViewModel(private val repo: VideoRepository): ViewModel() {
    val isRandom = MutableLiveData(false)
    val isRepeat = MutableLiveData(false)
    val currentlyPlayingSong = MutableLiveData<Song>()
    val currentlyPlayingIndex = MutableLiveData(0)
    val songsCount = MutableLiveData(0)
    val lyricsOfCurrentSong = MutableLiveData("")
    val lyricsViewScale = MutableLiveData(1)
    lateinit var songs: ArrayList<Song>

    fun savePlaylist(songs: ArrayList<Song>) {
        this.songs = songs
        this.songsCount.value = songs.size
        viewModelScope.launch {
            try {
                repo.savePlaylist(songs)
            } catch (e: Exception) {
                Log.e("Troter", "Something went wrong : $e")
            }
        }
    }

    fun toggleIsRandom() {
        isRandom.value = !isRandom.value!!
    }

    fun toggleIsRepeat() {
        isRepeat.value = !isRepeat.value!!
    }

    fun setCurrentlyPlayingSong(videoId: String) {
        val currentSong = this.songs.find { song -> song.video[0].key == videoId }
        currentlyPlayingSong.value = currentSong
        currentlyPlayingIndex.value = songs.indexOf(currentSong) + 1
        lyricsOfCurrentSong.value = currentSong!!.lyrics
    }

    fun isPlaying(item: Song): Boolean {
        if (currentlyPlayingSong.value?.video.isNullOrEmpty()) {
            return false
        }

        return currentlyPlayingSong.value?.video!![0].key == item.video[0].key
    }

    fun changeLyricsViewScale() {
        val nextScale = if (lyricsViewScale.value == 1) {
            2
        } else if (lyricsViewScale.value == 2) {
            4
        } else {
            1
        }

        lyricsViewScale.value = nextScale
    }

}