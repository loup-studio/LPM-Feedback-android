package lpm.feedback;

import android.content.Context;

public class FeedbackConfiguration {
    public static String title = "Donnez-nous votre avis";
    public static String ratingHint = "Etes-vous satisfait(e) de l'application ?";
    public static String feedbackHint = "Faites nous part de vos commentaires ou suggestions :";
    public static String rate0 = "Pas du tout satisfait(e)";
    public static String rate1 = "Pas très satisfait(e)";
    public static String rate2 = "Plutôt satisfait(e)";
    public static String rate3 = "Très satisfait(e)";
    public static String email = "";
    public static String api = "";
    public static FeedbackManager manager;

    public static void init(Context context) {
        manager = new FeedbackManager(context);
    }
}
