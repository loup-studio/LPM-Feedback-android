package lpm.feedback;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class RatingsView extends LinearLayout {

    final RatingView rating0View;
    final RatingView rating1View;
    final RatingView rating2View;
    final RatingView rating3View;

    int score = -1;

    public RatingsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.lmp_feedback_ratings_view, this);
        setOrientation(LinearLayout.HORIZONTAL);

        rating0View = findViewById(R.id.lpm_feedback_ratings_view_rating_0);
        rating1View = findViewById(R.id.lpm_feedback_ratings_view_rating_1);
        rating2View = findViewById(R.id.lpm_feedback_ratings_view_rating_2);
        rating3View = findViewById(R.id.lpm_feedback_ratings_view_rating_3);

        ((LayoutParams) rating0View.getLayoutParams()).weight = 1f;
        ((LayoutParams) rating1View.getLayoutParams()).weight = 1f;
        ((LayoutParams) rating2View.getLayoutParams()).weight = 1f;
        ((LayoutParams) rating3View.getLayoutParams()).weight = 1f;

        rating0View.bindScore(0);
        rating1View.bindScore(1);
        rating2View.bindScore(2);
        rating3View.bindScore(3);

        rating0View.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                bindActive(0);
            }
        });
        rating1View.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                bindActive(1);
            }
        });
        rating2View.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                bindActive(2);
            }
        });
        rating3View.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                bindActive(3);
            }
        });
    }

    private void bindActive(int score) {
        this.score = score;
        rating0View.bindActive(score == 0);
        rating1View.bindActive(score == 1);
        rating2View.bindActive(score == 2);
        rating3View.bindActive(score == 3);
    }
}
