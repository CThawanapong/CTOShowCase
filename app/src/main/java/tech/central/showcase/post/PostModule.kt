package tech.central.showcase.post

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyItemSpacingDecorator
import dagger.Module
import dagger.Provides
import tech.central.showcase.R
import tech.central.showcase.di.qualifier.ActivityContext

@Module
class PostModule {
    @Provides
    fun provideLayoutManager(@ActivityContext context: Context) = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

    @Provides
    fun provideItemDecoration(@ActivityContext context: Context) = DividerItemDecoration(context, RecyclerView.VERTICAL)
}