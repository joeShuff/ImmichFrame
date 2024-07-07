package uk.co.joeshuff.immichframe.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.co.joeshuff.immichframe.data.api.HostChangeInterceptor
import uk.co.joeshuff.immichframe.data.api.ImmichUnauthorizedAPI
import uk.co.joeshuff.immichframe.data.api.repository.ImmichVerificationRepositoryImpl
import uk.co.joeshuff.immichframe.domain.repository.ImmichVerificationRepository
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ImmichFrameBindsModule {

    @Singleton
    @Binds
    abstract fun provideImmichVerificationRepository(impl: ImmichVerificationRepositoryImpl): ImmichVerificationRepository

}

@Module
@InstallIn(SingletonComponent::class)
class ImmichFrameModule {
    //region OKHttpClients
    @Singleton
    @Provides
    @Named("authorized")
    fun provideAuthorizedOkHttpClient(hostChangeInterceptor: HostChangeInterceptor): OkHttpClient =
        run {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                //todo: add auth interceptor
                .addInterceptor(hostChangeInterceptor)
                .build()
        }

    @Singleton
    @Provides
    @Named("unauthorized")
    fun provideUnauthorizedOkHttpClient(hostChangeInterceptor: HostChangeInterceptor): OkHttpClient =
        run {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(hostChangeInterceptor)
                .build()
        }
    //endregion

    //region RetroFit
    @Singleton
    @Provides
    @Named("authorized")
    fun provideRetroFit(@Named("authorized") okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://localhost")
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    @Named("unauthorized")
    fun providesUnauthorizedRetroFit(@Named("unauthorized") okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://localhost")
            .client(okHttpClient)
            .build()

    //endregionØ

    @Singleton
    @Provides
    fun provideUnauthorizedApi(@Named("unauthorized") retrofit: Retrofit) =
        retrofit.create(ImmichUnauthorizedAPI::class.java)
}