package com.example.movieapp.di

import android.app.Application
import androidx.room.Room
import com.example.movieapp.BuildConfig
import com.example.movieapp.DAO.MovieDAO
import com.example.movieapp.data.services.MovieAPI
import com.example.movieapp.data.utils.MovieDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)

object AppModule {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    @Provides
    @Singleton
    fun providesInterceptor(): Interceptor {
        return Interceptor {
            val originalRequest = it.request()
            val newHttpUrl = originalRequest.url.newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()

            val newRequest = originalRequest.newBuilder()
                .url(newHttpUrl)
                .build()
            it.proceed(newRequest)
        }
    }

    @Provides
    @Singleton
    fun providesClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesMovieApi(client: OkHttpClient): MovieAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
            .create(MovieAPI::class.java)
    }

    @Provides
    @Singleton
    fun providesMovieDB(app: Application): MovieDB {
        return Room.databaseBuilder(
            app,
            MovieDB::class.java,
            "MovieApp.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieDAO(database: MovieDB): MovieDAO {
        return database.movieDao
    }
}