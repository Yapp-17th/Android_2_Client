package com.yapp.sport_planet.di

import com.yapp.sport_planet.presentation.board.BoardViewModel
import com.yapp.sport_planet.presentation.chatting.viewmodel.ChattingActivityViewModel
import com.yapp.sport_planet.presentation.chatting.viewmodel.ChattingFragmentViewModel
import com.yapp.sport_planet.presentation.home.HomeViewModel
import com.yapp.sport_planet.presentation.home.filter.FilterViewModel
import com.yapp.sport_planet.presentation.home.filter.city.AddressCityViewModel
import com.yapp.sport_planet.presentation.home.filter.exercise.ExerciseViewModel
import com.yapp.sport_planet.presentation.mypage.MyPageViewModel
import com.yapp.sport_planet.presentation.mypage.history.viewModel.FinishTabViewModel
import com.yapp.sport_planet.presentation.mypage.history.viewModel.IngTabViewModel
import com.yapp.sport_planet.presentation.mypage.other.history.OtherHistoryViewModel
import com.yapp.sport_planet.presentation.mypage.other.mypage.OtherMyPageViewModel
import com.yapp.sport_planet.presentation.mypage.scrap.ScrapViewModel
import com.yapp.sport_planet.presentation.mypage.setting.SettingViewModel
import com.yapp.sport_planet.presentation.profile.ProfileViewModel
import com.yapp.sport_planet.presentation.search.SearchViewModel
import com.yapp.sport_planet.presentation.search.result.SearchResultViewModel
import com.yapp.sport_planet.presentation.write.WriteViewModel
import com.yapp.sport_planet.presentation.write.select.SelectViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { BoardViewModel(get(), get(), get(), get(), get(), get(), get()) }
    viewModel { ChattingActivityViewModel() }
    viewModel { ChattingFragmentViewModel() }
    viewModel { AddressCityViewModel() }
    viewModel { ExerciseViewModel() }
    viewModel { FilterViewModel() }
    viewModel { HomeViewModel() }
    viewModel { FinishTabViewModel() }
    viewModel { IngTabViewModel() }
    viewModel { OtherHistoryViewModel() }
    viewModel { OtherMyPageViewModel() }
    viewModel { ScrapViewModel() }
    viewModel { SettingViewModel() }
    viewModel { MyPageViewModel() }
    viewModel { ProfileViewModel() }
    viewModel { SearchResultViewModel() }
    viewModel { SearchViewModel() }
    viewModel { SelectViewModel() }
    viewModel { WriteViewModel() }

}