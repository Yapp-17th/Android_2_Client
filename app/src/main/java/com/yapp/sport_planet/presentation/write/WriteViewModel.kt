package com.yapp.sport_planet.presentation.write

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yapp.sport_planet.data.model.BoardContentModel
import com.yapp.sport_planet.data.model.UserTagModel
import com.yapp.sport_planet.data.response.basic.ExerciseResponse
import com.yapp.sport_planet.data.response.basic.RegionResponse
import com.yapp.sport_planet.presentation.base.BaseViewModel
import com.yapp.sport_planet.remote.RemoteDataSource
import com.yapp.sport_planet.util.Util.toDateFormatForWrite
import com.yapp.sport_planet.util.Util.toDateFormatHasTime
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject

class WriteViewModel(private val remote: RemoteDataSource) : BaseViewModel() {
    val boardId: MutableLiveData<Long> = MutableLiveData()

    val exercise: MutableLiveData<ExerciseResponse.Data> = MutableLiveData()
    val city: MutableLiveData<RegionResponse.Data> = MutableLiveData()
    val userTag: MutableLiveData<UserTagModel> = MutableLiveData()
    val time: MutableLiveData<String> = MutableLiveData()
    val place: MutableLiveData<String> = MutableLiveData()
    val title: MutableLiveData<String> = MutableLiveData()
    val body: MutableLiveData<String> = MutableLiveData()
    val count: MutableLiveData<Int> = MutableLiveData()

    val showBoardContent: PublishSubject<BoardContentModel> = PublishSubject.create()
    val showFinishView: PublishSubject<Unit> = PublishSubject.create()
    val showPostError: PublishSubject<Unit> = PublishSubject.create()

    fun postBoard() {
        if (exercise.value == null ||
            city.value == null ||
            userTag.value == null ||
            time.value == null ||
            place.value == null ||
            title.value == null ||
            body.value == null ||
            count.value == null
        ) {
            showPostError.onNext(Unit)
            return
        }

        remote.postBoard(
            title = title.value!!,
            content = body.value!!,
            category = exercise.value!!.id,
            city = city.value!!.id,
            userTag = userTag.value!!.id,
            recruitNumber = count.value!!,
            date = toDateFormatForWrite(toDateFormatHasTime(time.value!!)),
            place = place.value!!
        ).observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.onNext(true) }
            .doAfterTerminate { isLoading.onNext(false) }
            .subscribe({
                if (it.success) {
                    showFinishView.onNext(Unit)
                }
            }, {
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
    }

    fun editBoard() {
        if (exercise.value == null ||
            city.value == null ||
            userTag.value == null ||
            time.value == null ||
            place.value == null ||
            title.value == null ||
            body.value == null ||
            count.value == null
        ) {
            showPostError.onNext(Unit)
            return
        }

        remote.editBoard(
            boardId = boardId.value!!,
            title = title.value!!,
            content = body.value!!,
            category = exercise.value!!.id,
            city = city.value!!.id,
            userTag = userTag.value!!.id,
            recruitNumber = count.value!!,
            date = toDateFormatForWrite(toDateFormatHasTime(time.value!!)),
            place = place.value!!
        ).observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.onNext(true) }
            .doAfterTerminate { isLoading.onNext(false) }
            .subscribe({
                if (it.success) {
                    showFinishView.onNext(Unit)
                }
            }, {
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
    }

    fun getBoardContent() {
        boardId.value?.let { boardId ->
            remote.getBoardContent(boardId)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isLoading.onNext(true) }
                .doAfterTerminate { isLoading.onNext(false) }
                .subscribe({
                    if (it.success) {
                        showBoardContent.onNext(it.data)
                    }
                }, {
                    it.printStackTrace()
                })
                .addTo(compositeDisposable)
        }
    }

    fun getDateToString(): String {
        time.value?.run {
            return toDateFormatHasTime(this)
        } ?: return ""
    }
}

class WriteViewModelFactory(private val remote: RemoteDataSource) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WriteViewModel::class.java)) {
            return WriteViewModel(remote) as T
        } else {
            throw IllegalArgumentException()
        }
    }

}