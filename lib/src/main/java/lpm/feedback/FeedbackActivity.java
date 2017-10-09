package lpm.feedback;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class FeedbackActivity extends AppCompatActivity {

    private FeedbackView contentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contentView = new FeedbackView(this);
        setContentView(contentView);

        setTitle(FeedbackConfiguration.title);
    }
}
