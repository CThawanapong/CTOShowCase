package tech.central.showcase.posts

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyItemSpacingDecorator
import dagger.Module
import dagger.Provides
import tech.central.showcase.R
import tech.central.showcase.di.qualifier.ActivityContext



@Module
class PostItemModule {
    @Provides
    fun provideLayoutManager(@ActivityContext context: Context) = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    @Provides
    fun provideItemDecoration(@ActivityContext context: Context) = EpoxyItemSpacingDecorator(context.resources.getDimensionPixelSize(R.dimen.spacing_photo))
}