package com.ratanapps.consultoapp.di

import android.content.Context
import androidx.room.Room
import com.ratanapps.consultoapp.R
import com.ratanapps.consultoapp.data.local.db.ConsultoDb
import com.ratanapps.consultoapp.data.remote.AuthApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Named
import jakarta.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): ConsultoDb {
        return Room.databaseBuilder(
            context,
            ConsultoDb::class.java,
            "consulto_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.example.com/") // Replace with actual base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    // Generic helper (though Hilt typically uses specific provides)
    inline fun <reified T> createApiService(retrofit: Retrofit): T {
        return retrofit.create(T::class.java)
    }

    @Provides
    @Singleton
    @Named("web_client_id")
    fun provideFirebaseAuthIdToken(@ApplicationContext context: Context): String {
        return context.getString(R.string.default_web_client_id)
    }
}
