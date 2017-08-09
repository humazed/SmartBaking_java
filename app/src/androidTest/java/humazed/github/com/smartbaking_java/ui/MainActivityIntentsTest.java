package humazed.github.com.smartbaking_java.ui;


import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import humazed.github.com.smartbaking_java.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static humazed.github.com.smartbaking_java.ui.MainActivity.KEY_RECIPE;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityIntentsTest {

    @Rule
    public IntentsTestRule<MainActivity> mMainActivityIntentsTestRule = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void validateIntent() {
        IdlingRegistry.getInstance().register(mMainActivityIntentsTestRule.getActivity().mCountingIdlingResource);

        onView(allOf(withId(R.id.recyclerView), isDisplayed()))
                .perform(actionOnItemAtPosition(0, click()));

        intended(hasExtraWithKey(KEY_RECIPE));
    }
}
