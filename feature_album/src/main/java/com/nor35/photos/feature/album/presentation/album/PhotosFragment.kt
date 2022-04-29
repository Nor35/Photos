package com.nor35.photos.feature.album.presentation.album

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.nor35.photos.PhotosApplication
import com.nor35.photos.domain.Constants.DELAY_WHEN_ADDING_ONE_PICTURE
import com.nor35.photos.domain.Constants.NUMBER_OF_COLUMNS
import com.nor35.photos.domain.Constants.NUMBER_OF_ROWS
import com.nor35.photos.domain.Constants.PHOTO_ID_ARRAY
import com.nor35.photos.feature.album.R
import com.nor35.photos.feature.album.databinding.FragmentPhotosBinding
import com.nor35.photos.feature.album.di.DaggerFeatureAlbumComponent
import com.nor35.photos.feature.album.presentation.album.AlbumViewModel.AlbumViewModelFactory
import com.nor35.photos.feature.album.presentation.album.recyclerview.PhotoAdapter
import com.nor35.photos.presentation.delegate.viewBinding
import timber.log.Timber
import javax.inject.Inject

class PhotosFragment : Fragment(R.layout.fragment_photos) {

    private val binding: FragmentPhotosBinding by viewBinding()

    private var albumViewModel: AlbumViewModel? = null

    @Inject
    lateinit var albumViewModelFactory: AlbumViewModelFactory

    @Inject
    lateinit var photoAdapter: PhotoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        if (albumViewModel == null) {
            initViewModel(savedInstanceState)
            setupAlbumViewModelObserver()
        }

        initAlbumRecyclerview()
    }

    private fun initViewModel(savedInstanceState: Bundle?) {

        val photoIdArray = savedInstanceState?.getLongArray(PHOTO_ID_ARRAY)
        if (photoIdArray != null && photoIdArray.size >= 0) {
            Timber.d("Restore photoId Array from Bundle with ${photoIdArray.size} elements")
        }

        albumViewModel = albumViewModelFactory.create(photoIdArray)
    }

    private fun initAlbumRecyclerview() {
        photoAdapter.resetPhotoLayoutParams()

        binding.albumRecyclerview.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(
                requireContext(), getSpanCount(),
                LinearLayoutManager.HORIZONTAL, false
            )
            adapter = photoAdapter
        }
        photoAdapter.setOnClickListener { photoId: Long ->
            albumViewModel?.navigateToPhotoDetail(photoId)
        }
    }

    private fun getSpanCount(): Int {
        return if (resources.configuration.orientation == LinearLayoutManager.VERTICAL) {
            NUMBER_OF_ROWS
        } else {
            NUMBER_OF_COLUMNS
        }
    }

    private fun setupAlbumViewModelObserver() {

        albumViewModel?.liveData?.observe(viewLifecycleOwner) { photoState ->

            if (photoState.isLoading)
                binding.albumProgressBar.visibility = View.VISIBLE
            else
                binding.albumProgressBar.visibility = View.GONE

            if (photoState.error.isNotEmpty())
                Toast.makeText(this@PhotosFragment.context, photoState.error, Toast.LENGTH_LONG)
                    .show()

            if (photoState.album.isNotEmpty())
                photoAdapter.addPhotos(photoState.album)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_album, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.action_add_image -> {
                albumViewModel?.getPhoto()
                if (photoAdapter.itemCount > 0)
                    binding.albumRecyclerview.postDelayed(
                        {
                            binding.albumRecyclerview
                                .smoothScrollToPosition(photoAdapter.itemCount - 1)
                        },
                        DELAY_WHEN_ADDING_ONE_PICTURE
                    )
                true
            }
            R.id.action_reload_all -> {
                photoAdapter.deletePhotos()
                albumViewModel?.reloadAllPhotos()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val photoIdArray: MutableList<Long> = mutableListOf()
        photoAdapter.photos.onEach { photoIdArray.add(it.id) }

        outState.putLongArray(PHOTO_ID_ARRAY, photoIdArray.toLongArray())
        Timber.d("Save photoId Array in Bundle with ${photoIdArray.size} elements")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val appComponent = (activity?.applicationContext as PhotosApplication).appComponent

        DaggerFeatureAlbumComponent
            .builder()
            .bindAppComponent(appComponent)
            .buildAlbumComponent()
            .inject(this)
    }
}
