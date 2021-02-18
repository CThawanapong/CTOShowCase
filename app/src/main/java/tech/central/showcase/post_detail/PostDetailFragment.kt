package tech.central.showcase.post_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
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
    }

    private fun init(savedInstanceState: Bundle?) {

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_post_detail, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstance(view, savedInstanceState)

        //Register ViewModel
        mPostDetailViewModel.postDetailViewState
                .observe(viewLifecycleOwner) {
                    with(it.post) {
                        textViewPostTitle.text = title
                        textViewUserName.text = user?.name
                        textViewUserEmail.text = user?.email

                        //The detail of each post should contain both a title and a body.
                        textViewBody.text = "$title\n$body"
                    }
                }

        mPostDetailViewModel.loadInit()
    }

    private fun initInstance(rootView: View?, savedInstanceState: Bundle?) {
        //Init View instance
    }
}
