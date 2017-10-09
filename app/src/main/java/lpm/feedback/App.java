package lpm.feedback;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FeedbackConfiguration.init(this);
    }
}
