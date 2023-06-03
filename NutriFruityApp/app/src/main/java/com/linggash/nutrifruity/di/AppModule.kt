package com.linggash.nutrifruity.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.linggash.nutrifruity.data.FruitRemoteMediator
import com.linggash.nutrifruity.database.FruitDataItem
import com.linggash.nutrifruity.database.FruitDatabase
import com.linggash.nutrifruity.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFruitDatabase(@ApplicationContext context: Context): FruitDatabase {
        return Room.databaseBuilder(
            context,
            FruitDatabase::class.java,
            "fruit.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provide(): ApiService{
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//            val loggingInterceptor = if(BuildConfig.DEBUG) {
//                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//            } else {
//                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
//            }
//            val authInterceptor = Interceptor { chain ->
//                val req = chain.request()
//                val requestHeaders = req.newBuilder()
//                    .addHeader("Authorization", BuildConfig.KEY)
//                    .build()
//                chain.proceed(requestHeaders)
//            }
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
//                .addInterceptor(authInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://nutrifruity1-qfwlnztfjq-as.a.run.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideFruitPager(fruitDb: FruitDatabase, apiService: ApiService): Pager<Int, FruitDataItem> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
            ),
            remoteMediator = FruitRemoteMediator(fruitDb, apiService),
            pagingSourceFactory = {
                fruitDb.fruitDao().getAllFruit()
            }
        )
    }
}