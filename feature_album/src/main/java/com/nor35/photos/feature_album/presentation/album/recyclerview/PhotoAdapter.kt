package com.nor35.photos.feature_album.presentation.album.recyclerview

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.nor35.photos.domain.Constants.NUMBER_OF_COLUMNS
import com.nor35.photos.feature_album.R
import com.nor35.photos.feature_album.databinding.PhotoItemBinding
import com.nor35.photos.feature_album.domain.model.Photo
import javax.inject.Inject

class PhotoAdapter @Inject constructor(): RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    val photos: ArrayList<Photo> = arrayListOf()

    private var onClickListener: ((photoId: Long) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PhotoItemBinding.inflate(inflater, parent, false)

        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = photos[position]
        holder.itemView.setOnClickListener{
            onClickListener?.invoke(photo.id)
        }
        holder.bind(photo)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    fun addPhotos(list: List<Photo>){
        val oldListSize = photos.size
        photos.addAll(list)
        if(oldListSize == 0)
            notifyItemRangeInserted(0, list.size)
        else
            notifyItemRangeInserted(photos.size - list.size, list.size)
    }

    fun deletePhotos(){
        val oldListSize = photos.size
        photos.clear()
        notifyItemRangeRemoved(0, oldListSize)
    }

    fun setOnClickListener(listener: (photoId: Long) -> Unit) {
        this.onClickListener = listener
    }

    inner class PhotoViewHolder(private val photoItemBinding: PhotoItemBinding):
            RecyclerView.ViewHolder(photoItemBinding.root) {

                fun bind(photo: Photo){

                    val params = this.itemView.layoutParams
                    params.width = getPhotoWidth()
                    this.itemView.layoutParams = params

                    if(photo.imageUrl.isEmpty()) {
                        photoItemBinding.photoImageview.visibility = View.GONE
                        photoItemBinding.coverErrorImageView.visibility = View.VISIBLE
                    }
                    else {
                        photoItemBinding.coverErrorImageView.visibility = View.GONE
                        photoItemBinding.photoImageview.visibility = View.VISIBLE
                        photoItemBinding.photoImageview.load(photo.imageUrl) {
                            crossfade(true)
                            error(R.drawable.ic_stub_image)
                            scale(Scale.FILL)
                        }
                    }
                }

                private fun getPhotoWidth(): Int {

                    val screenWidth = Resources.getSystem().displayMetrics.widthPixels
                    return  screenWidth / NUMBER_OF_COLUMNS
                }
            }
}