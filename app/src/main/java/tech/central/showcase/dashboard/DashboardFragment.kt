package tech.central.showcase.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.fragment_dashboard.*
import tech.central.showcase.R
import tech.central.showcase.base.BaseFragment

class DashboardFragment : BaseFragment() {
    companion object {
        @JvmStatic
        private val TAG = DashboardFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(): DashboardFragment {
            val fragment = DashboardFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    //Injection

    //Data Members

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        arguments?.apply { }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_dashboard, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstance(view, savedInstanceState)

        //Register ViewModel
    }

    private fun initInstance(rootView: View?, savedInstanceState: Bundle?) {
        //Init View instance
        subscriptions += buttonPhoto.clicks()
                .subscribeBy {
                    this@DashboardFragment.view?.findNavController()
                            ?.navigate(R.id.action_dashboardFragment_to_photoFragment)
                }

        subscriptions += buttonPost.clicks()
                .subscribeBy(
                        onError = { e -> e.printStackTrace() },
                        onNext = {
                            findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToPostFragment(true))
                        }
                )
    }
}
