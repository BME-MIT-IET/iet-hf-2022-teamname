package sk.kasper.ui_launch.section

import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import sk.kasper.domain.model.Photo
import sk.kasper.domain.model.SuccessResponse
import sk.kasper.domain.usecase.launchdetail.GetPhotos
import sk.kasper.ui_common.viewmodel.ReducerViewModel
import sk.kasper.ui_launch.R
import sk.kasper.ui_launch.gallery.PhotoItem
import sk.kasper.ui_launch.gallery.PhotoPagerData

data class GalleryState(
    val title: Int = R.string.gallery,
    var visible: Boolean = true,
    val galleryItems: List<Photo> = emptyList()
)

sealed class GallerySideEffect
data class ShowPhotoPager(val photoPagerData: PhotoPagerData) : GallerySideEffect()

class GalleryViewModel @AssistedInject constructor(
    @Assisted private val launchId: String,
    private val getPhotos: GetPhotos
) : ReducerViewModel<GalleryState, GallerySideEffect>(GalleryState()) {

    init {
        initAction()
    }

    private fun initAction() = action {
        getPhotos.getPhotos(launchId).also {
            when (it) {
                is SuccessResponse -> reduce {
                    copy(
                        visible = it.data.isNotEmpty(),
                        galleryItems = it.data
                    )
                }
                else -> reduce {
                    copy(visible = false)
                }
            }
        }
    }

    fun onPhotoClicked(photo: Photo) = action {
        val oldState = snapshot()

        emitSideEffect(
            ShowPhotoPager(
                PhotoPagerData(oldState.galleryItems.indexOf(photo),
                    oldState.galleryItems.map {
                        PhotoItem(
                            it.fullSizeUrl,
                            it.sourceName,
                            it.description
                        )
                    })
            )
        )
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(launchId: String): GalleryViewModel
    }

}