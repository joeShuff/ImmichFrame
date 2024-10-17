package uk.co.joeshuff.immichframe.viewmodels

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import uk.co.joeshuff.immichframe.prefs.usecases.GetIsLoggedInUseCase
import uk.co.joeshuff.immichframe.testHelpers.simpleFlowTest

class FrameActivityViewModelTest {

    val getIsLoggedInUseCase: GetIsLoggedInUseCase = mockk(relaxed = true)

    val viewModel = FrameActivityViewModel(getIsLoggedInUseCase)

    @Test
    fun `initial state is loading`() = runTest {
        viewModel.state.simpleFlowTest {
            assertThat(awaitItem()).isEqualTo(FrameActivityViewModel.State.Loading)
        }
    }

    @Test
    fun `no user logged in, calculate state sets state to no server`() = runTest {
        coEvery { getIsLoggedInUseCase() } returns false

        viewModel.calculateState()

        viewModel.state.simpleFlowTest {
            assertThat(awaitItem()).isEqualTo(FrameActivityViewModel.State.OnboardingRequired.NoServer)
        }
    }

    @Test
    fun `user is logged in, calculateState sets state to display media`() = runTest {
        coEvery { getIsLoggedInUseCase() } returns true

        viewModel.calculateState()

        viewModel.state.simpleFlowTest {
            assertThat(awaitItem()).isEqualTo(FrameActivityViewModel.State.DisplayMedia)
        }
    }
}