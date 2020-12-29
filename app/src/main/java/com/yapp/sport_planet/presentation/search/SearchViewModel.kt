package com.yapp.sport_planet.presentation.search

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yapp.sport_planet.presentation.base.BaseViewModel
import io.reactivex.subjects.PublishSubject
import org.json.JSONArray
import org.json.JSONException

class SearchViewModel(private val pref: SharedPreferences) : BaseViewModel() {
    val itemList: MutableLiveData<ArrayList<String>> = MutableLiveData()
//    private var _itemList: ArrayList<String> = ArrayList()
//        set(value) = {
//            _itemList = value
//            itemList
//        }

    val changeItemList: PublishSubject<List<String>> = PublishSubject.create()

    fun putRecentSearchList(item: String) {
        val items: ArrayList<String> = itemList.value ?: arrayListOf()
//        if (items.size >= 5) {
//            items.removeAt(0)
//        }
        items.add(item)
        val a = JSONArray()
        for (i in 0 until items.size) {
            a.put(items[i])
        }
        if (items.isNotEmpty()) {
            pref.edit().putString(RECENT_SEARCH_LIST, a.toString()).apply()
        } else {
            pref.edit().putString(RECENT_SEARCH_LIST, null).apply()
        }

        getRecentSearchList()
    }

    fun deleteRecentSearchList(position: Int) {
        val items: ArrayList<String> = itemList.value ?: arrayListOf()
        items.removeAt(position)

        val a = JSONArray()
        for (i in 0 until items.size) {
            a.put(items[i])
        }
        if (items.isNotEmpty()) {
            pref.edit().putString(RECENT_SEARCH_LIST, a.toString()).apply()
        } else {
            pref.edit().putString(RECENT_SEARCH_LIST, null).apply()
        }

        getRecentSearchList()
    }

    fun clearRecentSearchList() {
        pref.edit().clear().apply()
        getRecentSearchList()
    }

    fun getRecentSearchList() {
        val json = pref.getString(RECENT_SEARCH_LIST, null)
        val items = ArrayList<String>()
        if (json != null) {
            try {
                val a = JSONArray(json)
                for (i in 0 until a.length()) {
                    val item = a.optString(i)
                    items.add(item)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        itemList.value = items
    }

    companion object {
        const val RECENT_SEARCH_LIST = "RECENT_SEARCH_LIST"
    }
}

class SearchViewModelFactory(private val pref: SharedPreferences) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            SearchViewModel(pref) as T
        } else {
            throw IllegalArgumentException()
        }
    }

}