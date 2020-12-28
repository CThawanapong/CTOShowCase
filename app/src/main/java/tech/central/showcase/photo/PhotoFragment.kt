package tech.central.showcase.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.epoxy.EpoxyItemSpacingDecorator
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_photo.*
import tech.central.showcase.R
import tech.central.showcase.base.BaseFragment
import tech.central.showcase.photo.controller.PhotoController
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider

class PhotoFragment : BaseFragment() {
    companion object {
        @JvmStatic
        private val TAG = PhotoFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(): PhotoFragment {
            val fragment = PhotoFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    //Injection
    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var mPhotoController: PhotoController

    @Inject
    @Named("GRID_LAYOUT_MANAGER")
    lateinit var mLayoutManagerProvider: Provider<GridLayoutManager>

    @Inject
    @Named("GRID_DECORATOR")
    lateinit var mItemDecoration: EpoxyItemSpacingDecorator

    //Data Members
    private val mPhotoViewModel by activityViewModels<PhotoViewModel> { mViewModelFactory }
    private lateinit var mLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        arguments?.apply { }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_photo, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstance(view, savedInstanceState)

        //Register ViewModel
        mPhotoViewModel.photoViewStateLiveData
            .observe(viewLifecycleOwner) {
                mPhotoController.setData(it)
            }

        mPhotoViewModel.loadInit()
    }

    private fun initInstance(rootView: View?, savedInstanceState: Bundle?) {
        //Init View instance
        mLayoutManager = mLayoutManagerProvider.get()
        mPhotoController.spanCount = mLayoutManager.spanCount
        recyclerView.apply {
            adapter = mPhotoController.adapter
            layoutManager = mLayoutManager.apply {
                spanSizeLookup = mPhotoController.spanSizeLookup
            }
            addItemDecoration(mItemDecoration)
        }

        subscriptions += mPhotoController.bindDetailRelay()
            .map {
                PhotoFragmentDirections.actionPhotoFragmentToPhotoDetailFragment(it)
            }
            .subscribeBy(
                onError = {},
                onNext = findNavController()::navigate
            )
    }
}
