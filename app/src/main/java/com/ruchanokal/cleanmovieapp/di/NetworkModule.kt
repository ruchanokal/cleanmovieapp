package com.ruchanokal.cleanmovieapp.di

import com.ruchanokal.cleanmovieapp.data.remote.api.MovieAPI
import com.ruchanokal.cleanmovieapp.data.repository.MovieRepositoryImpl
import com.ruchanokal.cleanmovieapp.domain.repository.MovieRepository
import com.ruchanokal.cleanmovieapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideMovieAPIService() : MovieAPI{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(api : MovieAPI) : MovieRepository {
        return MovieRepositoryImpl(api = api)
    }


}