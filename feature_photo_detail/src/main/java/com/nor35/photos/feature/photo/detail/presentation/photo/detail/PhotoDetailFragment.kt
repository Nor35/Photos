package com.nor35.photos.feature.photo.detail.presentation.photo.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import coil.load
import com.nor35.photos.PhotosApplication
import com.nor35.photos.feature.photo.detail.R
import com.nor35.photos.feature.photo.detail.databinding.FragmentPhotoDetailBinding
import com.nor35.photos.feature.photo.detail.di.DaggerFeaturePhotoDetailComponent
import javax.inject.Inject

class PhotoDetailFragment : Fragment() {

    private lateinit var binding: FragmentPhotoDetailBinding

    @Inject
    lateinit var photoDetailViewModelFactory: PhotoDetailViewModelFactory

    lateinit var photoDetailViewModel: PhotoDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentPhotoDetailBinding.inflate(inflater, container, false)

        val photoId = requireArguments().getLong(this.resources.getString(R.string.photoId))

        photoDetailViewModel = photoDetailViewModelFactory.create(photoId)
        setupPhotoDetailViewModelObserver()

        return binding.root
    }

    private fun setupPhotoDetailViewModelObserver() {

        photoDetailViewModel.liveData.observe(viewLifecycleOwner) { photoDetailState ->
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

                binding.photoDetailWidht.text = getString(R.string.width_message, photoDetailState.photoDetail.width)
                binding.photoDetailHeight.text = getString(R.string.height_message, photoDetailState.photoDetail.height)
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
