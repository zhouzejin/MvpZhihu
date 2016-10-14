package com.sunny.mvpzhihu;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.util.List;

import rx.Observable;
import com.sunny.mvpzhihu.data.model.bean.Subject;
import com.sunny.mvpzhihu.test.common.TestComponentRule;
import com.sunny.mvpzhihu.test.common.TestDataFactory;
import com.sunny.mvpzhihu.ui.main.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    public final TestComponentRule component =
            new TestComponentRule(InstrumentationRegistry.getTargetContext());
    public final ActivityTestRule<MainActivity> main =
            new ActivityTestRule<MainActivity>(MainActivity.class, false, false) {
                @Override
                protected Intent getActivityIntent() {
                    // Override the default intent so we pass a false flag for syncing so it doesn't
                    // start a sync service in the background that would affect  the behaviour of
                    // this test.
                    return MainActivity.getStartIntent(
                            InstrumentationRegistry.getTargetContext(), false);
                }
            };

    // TestComponentRule needs to go first to make sure the Dagger ApplicationTestComponent is set
    // in the Application before any Activity is launched.
    @Rule
    public final TestRule chain = RuleChain.outerRule(component).around(main);

    @Test
    public void listOfSubjectsShows() {
        List<Subject> testDataSubjects = TestDataFactory.makeListSubject(20);
        when(component.getMockDataManager().getSubjects())
                .thenReturn(Observable.just(testDataSubjects));

        main.launchActivity(null);

        int position = 0;
        for (Subject subject : testDataSubjects) {
            onView(withId(R.id.recycler_view))
                    .perform(RecyclerViewActions.scrollToPosition(position));
            String genres = subject.genres().toString();
            onView(withText(genres))
                    .check(matches(isDisplayed()));
            onView(withText(subject.title()))
                    .check(matches(isDisplayed()));
            position++;
        }
    }

}
