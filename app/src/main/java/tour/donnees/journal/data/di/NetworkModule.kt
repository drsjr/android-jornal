package tour.donnees.journal.data.di

import android.content.Context
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tour.donnees.journal.BuildConfig
import tour.donnees.journal.core.NoConnectionInterceptor
import tour.donnees.journal.domain.mapper.NewsMapper
import tour.donnees.journal.data.remote.api.JournalService
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideJournalService(@ApplicationContext context: Context): JournalService {
        val builder = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        builder.connectTimeout(15, TimeUnit.SECONDS)
        builder.readTimeout(15, TimeUnit.SECONDS)
        builder.addInterceptor(logging)
        builder.addInterceptor(NoConnectionInterceptor(context))

        return Retrofit.Builder()
            .client(builder.build())
            .baseUrl(BuildConfig.ENDPOINT)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(JournalService::class.java)
    }

    @Singleton
    @Provides
    fun provideNewsMapper(): NewsMapper {
        return NewsMapper()
    }
}