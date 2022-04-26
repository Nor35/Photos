package com.nor35.photos.feature.photo.detail.presentation.photo.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import coil.compose.AsyncImage
import com.nor35.photos.PhotosApplication
import com.nor35.photos.feature.photo.detail.R
import com.nor35.photos.feature.photo.detail.di.DaggerFeaturePhotoDetailComponent
import com.nor35.photos.feature.photo.detail.presentation.photo.detail.ui.theme.MyComposeApplicationTheme
import javax.inject.Inject

class PhotoDetailFragment : Fragment() {

    @Inject
    lateinit var photoDetailViewModelFactory: PhotoDetailViewModelFactory

    lateinit var photoDetailViewModel: PhotoDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val photoId = requireArguments().getLong(this.resources.getString(R.string.photoId))

        photoDetailViewModel = photoDetailViewModelFactory.create(photoId)

        return ComposeView(requireContext()).apply {
            setContent {
                MyComposeApplicationTheme {
                    Surface(color = MaterialTheme.colors.background) {
                        SetupPhotoDetailViewModelObserver()
                    }
                }
            }
        }
    }

    @Composable
    private fun SetupPhotoDetailViewModelObserver() {

        val state = photoDetailViewModel.mutableState.value

        Box(modifier = Modifier.fillMaxSize()) {
            state.photoDetail?.let { photoDetail ->
                Column(modifier = Modifier.align(Alignment.Center)) {

                    AsyncImage(
                        model = photoDetail.imageUrl,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        placeholder = painterResource(R.drawable.ic_stub_image),
                        error = painterResource(R.drawable.ic_error_image)
                    )
                    Text(
                        text = getString(R.string.width_message, photoDetail.width),
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = getString(R.string.height_message, photoDetail.height),
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            if (state.isLoading)
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            if (state.error.isNotBlank())
                Text(
                    text = state.error,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
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
