package com.nor35.photos.feature.photo.detail.presentation.photo.detail

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import com.nor35.photos.PhotosApplication
import com.nor35.photos.feature.photo.detail.R
import com.nor35.photos.feature.photo.detail.databinding.FragmentPhotoDetailBinding
import com.nor35.photos.feature.photo.detail.di.DaggerFeaturePhotoDetailComponent
import com.nor35.photos.feature.photo.detail.domain.notifications.MeowNotification
import com.nor35.photos.presentation.delegate.viewBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

class PhotoDetailFragment : Fragment(R.layout.fragment_photo_detail) {

    private val binding: FragmentPhotoDetailBinding by viewBinding()

    private var photoDetailViewModel: PhotoDetailViewModel? = null

    @Inject
    lateinit var photoDetailViewModelFactory: PhotoDetailViewModelFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photoId = requireArguments().getLong(this.resources.getString(R.string.photoId))
        photoDetailViewModel = photoDetailViewModelFactory.create(photoId)
        setupPhotoDetailViewModelObserver()
    }

    private fun setupPhotoDetailViewModelObserver() {

        photoDetailViewModel?.liveData?.observe(viewLifecycleOwner) { photoDetailState ->
            if (photoDetailState.isLoading)
                binding.photoDetailProgressBar.visibility = View.VISIBLE
            else
                binding.photoDetailProgressBar.visibility = View.GONE

            if (photoDetailState.error.isNotEmpty())
                Toast.makeText(
                    this@PhotoDetailFragment.context, photoDetailState.error,
                    Toast.LENGTH_LONG
                ).show()

            if (photoDetailState.photoDetail != null) {

                binding.photoDetailCoverErrorImageView.visibility = View.GONE
                binding.photodetailImageview.visibility = View.VISIBLE

                binding.photodetailImageview.load(photoDetailState.photoDetail.imageUrl) {
                    crossfade(true)
                }

                binding.photoDetailWidht.text =
                    getString(R.string.width_message, photoDetailState.photoDetail.width)
                binding.photoDetailHeight.text =
                    getString(R.string.height_message, photoDetailState.photoDetail.height)

                viewLifecycleOwner.lifecycleScope.launch {
                    MeowNotification().invoke(
                        this@PhotoDetailFragment.requireContext(),
                        photoDetailState.photoDetail.imageUrl
                    )
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val appComponent = (activity?.applicationContext as PhotosApplication).appComponent

        DaggerFeaturePhotoDetailComponent
            .builder()
            .bindAppComponent(appComponent)
            .buildAlbumComponent()
            .inject(this)
    }
}
