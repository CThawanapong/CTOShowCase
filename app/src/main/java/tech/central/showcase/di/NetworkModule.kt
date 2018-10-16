package tech.central.showcase.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import tech.central.showcase.BuildConfig
import tech.central.showcase.base.service.MockService
import tech.central.showcase.di.qualifier.ApplicationContext
import java.io.File
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
class NetworkModule {
    companion object {
        @JvmStatic
        private val TAG = NetworkModule::class.java.simpleName
    }

    @Singleton
    @Provides
    fun provideGson() = GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()

    @Singleton
    @Provides
    fun provideInterceptor() = LoggingInterceptor.Builder()
            .loggable(BuildConfig.DEBUG)
            .setLevel(Level.HEADERS)
            .request(TAG)
            .response(TAG)
            .log(Platform.INFO)
            .executor(Executors.newSingleThreadExecutor())
            .build()

    @Singleton
    @Provides
    fun provideCache(@ApplicationContext context: Context) = Cache(File(context.cacheDir, "http"), 30 * 1024 * 1024)

    @Singleton
    @Provides
    fun provideHttpClient(cache: Cache, loggingInterceptor: LoggingInterceptor, @ApplicationContext context: Context): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .apply {
                    if (BuildConfig.DEBUG || BuildConfig.FLAVOR == "dev") {
                        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                            @Throws(CertificateException::class)
                            override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                            }

                            @Throws(CertificateException::class)
                            override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                            }

                            override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                                return arrayOf()
                            }
                        })

                        val sslContext = SSLContext.getInstance("SSL")
                        sslContext.init(null, trustAllCerts, SecureRandom())
                        sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
                        hostnameVerifier { _, _ -> true }
                    }
                }
                .followRedirects(false)
                .cache(cache)
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    val chainBuilder = chain.request().newBuilder()

                    val request = chainBuilder.build()
                    chain.proceed(request)
                }
                .addInterceptor(loggingInterceptor)
        return builder.build()
    }

    @Singleton
    @Named("MOCK_RETROFIT")
    @Provides
    fun provideMockRetrofit(@Named("MOCK_ENDPOINT") endpoint: String, gson: Gson, okHttpClient: OkHttpClient) = Retrofit.Builder()
            .baseUrl(endpoint)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideMockService(@Named("MOCK_RETROFIT") retrofit: Retrofit) = retrofit.create(MockService::class.java)
}