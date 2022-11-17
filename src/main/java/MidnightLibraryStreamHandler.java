import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import handlers.*;
import requestinterceptors.DialogManagementStateInterceptor;
import responseinterceptors.ResponseLogger;

public class MidnightLibraryStreamHandler extends SkillStreamHandler {

    private static Skill getSkill() {
        return Skills.standard()
                .addRequestInterceptor(new DialogManagementStateInterceptor())
                .addRequestHandlers(
                        new LaunchHandler(),
                        new HelpIntentHandler(),
                        new NameIntentHandler(),
                        new BookInformationIntentHandler(),
                        new BookAuthorIntentHandler(),
                        new BookRatingIntentHandler(),
                        new BookSummaryIntentHandler(),
                        new InProgressBookRecommendationIntentHandler(),
                        new CompletedBookRecommendationIntentHandler(),
                        new ExplainBookTypesIntentHandler(),
                        new ExitIntentHandler()
                )
                .addResponseInterceptor(new ResponseLogger())
                .withTableName("midnight_library_users")
                // Add your skill id below
                //withSkillId("amzn1.ask.skill.9aecbb47-fe9d-46ac-984b-7748520e6a11")
                .build();
    }

    public MidnightLibraryStreamHandler() {
        super(getSkill());
    }

}
