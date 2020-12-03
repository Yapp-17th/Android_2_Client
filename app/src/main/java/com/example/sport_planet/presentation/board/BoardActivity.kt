package com.example.sport_planet.presentation.board

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.R
import com.example.sport_planet.databinding.ActivityBoardBinding
import com.example.sport_planet.presentation.base.BaseActivity
import com.example.sport_planet.presentation.base.BaseViewModel
import com.example.sport_planet.remote.RemoteDataSourceImpl

class BoardActivity : BaseActivity<ActivityBoardBinding>(R.layout.activity_board) {
    private val viewModel: BaseViewModel by lazy {
        ViewModelProvider(
            this,
            BoardViewModelFactory(RemoteDataSourceImpl())
        ).get(BaseViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding
    }
}