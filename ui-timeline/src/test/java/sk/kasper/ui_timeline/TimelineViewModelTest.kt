package sk.kasper.ui_timeline

import com.google.common.truth.Truth.assertThat
import com.google.common.truth.Truth.assertWithMessage
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.threeten.bp.LocalDateTime
import org.threeten.bp.Month
import sk.kasper.domain.model.FilterSpec
import sk.kasper.domain.model.SuccessResponse
import sk.kasper.domain.usecase.timeline.GetTimelineItems
import sk.kasper.domain.usecase.timeline.RefreshTimelineItems
import sk.kasper.domain.utils.createLaunch
import sk.kasper.ui_common.rocket.RocketMapper
import sk.kasper.ui_common.settings.SettingsManager
import sk.kasper.ui_common.tag.TagMapper
import sk.kasper.ui_timeline.utils.ReducerCoroutineRule


private val LOCAL_DATE_TIME_NOW: LocalDateTime = LocalDateTime.of(2000, Month.JANUARY, 1, 12, 0)

class TimelineViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var reducerCoroutineRule = ReducerCoroutineRule()

    @Test
    fun noLaunches_noItems() = timelineListTest {
        expectingNoMatchingLaunchesVisible = false
        expectingRetryToLoadLaunchesVisible = true
    }

    @Test
    fun singleLaunchToday_todayLabelAndOneItem() = timelineListTest {
        accurate launchAt LOCAL_DATE_TIME_NOW.plusHours(2)

        expecting item LabelListItem.Today
        expecting item launch

        expectingNoMatchingLaunchesVisible = false
        expectingRetryToLoadLaunchesVisible = false
    }

    @Test
    fun singleLaunchThisWeek_thisWeekLabelAndOneItem() = timelineListTest {
        accurate launchAt LOCAL_DATE_TIME_NOW.plusDays(4)

        expecting item LabelListItem.ThisWeek
        expecting item launch

        expectingRetryToLoadLaunchesVisible = false
    }

    @Test
    fun singleLaunchAfterThisWeek_monthLabelAndOneItem() = timelineListTest {
        accurate launchAt LOCAL_DATE_TIME_NOW.plusDays(10)

        expecting item LabelListItem.Month(1)
        expecting item launch
    }

    @Test
    fun singleLaunchTomorrow_tomorrowLabelAndOneItem() = timelineListTest {
        accurate launchAt LOCAL_DATE_TIME_NOW.plusHours(25)

        expecting item LabelListItem.Tomorrow
        expecting item launch
    }

    @Test
    fun singleLaunchTomorrow_inaccurate_monthLabelAndOneItem() = timelineListTest {
        inaccurate launchAt LOCAL_DATE_TIME_NOW.plusHours(25)

        expecting item LabelListItem.Month(1)
        expecting item inaccurate
    }

    @Test
    fun singleLaunchTomorrow_inaccurate_unconfirmedLaunchesForbidden_noItems() = timelineListTest {
        showUnconfirmedLaunches = false

        inaccurate launchAt LOCAL_DATE_TIME_NOW.plusHours(25)

        expectingRetryToLoadLaunchesVisible = true
    }

    @Test
    fun singleLaunchEarlierToday_monthLabelAndOneItem() = timelineListTest {
        accurate launchAt LOCAL_DATE_TIME_NOW.minusHours(4)

        expecting item LabelListItem.Today
        expecting item launch
    }

    @Test
    fun singleLaunchYesterday_noItems() = timelineListTest {
        accurate launchAt LOCAL_DATE_TIME_NOW.minusDays(1)

        expectingRetryToLoadLaunchesVisible = true
    }

    @Test
    fun oneInaccurateOneAccurate_inaccurateIsLast() = timelineListTest {
        inaccurate launchAt LOCAL_DATE_TIME_NOW.plusDays(1)
        accurate launchAt LOCAL_DATE_TIME_NOW.plusDays(5)

        expecting item LabelListItem.ThisWeek
        expecting item accurate
        expecting item LabelListItem.Month(1)
        expecting item inaccurate
    }

    @Test
    fun twoLaunchesTomorrow_tomorrowLabelAndTwoItems() = timelineListTest {
        LOCAL_DATE_TIME_NOW plusHours 25 isLaunchTimeOf launch
        LOCAL_DATE_TIME_NOW plusHours 26 isLaunchTimeOf launch

        expecting item LabelListItem.Tomorrow
        expecting item launch
        expecting item launch
    }

    private infix fun LocalDateTime.plusHours(hours: Long): LocalDateTime {
        return this.plusHours(hours)
    }

    private fun timelineListTest(given: suspend TimelineListTestBuilder.() -> Unit) = runBlocking {
        TimelineListTestBuilder().apply {
            given()
            check()
        }

        Unit
    }

    private class TimelineListTestBuilder {

        @Mock
        private lateinit var getTimelineItems: GetTimelineItems

        @Mock
        private lateinit var refreshTimelineItems: RefreshTimelineItems

        @Mock
        private lateinit var settingsManager: SettingsManager

        @Mock
        private lateinit var tagMapper: TagMapper

        @Mock
        private lateinit var rocketMapper: RocketMapper

        private lateinit var viewModel: TimelineViewModelUnderTest

        private val launches = mutableListOf<sk.kasper.domain.model.Launch>()
        private val expectedItems = mutableListOf<ExpectedListItem>()

        val accurate = true
        val launch = true
        val inaccurate = false
        val expecting = "expecting"

        var showUnconfirmedLaunches = true

        var expectingNoMatchingLaunchesVisible = false
        var expectingRetryToLoadLaunchesVisible = false

        init {
            MockitoAnnotations.initMocks(this)
        }

        suspend fun check() {
            whenever(getTimelineItems.getTimelineItems(FilterSpec.EMPTY_FILTER)).thenReturn(
                SuccessResponse(launches)
            )
            whenever(settingsManager.showUnconfirmedLaunches).thenReturn(showUnconfirmedLaunches)

            viewModel = TimelineViewModelUnderTest()

            assertThat(expectedItems.size)
                .isEqualTo(getCurrentState().timelineItems.size)

            assertThat(getCurrentState().showNoMatchingLaunches)
                .isEqualTo(expectingNoMatchingLaunchesVisible)

            assertThat(getCurrentState().showRetryToLoadLaunches)
                .isEqualTo(expectingRetryToLoadLaunchesVisible)

            assertThat(getCurrentState().progressVisible)
                .isEqualTo(false)

            expectedItems.forEachIndexed { index, expectedListItem ->
                val actualListItem = getCurrentTimelineItems()[index]

                when (expectedListItem) {
                    is ExpectedListItem.Launch -> {
                        assertThat(actualListItem is LaunchListItem)
                            .isTrue()

                        if (actualListItem is LaunchListItem) {
                            val message =
                                "of launch at position $index is expected to be accurate=${expectedListItem.accurate}"

                            assertWithMessage("date $message")
                                .that(actualListItem.accurateDate)
                                .isEqualTo(expectedListItem.accurate)

                            assertWithMessage("time $message")
                                .that(actualListItem.accurateTime)
                                .isEqualTo(expectedListItem.accurate)
                        }
                    }
                    is ExpectedListItem.Label -> {
                        assertThat(actualListItem is LabelListItem)
                            .isTrue()

                        if (actualListItem is LabelListItem) {
                            assertWithMessage("expected label ${expectedListItem.labelListItem.javaClass.simpleName} at position $index but was ${actualListItem.javaClass.simpleName}")
                                .that(actualListItem)
                                .isEqualTo(expectedListItem.labelListItem)
                        }
                    }
                }
            }
        }

        infix fun Boolean.launchAt(localDateTime: LocalDateTime) {
            launches.add(
                createLaunch(
                    launchDateTime = localDateTime,
                    accurateDate = this,
                    accurateTime = this
                )
            )
        }

        infix fun LocalDateTime.isLaunchTimeOf(accurate: Boolean) {
            launches.add(
                createLaunch(
                    launchDateTime = this,
                    accurateDate = accurate,
                    accurateTime = accurate
                )
            )
        }

        infix fun String.item(accurate: Boolean) {
            expectedItems.add(ExpectedListItem.Launch(accurate))
        }

        infix fun String.item(label: LabelListItem) {
            expectedItems.add(ExpectedListItem.Label(label))
        }

        private fun getCurrentTimelineItems() = viewModel.state.value.timelineItems

        private fun getCurrentState() = viewModel.state.value

        private sealed class ExpectedListItem {
            data class Launch(val accurate: Boolean) : ExpectedListItem()
            data class Label(val labelListItem: LabelListItem) : ExpectedListItem()
        }

        private inner class TimelineViewModelUnderTest : TimelineViewModel(
            getTimelineItems,
            refreshTimelineItems,
            settingsManager,
            tagMapper,
            rocketMapper
        ) {
            override fun getCurrentDateTime(): LocalDateTime = LOCAL_DATE_TIME_NOW
        }
    }

}