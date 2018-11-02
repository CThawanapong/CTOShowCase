package tech.central.showcase.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.epoxy.EpoxyItemSpacingDecorator
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_photo.*
import tech.central.showcase.MainViewModelFactory
import tech.central.showcase.base.BaseFragment
import tech.central.showcase.base.epoxy.view.EpoxyLoadingViewModel_
import tech.central.showcase.photo.PhotoViewModel
import tech.central.showcase.photo.controller.PhotoController
import tech.central.showcase.photo_detail.PhotoDetailFragment
import javax.inject.Inject
import javax.inject.Provider
import tech.central.showcase.R

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
    }

    private fun initInstance(rootView: View?, savedInstanceState: Bundle?) {
        //Init View instance
    }

}
