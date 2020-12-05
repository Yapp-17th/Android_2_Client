package com.example.sport_planet.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sport_planet.data.request.MyViewEditRequest
import com.example.sport_planet.data.response.basic.ExerciseResponse
import com.example.sport_planet.data.response.basic.RegionResponse
import com.example.sport_planet.data.response.login.SignUpResponse
import com.example.sport_planet.presentation.base.BaseViewModel
import com.example.sport_planet.remote.RemoteDataSourceImpl
import com.example.sport_planet.util.applySchedulers

class ProfileViewModel : BaseViewModel() {
    private val remoteDataSourceImpl = RemoteDataSourceImpl()

    private val _userToken = MutableLiveData<String>()
    val userToken: LiveData<String> get() = _userToken

    private val _serverToken = MutableLiveData<String>()
    val serverToken: LiveData<String> get() = _serverToken

    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String> get() = _userId

    private val _userExerciseList = MutableLiveData<List<String>>()
    val userExerciseList: LiveData<List<String>> get() = _userExerciseList

    private val _userExerciseIdList = MutableLiveData<List<Long>>()
    private val userExerciseIdList: LiveData<List<Long>> get() = _userExerciseIdList

    private val _userRegionId = MutableLiveData<Long>()
    private val userRegionId: LiveData<Long> get() = _userRegionId

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
    val userRegion = MutableLiveData<String>()

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

    fun setUserRegion(userRegion: String, userRegionId: Long) {
        this.userRegion.value = userRegion
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
    fun getMyProfileEdit(){
        compositeDisposable.add(RemoteDataSourceImpl().getMyProfileEdit()
            .applySchedulers()
            .subscribe({
                if(it.success){
                    userName.value = it.data.userName
                    userNickname.value = it.data.nickName
                    userEmail.value = it.data.email
                    userIntroduceMyself.value = it.data.intro
                    _userExerciseList.value = it.data.category.map { category ->
                        category.name
                    }
                    _userExerciseIdList.value = it.data.category.map { category ->
                        category.id
                    }
                    userRegion.value = it.data.city.name
                    _userRegionId.value  = it.data.city.id
                }
            },{}))
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
        compositeDisposable.add(
            RemoteDataSourceImpl().postSignUp(signUpResponse)
                .applySchedulers()
                .subscribe({
                    _serverToken.value = it.headers()["token"]
                    _postSignUpStatus.postValue(it.body()?.status)
                    _postSignUpStatusMessage.postValue(it.body()?.message)
                }, {})
        )
    }

    fun editProfile() {
        val myProfile = MyViewEditRequest(
            userName = userName.value.toString(),
            address = userRegionId.value!!,
            category = userExerciseIdList.value!!,
            email = userEmail.value.toString(),
            intro = userIntroduceMyself.value.toString(),
            nickName = userNickname.value.toString()
        )
        compositeDisposable.add(
            RemoteDataSourceImpl().putMyProfile(myProfile)
                .applySchedulers()
                .subscribe({
                    _postSignUpStatus.postValue(it.status)
                    _postSignUpStatusMessage.postValue(it.message)
                },{})
        )
    }
}

