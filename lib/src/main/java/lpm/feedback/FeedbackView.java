package lpm.feedback;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

public class FeedbackView extends ScrollView {

    private Dialog dialog;

    private FeedbackPresenter presenter = new FeedbackPresenter(this);

    private final TextView ratingHintTextView;
    private final TextView feedbackHintTextView;
    private final Button submitButton;
    private final RatingsView ratingsView;
    private final EditText feedbackEditText;

    public FeedbackView(@NonNull Context context) {
        super(context);
        View.inflate(context, R.layout.lmp_feedback_feedback_view, this);
        ratingHintTextView = findViewById(R.id.lpm_feedback_feedback_view_rating_hint_txt);
        feedbackHintTextView = findViewById(R.id.lpm_feedback_feedback_view_feedback_hint_txt);
        submitButton = findViewById(R.id.lpm_feedback_feedback_view_feedback_send_btn);
        ratingsView = findViewById(R.id.lpm_feedback_feedback_view_ratings_view);
        feedbackEditText = findViewById(R.id.lpm_feedback_feedback_view_feedback_editxt);

        setupView();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.onAttach();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.onDetach();

        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //noinspection SimplifiableIfStatement
        if (!isEnabled()) {
            return false;
        }

        return super.dispatchTouchEvent(ev);
    }

    void bindLoading() {
        setEnabled(false);

        if (dialog != null) dialog.dismiss();
        dialog = new MaterialDialog.Builder(getContext())
                .content(R.string.lpm_feedback_loading)
                .progress(true, 0)
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .show();
    }

    void bindNormal() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
        setEnabled(true);
    }

    void bindSuccess() {
        bindNormal();

        feedbackEditText.setText("");

        if (dialog != null) dialog.dismiss();
        dialog = new MaterialDialog.Builder(getContext())
                .content(R.string.lpm_feedback_success)
                .neutralText("OK")
                .cancelable(true)
                .canceledOnTouchOutside(true)
                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        ((Activity) getContext()).finish();
                    }
                })
                .show();
    }

    void bindFailure(int message) {
        Snackbar.make(this, message, Snackbar.LENGTH_LONG).show();
        bindNormal();
    }

    private void setupView() {
        ratingHintTextView.setFocusableInTouchMode(true);

        setFillViewport(true);
        setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.lpm_feedback_white_fake, null));

        ratingHintTextView.setText(FeedbackConfiguration.ratingHint);
        feedbackHintTextView.setText(FeedbackConfiguration.feedbackHint);

        submitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.submit(ratingsView.score, feedbackEditText.getText().toString());
            }
        });
    }

}
