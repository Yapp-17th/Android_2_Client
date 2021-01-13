package com.yapp.sport_planet.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.yapp.sport_planet.presentation.login.LoadingFragment
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes val layoutId: Int
) : Fragment() {
    protected lateinit var binding: B
    protected abstract val viewModel: VM
    protected val compositeDisposable = CompositeDisposable()
    private var loadingFragment: LoadingFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        init()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    protected fun onBackPressed() {
        if (parentFragmentManager.backStackEntryCount > 0) {
            parentFragmentManager.popBackStack()
        } else {
            activity?.finish()
        }
    }

    fun showLoading() {
        activity?.runOnUiThread {
            if (loadingFragment != null || (loadingFragment?.dialog)?.isShowing == true) {
                return@runOnUiThread
            } else {
                loadingFragment = LoadingFragment.newInstance()
                loadingFragment?.let { it ->
                    it.show(childFragmentManager, "LOADING_FRAGMENT")
                }
            }
        }
    }

    fun hideLoading() {
        activity?.runOnUiThread {
            (childFragmentManager.findFragmentByTag("LOADING_FRAGMENT") as? LoadingFragment)?.dismiss()
            loadingFragment = null
        }
    }

    abstract fun init()

    protected fun showToast(msg: String) =
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}