package uk.co.joeshuff.immichframe.testHelpers

import app.cash.turbine.FlowTurbine
import app.cash.turbine.test
import kotlinx.coroutines.flow.Flow

suspend fun <T> Flow<T>.simpleFlowTest(flowContent: suspend FlowTurbine<T>.() -> Unit) {
    this.test {
        flowContent()
        cancelAndConsumeRemainingEvents()
    }
}
