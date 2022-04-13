package com.nor35.photos.feature_album.presentation.album.recyclerview

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.nor35.photos.domain.Constants.NUMBER_OF_COLUMNS
import com.nor35.photos.domain.Constants.NUMBER_OF_ROWS
import com.nor35.photos.feature_album.R
import com.nor35.photos.feature_album.databinding.PhotoItemBinding
import com.nor35.photos.feature_album.domain.model.Photo
import timber.log.Timber
import javax.inject.Inject

class PhotoAdapter @Inject constructor() : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    val photos: ArrayList<Photo> = arrayListOf()

    private var onClickListener: ((photoId: Long) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PhotoItemBinding.inflate(inflater, parent, false)

        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = photos[position]
        holder.itemView.setOnClickListener {
            onClickListener?.invoke(photo.id)
        }

        holder.bind(photo)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    fun addPhotos(list: List<Photo>) {
        val oldListSize = photos.size
        photos.addAll(list)
        if (oldListSize == 0)
            notifyItemRangeInserted(0, list.size)
        else
            notifyItemRangeInserted(photos.size - list.size, list.size)
    }

    fun deletePhotos() {
        val oldListSize = photos.size
        photos.clear()
        notifyItemRangeRemoved(0, oldListSize)
    }

    fun setOnClickListener(listener: (photoId: Long) -> Unit) {
        this.onClickListener = listener
    }

    inner class PhotoViewHolder(private val photoItemBinding: PhotoItemBinding) :
        RecyclerView.ViewHolder(photoItemBinding.root) {

        fun bind(photo: Photo) {
            setPhotoLayoutParams()
            setPhoto(photo)
        }

        private fun setPhotoLayoutParams() {
            val photoLength = PhotoLayoutParams.getWidth(
                this.itemView.context.resources.configuration.orientation
            )
            val params = this.itemView.layoutParams

            Timber.d("displayMetrics width = ${Resources.getSystem().displayMetrics.widthPixels}")
            Timber.d("displayMetrics height = ${Resources.getSystem().displayMetrics.heightPixels}")
            Timber.d("displayMetrics new photoItemBinding height and width = $photoLength")

            params.width = photoLength
            params.height = photoLength
            this.itemView.layoutParams = params
        }

        private fun setPhoto(photo: Photo) {
            if (photo.imageUrl.isEmpty()) {
                photoItemBinding.photoImageview.visibility = View.GONE
                photoItemBinding.coverErrorImageView.visibility = View.VISIBLE
            } else {
                photoItemBinding.coverErrorImageView.visibility = View.GONE
                photoItemBinding.photoImageview.visibility = View.VISIBLE
                photoItemBinding.photoImageview.load(photo.imageUrl) {
                    crossfade(true)
                    error(R.drawable.ic_stub_image)
                    scale(Scale.FILL)
                }
            }
        }
    }

    fun resetPhotoLayoutParams() {
        PhotoLayoutParams.photoLength = 0
    }

    private object PhotoLayoutParams {

        var photoLength: Int = 0

        fun getWidth(orientation: Int): Int {

            if (photoLength == 0) {
                photoLength = if (orientation == LinearLayoutManager.VERTICAL) {
                    ((Resources.getSystem().displayMetrics.widthPixels) / NUMBER_OF_COLUMNS) - 2
                } else {
                    (Resources.getSystem().displayMetrics.heightPixels / NUMBER_OF_ROWS)
                }
            }

            return photoLength
        }
    }
}
