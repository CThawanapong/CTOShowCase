package tech.central.showcase.post

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.fragment_post.*
import tech.central.showcase.R
import tech.central.showcase.base.BaseFragment
import tech.central.showcase.post.controller.PostController
import javax.inject.Inject
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

    private fun init(savedInstanceState: Bundle?) {
        arguments?.apply { }
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

    //Data members
    private val mPostViewModel by activityViewModels<PostViewModel> { mViewModelFactory }
    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
        mPostViewModel.sortList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstance(view, savedInstanceState)
        //Register ViewModel
        mPostViewModel.postViewStateLiveData
                .observe(viewLifecycleOwner) {
                    mPostController.setData(it)
                }

//        mPostViewModel.isLoadingLiveData
//                .observe(viewLifecycleOwner) {
//                    when {
//                        it -> showProgressDialog()
//                        else -> hideProgressDialog()
//                    }
//                }

        mPostViewModel.loadInit()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    private fun initInstance(rootView: View, savedInstanceState: Bundle?) {
        // Init View instance
        mLayoutManager = mLayoutManagerProvider.get()

        recyclerView.apply {
            adapter = mPostController.adapter
            layoutManager = mLayoutManager
            addItemDecoration(mItemDecoration)
        }

        subscriptions += mPostController.bindDetailRelay().subscribe {
            val destination = PostFragmentDirections.actionPostFragmentToPostDetailFragment(
                    post = it.post
            )
            val extra = FragmentNavigatorExtras(
                    it.layout to it.layout.transitionName
            )
            findNavController().navigate(destination, extra)
        }




        exitTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.fade)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_sort -> {
                mPostViewModel.sortedListMenu()
                mLayoutManager.scrollToPosition(0)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
