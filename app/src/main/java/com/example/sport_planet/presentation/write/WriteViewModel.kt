package com.example.sport_planet.presentation.write

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.presentation.base.BaseViewModel
import com.example.sport_planet.remote.RemoteDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import java.util.*

class WriteViewModel(private val remote: RemoteDataSource) : BaseViewModel() {

    fun getBoardContent(boardId: Long) {
        remote.getBoardContent(boardId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.onNext(true) }
            .doAfterTerminate { isLoading.onNext(false) }
            .subscribe({}, { it.printStackTrace() })
            .addTo(compositeDisposable)
    }

    fun postBoard(
        title: String,
        content: String,
        category: Long,
        city: Long,
        userTag: Long,
        recruitNumber: Int,
        date: Date,
        place: String
    ) {
        remote.postBoard(title, content, category, city, userTag, recruitNumber, date, place)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.onNext(true) }
            .doAfterTerminate { isLoading.onNext(false) }
            .subscribe({

            }, {
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
    }

    fun editBoard(
        boardId: Long,
        title: String,
        content: String,
        category: Long,
        city: Long,
        userTag: Long,
        recruitNumber: Int,
        date: Date,
        place: String
    ) {
        remote.editBoard(
            boardId,
            title,
            content,
            category,
            city,
            userTag,
            recruitNumber,
            date,
            place
        ).observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.onNext(true) }
            .doAfterTerminate { isLoading.onNext(false) }
            .subscribe({}, {
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
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