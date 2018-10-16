package tech.central.showcase.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.epoxy.EpoxyItemSpacingDecorator
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_photo.*
import tech.central.showcase.MainViewModelFactory
import tech.central.showcase.R
import tech.central.showcase.base.BaseFragment
import tech.central.showcase.base.epoxy.view.EpoxyLoadingViewModel_
import tech.central.showcase.photo.controller.PhotoController
import tech.central.showcase.photo_detail.PhotoDetailFragment
import javax.inject.Inject
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
    lateinit var mMainViewModelFactory: MainViewModelFactory
    @Inject
    lateinit var mPhotoController: PhotoController
    @Inject
    lateinit var mLayoutManagerProvider: Provider<GridLayoutManager>
    @Inject
    lateinit var mItemDecoration: EpoxyItemSpacingDecorator

    //Data Members
    private val mPhotoViewModel by lazy { ViewModelProviders.of(activity, mMainViewModelFactory).get(PhotoViewModel::class.java) }
    private lateinit var mLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        arguments?.apply { }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_photo, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstance(view, savedInstanceState)

        //Register ViewModel
        mPhotoViewModel.bindPhotoListLiveData()
                .observe(this, Observer {
                    mPhotoController.setData(it)
                })
    }

    private fun initInstance(rootView: View?, savedInstanceState: Bundle?) {
        //Init View instance
        mLayoutManager = mLayoutManagerProvider.get()
        mLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val model = mPhotoController.adapter.getModelAtPosition(position)
                return when (model) {
                    is EpoxyLoadingViewModel_ -> mLayoutManager.spanCount
                    else -> 1
                }
            }
        }
        recyclerView.apply {
            adapter = mPhotoController.adapter
            layoutManager = mLayoutManager
            addItemDecoration(mItemDecoration)
        }

        subscriptions += mPhotoController.bindDetailRelay()
                .subscribeBy(
                        onNext = {
                            this@PhotoFragment.view?.findNavController()
                                    ?.navigate(R.id.action_photoFragment_to_photoDetailFragment, PhotoDetailFragment.newBundle(it))
                        }
                )
    }
}
