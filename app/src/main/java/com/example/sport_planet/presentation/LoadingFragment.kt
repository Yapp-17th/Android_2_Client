package com.example.sport_planet.presentation

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.sport_planet.R
import com.example.sport_planet.databinding.FragmentLoadingBinding

class LoadingFragment private constructor() : DialogFragment() {

    private lateinit var bindidng: FragmentLoadingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindidng = DataBindingUtil.inflate(inflater, R.layout.fragment_loading, container, false)
        return bindidng.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this).asGif().load(R.raw.sportplanet_loading_indicator).into(bindidng.ivLoading)

        dialog?.setCancelable(false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    companion object {
        fun newInstance() = LoadingFragment()
    }
}