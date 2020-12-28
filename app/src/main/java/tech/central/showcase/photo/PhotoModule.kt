package tech.central.showcase.photo

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyItemSpacingDecorator
import dagger.Module
import dagger.Provides
import tech.central.showcase.R
import tech.central.showcase.di.qualifier.ActivityContext
import javax.inject.Named

@Module
class PhotoModule {
    @Provides
    @Named("GRID_LAYOUT_MANAGER")
    fun provideLayoutManager(@ActivityContext context: Context) = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)

    @Provides
    @Named("GRID_DECORATOR")
    fun provideItemDecoration(@ActivityContext context: Context) = EpoxyItemSpacingDecorator(context.resources.getDimensionPixelSize(R.dimen.spacing_photo))
}