package tech.central.showcase.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.epoxy.EpoxyItemSpacingDecorator
import tech.central.showcase.MainViewModelFactory
import tech.central.showcase.R
import tech.central.showcase.base.BaseFragment
import tech.central.showcase.base.epoxy.view.EpoxyLoadingViewModel_
import javax.inject.Inject
import javax.inject.Provider
import tech.central.showcase.post.controller.PostController
import kotlinx.android.synthetic.main.fragment_post.*
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import tech.central.showcase.post_detail.PostDetailFragment

class PostFragment : BaseFragment() {
    companion object {
        @JvmStatic
        private val TAG = PostFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(): PostFragment {
            val fragment = PostFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    //Injection
    @Inject
    lateinit var mMainViewModelFactory: MainViewModelFactory

    @Inject
    lateinit var mPostController: PostController

    @Inject
    lateinit var mLayoutManagerProvider: Provider<GridLayoutManager>
    @Inject
    lateinit var mItemDecoration: EpoxyItemSpacingDecorator

    //Data Members
    private val mPostViewModel by lazy { ViewModelProviders.of(activity, mMainViewModelFactory).get(PostViewModel::class.java) }


    private lateinit var mLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        arguments?.apply { }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_post, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstance(view, savedInstanceState)

        //Register ViewModel
        mPostViewModel.bindPostListLiveData()
                .observe(this, Observer {
                    mPostController.setData(it)
                })

    }

    private fun initInstance(rootView: View?, savedInstanceState: Bundle?) {
        //Init View instance
        mLayoutManager = mLayoutManagerProvider.get()
        mLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val model = mPostController.adapter.getModelAtPosition(position)
                return when (model) {
                    is EpoxyLoadingViewModel_ -> mLayoutManager.spanCount
                    else -> 1
                }
            }
        }
        recyclerView.apply {
            adapter = mPostController.adapter
            layoutManager = mLayoutManager
            addItemDecoration(mItemDecoration)
        }

        subscriptions += mPostController.bindDetailRelay()
                .subscribeBy(
                        onError = {},
                        onNext = {
                            this@PostFragment.view?.findNavController()
                                    ?.navigate(R.id.action_postFragment_to_postDetailFragment, PostDetailFragment.newBundle(it))
                        }
                )
    }
}
