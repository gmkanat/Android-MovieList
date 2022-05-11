package com.example.app

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.app.classes.Movie
import com.example.app.room.AppDatabase
import com.example.app.room.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield

class MovieViewModel(application: Application): AndroidViewModel(application) {
    private val repository: MovieRepository

    private val _movies = MutableLiveData<List<Movie>>()
    var readAll: LiveData<List<Movie>> =  _movies

    init {
        val noteDao = AppDatabase.getDatabase(application).movieDao()
        repository = MovieRepository(noteDao)
    }

    fun makeSearch(string: String) {
        viewModelScope.launch{
            repository.makeSearch(_movies, string)
        }
    }

    fun getPopular() {
        viewModelScope.launch(Dispatchers.IO){
            repository.getPopular(_movies)
//            repository.getAll()
        }
    }
    fun getAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAll(_movies)
        }
    }
    fun addMovie(movie: Movie){
        viewModelScope.launch(Dispatchers.IO){
            repository.addMovie(movie)
        }
    }

    fun addMovies(lst: List<Movie>){
        viewModelScope.launch(Dispatchers.IO){
            repository.addMovies(lst)
        }
    }

    fun deleteMovie(id: Int){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteMovie(id)
        }
    }

    fun deleteMovies(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteMovies()
        }
    }

}