package tech.central.showcase.post

import android.os.Bundle
import android.view.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_photo.*
import tech.central.showcase.R
import tech.central.showcase.base.BaseFragment
import tech.central.showcase.post.controller.PostController
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider

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
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var mPostController: PostController

    @Inject
    @Named("LINEAR_LAYOUT_MANAGER")
    lateinit var mLayoutManagerProvider: Provider<LinearLayoutManager>

    @Inject
    @Named("LINEAR_DECORATOR")
    lateinit var mItemDecoration: DividerItemDecoration

    //Data Members
    private val mPostViewModel by activityViewModels<PostViewModel> { mViewModelFactory }
    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
        mPostViewModel.postViewStateLiveData.observe(viewLifecycleOwner) {
            mPostController.setData(it)
        }

        mPostViewModel.onPostListLoadedEvent.observe(viewLifecycleOwner, Observer {
            it?.let {
                Snackbar.make(view, R.string.post_loaded, Snackbar.LENGTH_LONG).show()
                mPostViewModel.clearIsPostLoadedEvent()
            }
        })
    }

    private fun initInstance(rootView: View?, savedInstanceState: Bundle?) {
        //Init View instance
        mLayoutManager = mLayoutManagerProvider.get()
        recyclerView.apply {
            adapter = mPostController.adapter
            layoutManager = mLayoutManager
            addItemDecoration(mItemDecoration)
        }

        subscriptions += mPostController.bindDetailRelay()
                .map {
                    PostFragmentDirections.actionPostFragmentToPostDetailFragment(it)
                }
                .subscribeBy(
                        onError = {},
                        onNext = findNavController()::navigate
                )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_sort_post, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sortByTitleAsc -> {
                mPostViewModel.sortListPost()
                true
            }
            R.id.sortByTitleDesc -> {
                mPostViewModel.sortListPost(isDesc = true)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
