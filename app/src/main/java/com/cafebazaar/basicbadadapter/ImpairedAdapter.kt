package com.cafebazaar.basicbadadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ImpairedAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val listOfAudios = mutableListOf<Audio>()
    private val listOfVideos = mutableListOf<Video>()

    private fun fetchVideo(pos: Int) = listOfVideos[mapOfPositions[pos]!!]
    private fun fetchAudio(pos: Int) = listOfAudios[mapOfPositions[pos]!!]

    private val mapOfPositions = hashMapOf<Int, Int>()
    private val mAdapterData: ListWithCallback<AdapterBindingItem<*>> =
        ListWithCallback { it, pos ->
            mItemViewHoldersMap[it.viewType] = AdapterViewHolderCreator()
        }

    val adapterData: ListWithCallback<Media> = ListWithCallback { it, pos ->
        mAdapterData.add(
            when (it) {
                is Video -> {
                    listOfVideos.add(it)
                    mapOfPositions[pos] = listOfVideos.size-1
                    VideoItem(::fetchVideo)
                }
                is Audio -> {
                    listOfAudios.add(it)
                    mapOfPositions[pos] = listOfAudios.size-1
                    AudioItem(::fetchAudio)
                }
            }
        )
    }

    private val mItemViewHoldersMap: HashMap<Int, AdapterViewHolderCreator> = hashMapOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        mItemViewHoldersMap[viewType]!!.onCreateViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        mAdapterData[position].bind(holder, position) // no cast
    }

    override fun getItemCount() = mAdapterData.size
    override fun getItemViewType(position: Int) = mAdapterData[position].viewType
}

class AdapterViewHolderCreator {
    fun onCreateViewHolder(parent: ViewGroup, viewResId: Int): RecyclerView.ViewHolder =
        object : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(viewResId, parent, false)
        ) {}
}

abstract class AdapterBindingItem<T>(val viewType: Int) {
    abstract val binding: (Int) -> T
    abstract fun bind(holder: RecyclerView.ViewHolder, position: Int)
}


class ListWithCallback<T>(val onAddCall: (T, Int) -> Unit) : ArrayList<T>() {
    override fun add(element: T): Boolean {
        onAddCall(element, this.size)
        return super.add(element)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        elements.forEachIndexed { index, it ->
            onAddCall(it, index)
        }
        return super.addAll(elements)
    }

}


// __________________________________________ Adapter Items

class VideoItem(override val binding: (Int) -> Video) : AdapterBindingItem<Video>(
    R.layout.video_item
) {
    override fun bind(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.text).text =
            "video url is ${binding(position).videoUrl}"
    }
}

class AudioItem(override val binding: (Int) -> Audio) :
    AdapterBindingItem<Audio>(R.layout.audio_item) {
    override fun bind(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.text).text =
            "Audio url is ${binding(position).audioUrl}"
    }
}

// __________________________________________ DTOs

sealed class Media
data class Video(val videoUrl: String) : Media()
data class Audio(val audioUrl: String) : Media()
