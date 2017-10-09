package lpm.feedback;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("ViewConstructor")
public class RatingView extends LinearLayout {

    final ImageView imageView;
    final TextView textView;

    private int score;
    boolean isActive;

    public RatingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        View.inflate(context, R.layout.lmp_feedback_rating_view, this);
        setOrientation(LinearLayout.VERTICAL);
        imageView = findViewById(R.id.lpm_feedback_rating_view_img);
        textView = findViewById(R.id.lpm_feedback_rating_view_txt);
    }

    void bindScore(int score) {
        this.score = score;

        switch (score) {
            case 0:
                imageView.setImageResource(R.drawable.ic_rate_0_inactive);
                textView.setText(FeedbackConfiguration.rate0);
                break;
            case 1:
                imageView.setImageResource(R.drawable.ic_rate_1_inactive);
                textView.setText(FeedbackConfiguration.rate1);
                break;
            case 2:
                imageView.setImageResource(R.drawable.ic_rate_2_inactive);
                textView.setText(FeedbackConfiguration.rate2);
                break;
            case 3:
                imageView.setImageResource(R.drawable.ic_rate_3_inactive);
                textView.setText(FeedbackConfiguration.rate3);
                break;
        }
    }

    void bindActive(boolean isActive) {
        this.isActive = isActive;

        switch (score) {
            case 0:
                imageView.setImageResource(isActive ? R.drawable.ic_rate_0_active : R.drawable.ic_rate_0_inactive);
                break;
            case 1:
                imageView.setImageResource(isActive ? R.drawable.ic_rate_1_active : R.drawable.ic_rate_1_inactive);
                break;
            case 2:
                imageView.setImageResource(isActive ? R.drawable.ic_rate_2_active : R.drawable.ic_rate_2_inactive);
                break;
            case 3:
                imageView.setImageResource(isActive ? R.drawable.ic_rate_3_active : R.drawable.ic_rate_3_inactive);
                break;
        }
    }
}
