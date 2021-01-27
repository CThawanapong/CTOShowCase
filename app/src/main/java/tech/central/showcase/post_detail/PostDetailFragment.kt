package tech.central.showcase.post_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
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
    }

    //Injection
    @Inject
    lateinit var mSavedStateViewModelFactory: SavedStateViewModelFactory

    //Data Members
    private val mPostDetailViewModel by viewModels<PostDetailViewModel> { mSavedStateViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
        val animation  = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = animation
        sharedElementEnterTransition = animation
    }

    private fun init(savedInstanceState: Bundle?) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initInstance(view, savedInstanceState)

        //Register ViewModel
        mPostDetailViewModel.postDetailViewState
                .observe(viewLifecycleOwner) {
                    with(it.postItem) {
                        txt_title_post.text = first.title
                        txt_name.text = second.name
                        txt_email.text = second.email
                        txt_body_content.text = first.body
                        cardViewTitle.transitionName = "${first.id}"
                    }
                }

        mPostDetailViewModel.loadInit()
    }
    private fun initInstance(rootView: View?, savedInstanceState: Bundle?) {
        //Init View instance
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                sharedElementReturnTransition
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}