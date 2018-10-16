package tech.central.showcase.photo_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_photo_detail.*
import tech.central.showcase.R
import tech.central.showcase.base.BaseFragment
import tech.central.showcase.base.extension.loadUrlCropCenter
import tech.central.showcase.base.model.Photo
import javax.inject.Inject

class PhotoDetailFragment : BaseFragment() {
    companion object {
        @JvmStatic
        private val TAG = PhotoDetailFragment::class.java.simpleName

        private const val ARG_PHOTO = "ARG_PHOTO"

        @JvmStatic
        fun newBundle(photo: Photo): Bundle {
            val bundle = Bundle()
            bundle.putParcelable(ARG_PHOTO, photo)
            return bundle
        }
    }

    //Injection
    @Inject
    lateinit var mPhotoDetailViewModelFactory: PhotoDetailViewModelFactory

    //Data Members
    private val mPhotoDetailViewModel by lazy { ViewModelProviders.of(this, mPhotoDetailViewModelFactory).get(PhotoDetailViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        arguments?.apply {
            mPhotoDetailViewModelFactory.photo = getParcelable(ARG_PHOTO) as Photo
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_photo_detail, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstance(view, savedInstanceState)

        //Register ViewModel
        mPhotoDetailViewModel.bindPhotoLiveData()
                .observe(this, Observer {
                    it?.let {
                        imageView.loadUrlCropCenter(context, it.url)
                        textView.text = it.title
                    }
                })
    }

    private fun initInstance(rootView: View?, savedInstanceState: Bundle?) {
        //Init View instance

    }
}
