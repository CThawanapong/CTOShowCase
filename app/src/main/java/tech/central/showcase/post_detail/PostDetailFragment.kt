package tech.central.showcase.post_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import kotlinx.android.synthetic.main.fragment_post_detail.*
import tech.central.showcase.R
import tech.central.showcase.base.BaseFragment

class PostDetailFragment : BaseFragment() {

    //Data Members
    private val mPostDetailViewModel by viewModels<PostDetailViewModel>()

    private val postDetailFragmentArgs by navArgs<PostDetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstance(view, savedInstanceState)
        mPostDetailViewModel.loadPost(postDetailFragmentArgs.post)

        //Register ViewModel
        mPostDetailViewModel.postDetailViewState.observe(viewLifecycleOwner) {
            with(it.post) {
                tvPostTitle.text = title
                tvPostBody.text = body
                tvUserName.text = user?.name
                tvUserEmail.text = user?.email
            }
        }
    }

    private fun initInstance(rootView: View?, savedInstanceState: Bundle?) {
        //Init View instance
        postDetailFragmentArgs.post.id.let { id ->
            tvPostTitle.transitionName = getString(R.string.shared_element_title, id)
            tvUserName.transitionName = getString(R.string.shared_element_name, id)
            tvUserEmail.transitionName = getString(R.string.shared_element_email, id)
        }
    }
}
