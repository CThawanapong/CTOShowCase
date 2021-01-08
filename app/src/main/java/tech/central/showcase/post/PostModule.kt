package tech.central.showcase.post

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import tech.central.showcase.di.qualifier.ActivityContext

@Module
class PostModule {
    @Provides
    fun provideLinearLayoutManager(@ActivityContext context: Context): LinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    @Provides
    fun provideDividerItemDecoration(@ActivityContext context: Context): DividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
}