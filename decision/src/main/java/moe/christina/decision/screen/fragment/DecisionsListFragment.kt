package moe.christina.decision.screen.fragment

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import moe.christina.common.android.AndroidConstant
import moe.christina.common.android.coordination.LoadingViewVisibilityCoordinator
import moe.christina.decision.R
import moe.christina.decision.model.Decision
import moe.christina.decision.screen.DecisionsListScreen
import moe.christina.decision.screen.adapter.DecisionsListAdapter
import moe.christina.mvp.presenter.Presenter
import moe.christina.mvp.screen.behavior.ListScreenBehavior
import moe.christina.mvp.screen.behavior.ListScreenBehavior.ViewItemsEvent
import moe.christina.mvp.screen.behavior.ListScreenBehaviorDelegate
import moe.christina.mvp.screen.behavior.ListScreenBehaviorDelegate.Companion.asItemRefresher
import moe.christina.mvp.screen.behavior.ListScreenBehaviorDelegate.Companion.asItemsConsumer
import javax.inject.Inject

class DecisionsListFragment(private val listScreenBehaviorDelegate: ListScreenBehaviorDelegate<Decision>)
    : BaseDecisionFragment(), DecisionsListScreen, ListScreenBehavior<Decision> by listScreenBehaviorDelegate {
    companion object {
        @JvmStatic
        private val LOG_TAG = AndroidConstant.logTag<DecisionsListFragment>()

        @JvmStatic
        fun newInstance(): DecisionsListFragment {
            return DecisionsListFragment()
        }
    }

    constructor() : this(ListScreenBehaviorDelegate())

    private val decisionsAdapter = DecisionsListAdapter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_decisions_list, container, false)?.apply {
            listScreenBehaviorDelegate.apply {
                visibilityCoordinator = LoadingViewVisibilityCoordinator().apply {
                    contentView = (findViewById(R.id.content) as RecyclerView).apply {
                        adapter = decisionsAdapter
                        layoutManager = LinearLayoutManager(context)
                    }

                    noContentView = findViewById(R.id.no_content)
                    loadingView = findViewById(R.id.loading)
                    errorView = findViewById(R.id.loading_error)
                }
                itemsRefresher = (findViewById(R.id.refresh) as SwipeRefreshLayout).asItemRefresher()
                itemsConsumer = decisionsAdapter.asItemsConsumer()
            }
        }
    }

    @CallSuper
    override fun onResume() {
        super.onResume()

        listScreenBehaviorDelegate.riseViewItemsEvent(ViewItemsEvent.NEW)
    }

    @CallSuper
    override fun onInjectMembers() {
        super.onInjectMembers()

        decisionSubscreenComponent.inject(this)

        presenter.bindScreen(this)
    }

    @CallSuper
    override fun onReleaseInjectedMembers() {
        super.onReleaseInjectedMembers()

        presenter.unbindScreen()
    }

    @Inject
    lateinit var presenter: Presenter<DecisionsListScreen>
}
