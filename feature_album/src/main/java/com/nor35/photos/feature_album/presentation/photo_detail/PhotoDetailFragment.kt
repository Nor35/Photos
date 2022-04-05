package com.nor35.photos.feature_album.presentation.photo_detail

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import coil.load
import com.nor35.photos.feature_album.R
import com.nor35.photos.feature_album.databinding.FragmentPhotoDetailBinding
import com.nor35.photos.feature_album.di.DaggerFeatureAlbumComponent
import timber.log.Timber
import javax.inject.Inject


class PhotoDetailFragment : Fragment() {

    private lateinit var binding: FragmentPhotoDetailBinding

    @Inject
    lateinit var photoDetailViewModel: PhotoDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentPhotoDetailBinding.inflate(inflater, container, false)

        setupPhotoDetailViewModelObserver()
        val photoId = requireArguments().getLong(this.resources.getString(R.string.photoId))
        photoDetailViewModel.getPhoto(photoId)

        return binding.root
    }

    private fun setupPhotoDetailViewModelObserver() {

        photoDetailViewModel.liveData.observe(viewLifecycleOwner) { photoDetailState ->
            if (photoDetailState.isLoading)
                binding.photoDetailProgressBar.visibility = View.VISIBLE
            else
                binding.photoDetailProgressBar.visibility = View.GONE

            if (photoDetailState.error.isNotEmpty())
                Toast.makeText(this@PhotoDetailFragment.context, photoDetailState.error,
                    Toast.LENGTH_LONG).show()

            if (photoDetailState.photoDetail != null) {

                binding.photoDetailCoverErrorImageView.visibility = View.GONE
                binding.photodetailImageview.visibility = View.VISIBLE

                binding.photodetailImageview.load(photoDetailState.photoDetail.imageUrl)
                {
                    crossfade(true)
                }

                binding.photoDetailWidht.text =  getString(R.string.width_message, photoDetailState.photoDetail.width)
                binding.photoDetailHeight.text =  getString(R.string.height_message, photoDetailState.photoDetail.height)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        photoDetailViewModel.liveData.value?.photoDetail?.let {
            outState.putLong(this.resources.getString(R.string.photoId), it.id)
            Timber.d("Save photoId with id = ${it.id}")
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFeatureAlbumComponent
            .builder()
            .bindContext(context)
            .bindFragment(this)
            .buildAlbumComponent()
            .inject(this)
    }
}