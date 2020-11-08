package com.example.sport_planet.presentation.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sport_planet.model.ExerciseResponse
import com.example.sport_planet.model.RegionResponse
import com.example.sport_planet.model.SignUpResponse
import com.example.sport_planet.presentation.base.BaseViewModel
import com.example.sport_planet.remote.RemoteDataSourceImpl

class ProfileViewModel : BaseViewModel() {
    private val remoteDataSourceImpl = RemoteDataSourceImpl()

    private val _userToken = MutableLiveData<String>()
    val userToken: LiveData<String> get() = _userToken

    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String> get() = _userId

    private val _userExerciseList = MutableLiveData<List<String>>()
    val userExerciseList: LiveData<List<String>> get() = _userExerciseList

    private val _userExerciseIdList = MutableLiveData<List<Long>>()
    val userExerciseIdList: LiveData<List<Long>> get() = _userExerciseIdList

    private val _userRegion = MutableLiveData<String>()
    val userRegion: LiveData<String> get() = _userRegion

    private val _userRegionId = MutableLiveData<Long>()
    val userRegionId: LiveData<Long> get() = _userRegionId

    private val _exerciseList = MutableLiveData<ExerciseResponse>()
    val exerciseList: LiveData<ExerciseResponse> get() = _exerciseList

    private val _regionList = MutableLiveData<RegionResponse>()
    val regionList: LiveData<RegionResponse> get() = _regionList

    private val _postSignUpStatus = MutableLiveData<Int>()
    val postSignUpStatus: LiveData<Int> get() = _postSignUpStatus

    private val _postSignUpStatusMessage = MutableLiveData<String>()
    val postSignUpStatusMessage: LiveData<String> get() = _postSignUpStatusMessage

    val userIntroduceMyself = MutableLiveData<String>()
    val userName = MutableLiveData<String>()
    val userEmail = MutableLiveData<String>()
    val userNickname = MutableLiveData<String>()

    fun setUserToken(userToken: String) {
        _userToken.value = userToken
    }

    fun setUserId(userId: String) {
        _userId.value = userId
    }

    fun setUserExerciseList(userExerciseList: List<String>, userExerciseIdList: List<Long>) {
        _userExerciseList.value = userExerciseList
        _userExerciseIdList.value = userExerciseIdList
    }

    fun setUserRegion(userRegion: String,userRegionId:Long) {
        _userRegion.value = userRegion
        _userRegionId.value = userRegionId
    }

    fun removeUserExerciseItem(item: String, idItem: Int) {
        _userExerciseList.value = _userExerciseList.value?.filter { it != item }
        _userExerciseIdList.value =
            _userExerciseIdList.value?.filter { it != _userExerciseIdList.value!![idItem] }
    }

    fun showExercisePopup() {
        compositeDisposable.add(
            remoteDataSourceImpl.getExercise()
                .subscribe({
                    _exerciseList.postValue(it)
                }, {})
        )
    }

    fun showRegionPopup() {
        compositeDisposable.add(
            remoteDataSourceImpl.getRegion()
                .subscribe({
                    _regionList.postValue(it)
                }, {})
        )
    }

    fun postSignUp() {
        val signUpResponse = SignUpResponse(
            userId = userId.value.toString(),
            userName = userName.value.toString(),
            email = userEmail.value.toString(),
            accessToken = userToken.value.toString(),
            nickName = userNickname.value.toString(),
            address = userRegionId.value!!,
            category = userExerciseIdList.value!!,
            intro = userIntroduceMyself.value.toString()
        )
        compositeDisposable.add(RemoteDataSourceImpl().postSignUp(signUpResponse).subscribe({
            _postSignUpStatus.postValue(it.status)
            _postSignUpStatusMessage.postValue(it.message)
        }, {}))
    }
}

