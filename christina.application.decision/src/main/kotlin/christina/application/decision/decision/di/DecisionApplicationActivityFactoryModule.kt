package christina.application.decision.decision.di

import android.app.Activity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import christina.application.decision.decisions_viewer.DecisionsViewerActivity
import christina.application.decision.decisions_viewer.di.DecisionsViewerActivityComponent
import christina.application.decision.objects_viewer.ObjectsViewerActivity
import christina.application.decision.objects_viewer.di.ObjectsViewerActivityComponent
import christina.library.android.architecture.mvp.di.scope.ApplicationScope

@ApplicationScope
@Module
interface DecisionApplicationActivityFactoryModule {
    @Binds
    @IntoMap
    @ActivityKey(ObjectsViewerActivity::class)
    fun bindObjectsViewerActivityInjectorFactory(builder: ObjectsViewerActivityComponent.Builder): AndroidInjector.Factory<out Activity>

    @Binds
    @IntoMap
    @ActivityKey(DecisionsViewerActivity::class)
    fun bindDecisionsViewerActivityInjectorFactory(builder: DecisionsViewerActivityComponent.Builder): AndroidInjector.Factory<out Activity>
}