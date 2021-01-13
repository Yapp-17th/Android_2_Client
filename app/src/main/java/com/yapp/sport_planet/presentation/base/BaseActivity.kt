package com.yapp.sport_planet.presentation.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.yapp.sport_planet.presentation.login.LoadingFragment
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity<B : ViewDataBinding>
    (@LayoutRes private val layoutResId: Int) : AppCompatActivity() {

    protected lateinit var binding: B
    private var loadingFragment: LoadingFragment? = null
    protected val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResId)
        binding.lifecycleOwner = this
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    fun showLoading() {
        this.runOnUiThread {
            if (loadingFragment != null || (loadingFragment?.dialog)?.isShowing == true) {
                return@runOnUiThread
            } else {
                loadingFragment = LoadingFragment.newInstance()
                loadingFragment?.let { it ->
                    it.show(supportFragmentManager, "LOADING_FRAGMENT")
                }
            }
        }

        this.runOnUiThread {
            if (loadingFragment != null || (loadingFragment?.dialog)?.isShowing == true) {
                return@runOnUiThread
            } else {
                loadingFragment = LoadingFragment.newInstance()
                loadingFragment?.let { it ->
                    it.show(supportFragmentManager, "LOADING_FRAGMENT")
                }
            }
        }
    }

    fun hideLoading() {
        this.runOnUiThread {
            (supportFragmentManager.findFragmentByTag("LOADING_FRAGMENT") as? LoadingFragment)?.dismiss()
            loadingFragment = null
        }
    }

    protected fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}