package tech.central.showcase.post

import android.os.Bundle
import android.view.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.FragmentNavigatorExtras
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
import tech.central.showcase.post.model.SortType
import javax.inject.Inject
import javax.inject.Provider

class PostFragment : BaseFragment() {
    companion object {
        const val ARGS_SHOULD_RELOAD = "shouldReload"

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
    lateinit var mLayoutManagerProvider: Provider<LinearLayoutManager>

    @Inject
    lateinit var mItemDecoration: DividerItemDecoration

    //Data Members
    private val mPostViewModel by activityViewModels<PostViewModel> { mViewModelFactory }

    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstance(view, savedInstanceState)

        //I'm not using safeArgs here because when we back to this fragment, we can still get navArgs even when we already cleared it.
        val shouldReload = arguments?.get(ARGS_SHOULD_RELOAD) as Boolean?
        if (shouldReload == true) {
            mPostViewModel.loadPosts()
            arguments?.clear()
        }

        //Register ViewModel
        mPostViewModel.postViewStateLiveData.observe(viewLifecycleOwner) {
            mPostController.setData(it)
        }

        mPostViewModel.onLoadedLiveEvent.observe(viewLifecycleOwner) {
            Snackbar.make(view, R.string.post_loaded, Snackbar.LENGTH_LONG).show()
            scrollToTop()
        }

        mPostViewModel.onSortedLiveEvent.observe(viewLifecycleOwner) {
            scrollToTop()
        }
    }

    private fun scrollToTop() {
        recyclerView?.scrollToPosition(0)
    }

    private fun initInstance(rootView: View?, savedInstanceState: Bundle?) {
        //Init View instance
        postponeEnterTransition()
        rootView?.doOnPreDraw { startPostponedEnterTransition() }

        mLayoutManager = mLayoutManagerProvider.get()
        recyclerView.apply {
            adapter = mPostController.adapter
            layoutManager = mLayoutManager
            addItemDecoration(mItemDecoration)
        }

        subscriptions += mPostController.bindPostRelay().subscribeBy(
                onError = { e -> e.printStackTrace() },
                onNext = {
                    val extras = FragmentNavigatorExtras(
                            it.tvTitle to getString(R.string.shared_element_title, it.post.id),
                            it.tvName to getString(R.string.shared_element_name, it.post.id),
                            it.tvEmail to getString(R.string.shared_element_email, it.post.id))

                    findNavController().navigate(PostFragmentDirections.actionPostFragmentToPostDetailFragment(it.post), extras)
                }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_sort_post, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sortAsc -> {
                mPostViewModel.sortPostList(SortType.ASC)
                true
            }
            R.id.sortDesc -> {
                mPostViewModel.sortPostList(SortType.DESC)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
