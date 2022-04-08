package com.nor35.photos.feature_album.presentation.album

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.nor35.photos.domain.Constants.NUMBER_OF_COLUMNS
import com.nor35.photos.domain.Constants.NUMBER_OF_ROWS
import com.nor35.photos.feature_album.R
import com.nor35.photos.feature_album.databinding.FragmentPhotosBinding
import com.nor35.photos.feature_album.di.DaggerFeatureAlbumComponent
import com.nor35.photos.feature_album.presentation.album.recyclerview.PhotoAdapter
import javax.inject.Inject


class PhotosFragment : Fragment() {

    private lateinit var binding: FragmentPhotosBinding

    @Inject
    lateinit var albumViewModel: AlbumViewModel

    @Inject
    lateinit var photoAdapter: PhotoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentPhotosBinding.inflate(inflater, container, false)

        initAlbumRecyclerview()
        setupAlbumViewModelObserver()

        return binding.root
    }

    private fun initAlbumRecyclerview(){
        photoAdapter.resetPhotoLayoutParams()

        binding.albumRecyclerview.apply{
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), getSpanCount(),
                LinearLayoutManager.HORIZONTAL, false)
            adapter = photoAdapter
        }
        photoAdapter.setOnClickListener { photoId: Long ->
            albumViewModel.navigateToPhotoDetail(photoId) }
    }

    private fun getSpanCount(): Int{
        return if (resources.configuration.orientation == LinearLayoutManager.VERTICAL) {
            NUMBER_OF_ROWS
        } else {
            NUMBER_OF_COLUMNS
        }
    }

    private fun setupAlbumViewModelObserver() {

        albumViewModel.liveData.observe(viewLifecycleOwner) { photoState ->

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
                albumViewModel.getPhoto()
                if(photoAdapter.itemCount > 0)
                    binding.albumRecyclerview.postDelayed({
                        binding.albumRecyclerview
                            .smoothScrollToPosition(photoAdapter.itemCount - 1)
                    }, 1000)
                true
            }
            R.id.action_reload_all -> {
                photoAdapter.deletePhotos()
                albumViewModel.reloadAllPhotos()
                true
            }
            else -> super.onOptionsItemSelected(item)
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