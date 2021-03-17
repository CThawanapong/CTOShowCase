package tech.central.showcase.post_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.transition.TransitionInflater
import kotlinx.android.synthetic.main.fragment_post_detail.*
import tech.central.showcase.R
import tech.central.showcase.base.BaseFragment
import tech.central.showcase.di.factory.assisted.SavedStateViewModelFactory
import javax.inject.Inject

class PostDetailFragment : BaseFragment() {
    companion object {
        @JvmStatic
        private val TAG = PostDetailFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(): PostDetailFragment {
            val fragment = PostDetailFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    //Injection
    @Inject
    lateinit var mSavedStateViewModelFactory: SavedStateViewModelFactory

    //Data Members
    private val mPostDetailViewModel by viewModels<PostDetailViewModel> { mSavedStateViewModelFactory  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    private fun init(savedInstanceState: Bundle?) {
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstance(view, savedInstanceState)

        //Register ViewModel
        mPostDetailViewModel.postDetailViewState
                .observe(viewLifecycleOwner) {
                   with(it.post) {
                       titleTextView.text = title
                       nameTextView.text = name
                       emailTextView.text = email

                       bodyTextView.text = body
                       headCardView.transitionName = id.toString()
                   }
                }
        mPostDetailViewModel.loadInit()
    }

    private fun initInstance(rootView: View?, savedInstanceState: Bundle?) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}