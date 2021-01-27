package tech.central.showcase.posts

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.EpoxyItemSpacingDecorator
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_photo.*
import kotlinx.android.synthetic.main.fragment_posts.*
import tech.central.showcase.R
import tech.central.showcase.base.BaseFragment
import tech.central.showcase.posts.controller.PostItemController
import javax.inject.Inject
import javax.inject.Provider

class PostsFragment : BaseFragment() {
    companion object {
        @JvmStatic
        private val TAG = PostsFragment::class.java.simpleName
        const val ENTER_FROM_DASHBOARD = "enterFromDashboard"

        @JvmStatic
        fun newInstance(): PostsFragment {
            val fragment = PostsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    //Injection
    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var mPostItemController: PostItemController

    @Inject
    lateinit var mLayoutManagerProvider: Provider<LinearLayoutManager>

    @Inject
    lateinit var mItemDecoration: EpoxyItemSpacingDecorator


    private var rootView: View? = null;

    //Data Members
    private val mPostItemViewModel by activityViewModels<PostsViewModel> { mViewModelFactory }
    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        arguments?.apply { }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_posts, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initInstance(view, savedInstanceState)
        setHasOptionsMenu(true);

        val needReload = arguments?.get(ENTER_FROM_DASHBOARD) as Boolean?
        if (needReload == true) {
            mPostItemViewModel.loadInit()
            scrollToTopOfRecyclerView()
            arguments?.clear()
        }

        mPostItemViewModel.postItemStateLiveData
                .observe(viewLifecycleOwner) {
                    mPostItemController.setData(it)
                }

        mPostItemViewModel.snackBarMessage.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { message ->
                showSnackBar(message)
            }
        })

    }

    private fun initInstance(rootView: View?, savedInstanceState: Bundle?) {
        //Init View instance
        mLayoutManager = mLayoutManagerProvider.get()
        recyclerViewPostItem.apply {
            adapter = mPostItemController.adapter
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
            layoutManager = mLayoutManager
            addItemDecoration(mItemDecoration)
        }

        subscriptions += mPostItemController.bindDetailRelay()
                .map {
                    Pair(PostsFragmentDirections.actionPostsFragmentToPostDetailFragment(it.first, it.second), it.third)
                }
                .subscribeBy(
                        onError = {},
                        onNext = { it -> findNavController().navigate(it.first, it.second) }
                )
    }

    private fun scrollToTopOfRecyclerView() {
        Handler().postDelayed(Runnable { recyclerViewPostItem.smoothScrollToPosition(0) }, 100)
    }

    private fun showSnackBar(message: String) {
        val snackBar = Snackbar
                .make(recyclerViewPostItem, message, Snackbar.LENGTH_SHORT)
        snackBar.show()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.ascending_sort -> {
                mPostItemViewModel.sortItemAscending()
                scrollToTopOfRecyclerView()
                true
            }
            R.id.descending_sort -> {
                mPostItemViewModel.sortItemDescending()
                scrollToTopOfRecyclerView()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater)

    }
}