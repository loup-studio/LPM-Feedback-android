package lpm.feedback;

import com.jakewharton.rxrelay.PublishRelay;

import rx.Subscription;
import rx.functions.Action1;

class FeedbackPresenter {

    private final FeedbackView view;
    private final PublishRelay<Boolean> submitStream = PublishRelay.create();
    private Subscription subscription;

    FeedbackPresenter(FeedbackView view) {
        this.view = view;
    }

    void onAttach() {
        subscription = submitStream.subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean isSuccess) {
                if (isSuccess) {
                    view.bindSuccess();
                } else {
                    view.bindFailure(R.string.lpm_feedback_error);
                }
            }
        });
    }

    void onDetach() {
        subscription.unsubscribe();
        subscription = null;
    }

    void submit(int score, String comment) {
        if (score < 0) {
            view.bindFailure(R.string.lpm_feedback_error_blank_fields);
            return;
        }

        view.bindLoading();
        FeedbackConfiguration.manager.submit(score, comment, submitStream);
    }
}
