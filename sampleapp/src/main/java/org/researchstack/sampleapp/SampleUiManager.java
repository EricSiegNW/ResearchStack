package org.researchstack.sampleapp;
import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.answerformat.ChoiceAnswerFormat;
import org.researchstack.backbone.model.Choice;
import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.step.QuestionStep;
import org.researchstack.backbone.step.Step;
import org.researchstack.skin.ActionItem;
import org.researchstack.skin.UiManager;
import org.researchstack.skin.task.OnboardingTask;
import org.researchstack.skin.ui.LearnActivity;
import org.researchstack.skin.ui.fragment.ActivitiesFragment;
import org.researchstack.skin.ui.fragment.DashboardFragment;

import java.util.ArrayList;
import java.util.List;

public class SampleUiManager extends UiManager
{
    /**
     * @return List of ActionItems w/ Fragment class items
     */
    @Override
    public List<ActionItem> getMainTabBarItems()
    {
        List<ActionItem> navItems = new ArrayList<>();

        navItems.add(new ActionItem.ActionItemBuilder().setId(R.id.nav_activities)
                .setTitle(R.string.rss_activities)
                .setIcon(R.drawable.ic_tab_activities)
                .setClass(ActivitiesFragment.class)
                .build());

        navItems.add(new ActionItem.ActionItemBuilder().setId(R.id.nav_dashboard)
                .setTitle(R.string.rss_dashboard)
                .setIcon(R.drawable.ic_tab_dashboard)
                .setClass(DashboardFragment.class)
                .build());

        return navItems;
    }

    /**
     * @return List of ActionItems w/ Activity class items. The class items are then used to
     * construct an intent for a MenuItem when {@link org.researchstack.skin.ui.MainActivity#onCreateOptionsMenu}
     * is called
     */
    @Override
    public List<ActionItem> getMainActionBarItems()
    {
        List<ActionItem> navItems = new ArrayList<>();

        navItems.add(new ActionItem.ActionItemBuilder().setId(R.id.nav_learn)
                .setTitle(R.string.rss_learn)
                .setIcon(R.drawable.ic_action_info)
                .setClass(LearnActivity.class)
                .build());

        navItems.add(new ActionItem.ActionItemBuilder().setId(R.id.nav_settings)
                .setTitle(R.string.rss_settings)
                .setIcon(R.drawable.ic_action_settings)
                .setClass(SampleSettingsActivity.class)
                .build());

        return navItems;
    }

    /**
     * TODO Refactor into a framework step builder *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
     * This needs to change, strange that UIManager is handling step creation of OnboardingTask.
     * We should have a place where all the steps are created and returned if dev wants to edit any
     * step within said task.
     *
     * @return InclusionCriteria Step
     */
    @Override
    public Step getInclusionCriteriaStep()
    {
        Choice<Boolean> human = new Choice<>("Yes, I am a human.", true, null);
        Choice<Boolean> robot = new Choice<>(
                "No, I am a robot but I am sentient and concerned about my health.",
                true,
                null);
        Choice<Boolean> alien = new Choice<>("No, I’m an alien.", false, null);

        QuestionStep step = new QuestionStep(OnboardingTask.SignUpInclusionCriteriaStepIdentifier);
        step.setStepTitle(R.string.rss_eligibility);
        step.setTitle("Were you born somewhere on planet earth and are you a human-ish?");
        step.setAnswerFormat(new ChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle.SingleChoice,
                human,
                robot,
                alien));

        return step;
    }

    @Override
    public boolean isInclusionCriteriaValid(StepResult stepResult)
    {
        if(stepResult != null)
        {
            return ((StepResult<Boolean>) stepResult).getResult();
        }

        return false;
    }

    @Override
    public boolean isSignatureEnabledInConsent()
    {
        return true;
    }

    @Override
    public boolean isConsentSkippable()
    {
        return false;
    }

}
