package com.nor35.photos.feature_album.presentation.album_list.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nor35.photos.feature_album.R
import com.nor35.photos.feature_album.databinding.PhotoItemBinding
import com.nor35.photos.feature_album.domain.model.Photo
import javax.inject.Inject

class PhotoAdapter @Inject constructor():RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    private val photos: ArrayList<Photo> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PhotoItemBinding.inflate(inflater, parent, false)

        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    fun addPhotos(list: List<Photo>){
        photos.addAll(list)
        notifyDataSetChanged()
//        notifyItemChanged()
    }

    fun addPhotos(photo: Photo){
        photos.add(photo)
        notifyDataSetChanged()
//        notifyItemChanged()
    }

    class PhotoViewHolder(private val photoItemBinding: PhotoItemBinding):
            RecyclerView.ViewHolder(photoItemBinding.root) {

                fun bind(photo: Photo){

                    if(photo.imageUrl.isEmpty())
                        photoItemBinding.coverErrorImageView.visibility = View.VISIBLE
                    else {
                        photoItemBinding.coverErrorImageView.visibility = View.GONE
                        photoItemBinding.photoImageview.visibility = View.VISIBLE
                        photoItemBinding.photoImageview.load(photo.imageUrl) {
                            crossfade(true)
                            error(R.drawable.ic_stub_image)
                        }
                    }
                }
            }
}