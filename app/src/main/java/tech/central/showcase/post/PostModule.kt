package tech.central.showcase.post

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import tech.central.showcase.di.qualifier.ActivityContext
import javax.inject.Named

@Module
class PostModule {
    @Provides
    @Named("LINEAR_LAYOUT_MANAGER")
    fun provideLayoutManager(@ActivityContext context: Context) = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    @Provides
    @Named("LINEAR_DECORATOR")
    fun provideItemDecoration(@ActivityContext context: Context) = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
}