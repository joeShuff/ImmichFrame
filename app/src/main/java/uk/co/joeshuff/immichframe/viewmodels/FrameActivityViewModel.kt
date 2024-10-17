package uk.co.joeshuff.immichframe.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.co.joeshuff.immichframe.prefs.usecases.GetIsLoggedInUseCase
import javax.inject.Inject

@HiltViewModel
class FrameActivityViewModel @Inject constructor(
    private val getIsLoggedInUseCase: GetIsLoggedInUseCase
): ViewModel() {

    sealed class State {
        sealed class OnboardingRequired: State() {
            data object NoServer: OnboardingRequired()
            data object NoMediaSource: OnboardingRequired()
        }

        data object DisplayMedia: State()
    }

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.DisplayMedia)
    val state = _state.asStateFlow()

    init {
        calculateState()
    }

    /**
     * This function will check the state of the app to decide whether any onboarding is needed
     * or to just display media
     */
    fun calculateState() = viewModelScope.launch {
        val isLoggedIn =  getIsLoggedInUseCase()

        if (isLoggedIn) {
            _state.update { State.OnboardingRequired.NoServer }
            return@launch
        }

        //TODO: Check if no albums to onboard adding sources

        _state.update { State.DisplayMedia }
    }

}