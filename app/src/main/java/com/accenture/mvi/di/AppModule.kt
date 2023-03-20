package com.accenture.mvi.di

import androidx.lifecycle.SavedStateHandle
import com.accenture.mvi.BuildConfig
import com.accenture.mvi.data.PokemonAPI
import com.accenture.mvi.data.Repository
import com.accenture.mvi.domain.LoadListUseCase
import com.accenture.mvi.domain.LoadListWithDetailUseCase
import com.accenture.mvi.presentation.DetailViewModel
import com.accenture.mvi.presentation.ListViewModel
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    single {
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .callTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(3, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.HOST + BuildConfig.PATH)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single {
        GsonBuilder()
            .create()
    }

    single {
        get<Retrofit>().create(PokemonAPI::class.java)
    }

    single { Repository(get()) }

    single { SavedStateHandle() }
    viewModel { ListViewModel(get()) }
    viewModel { DetailViewModel() }

    single { LoadListUseCase(get()) }
    single { LoadListWithDetailUseCase(get()) }
}