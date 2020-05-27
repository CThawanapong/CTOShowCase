package tech.central.showcase.photo_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.fragment_photo_detail.*
import tech.central.showcase.R
import tech.central.showcase.base.BaseFragment
import tech.central.showcase.base.extension.loadUrlCropCenter
import tech.central.showcase.di.factory.assisted.SavedStateViewModelFactory
import javax.inject.Inject

class PhotoDetailFragment : BaseFragment() {
    companion object {
        @JvmStatic
        private val TAG = PhotoDetailFragment::class.java.simpleName
    }

    //Injection
    @Inject
    lateinit var mSavedStateViewModelFactory: SavedStateViewModelFactory

    //Data Members
    private val mPhotoDetailViewModel by viewModels<PhotoDetailViewModel> { mSavedStateViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_photo_detail, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstance(view, savedInstanceState)

        //Register ViewModel
        mPhotoDetailViewModel.photoDetailViewState
            .observe(viewLifecycleOwner) {
                with(it.photo) {
                    imageView.loadUrlCropCenter(context, url)
                    textView.text = title
                }
            }

        mPhotoDetailViewModel.loadInit()
    }

    private fun initInstance(rootView: View?, savedInstanceState: Bundle?) {
        //Init View instance
    }
}
