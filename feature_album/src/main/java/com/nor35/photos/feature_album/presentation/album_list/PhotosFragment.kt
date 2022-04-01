package com.nor35.photos.feature_album.presentation.album_list

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.MenuCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.nor35.photos.domain.Constants.NUMBER_OF_COLUMNS
import com.nor35.photos.feature_album.R
import com.nor35.photos.feature_album.databinding.FragmentPhotosBinding
import com.nor35.photos.feature_album.di.DaggerFeatureAlbumComponent
import com.nor35.photos.feature_album.presentation.album_list.recyclerview.PhotoAdapter
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

        binding.albumRecyclerview.apply{
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), NUMBER_OF_COLUMNS
                , LinearLayoutManager.HORIZONTAL, false
            )
            adapter = photoAdapter
        }

        setupAlbumViewModelObserver()

        return binding.root
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
                Toast.makeText(this@PhotosFragment.context, "addOne", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_reload_all -> {
                Toast.makeText(this@PhotosFragment.context, "reload all", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFeatureAlbumComponent
            .builder()
            .bindContext(context)
            .buildAlbumComponent()
            .inject(this)
    }
}