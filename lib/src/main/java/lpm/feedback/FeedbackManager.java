package lpm.feedback;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.jakewharton.rxrelay.PublishRelay;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class FeedbackManager {

    private static String PrefsShowsCount = "rate.shows_count";
    private static String PrefsDismissesCount = "rate.dismisses_count";
    private static String PrefsIsDisabled = "rate.is_disabled";

    private final SharedPreferences preferences;

    private int showsCount;
    private int dismissesCount;
    private boolean isDisabled;
    private boolean isDebug = false;

    private Api api;

    FeedbackManager(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        showsCount = preferences.getInt(PrefsShowsCount, 0);
        dismissesCount = preferences.getInt(PrefsDismissesCount, 0);
        isDisabled = preferences.getBoolean(PrefsIsDisabled, false);

        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl("http://blank.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    void submit(int score, String comment, final PublishRelay<Boolean> stream) {
        if (FeedbackConfiguration.api.equals("")) {
            throw new RuntimeException("FeedbackConfiguration.Api required");
        }

        if (FeedbackConfiguration.email.equals("")) {
            throw new RuntimeException("FeedbackConfiguration.Email required");
        }

        api.rate(FeedbackConfiguration.api, FeedbackConfiguration.email, score, comment, "android")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Response<Void>>() {
                    @Override
                    public void call(Response<Void> response) {
                        stream.call(response.isSuccessful());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        stream.call(false);
                    }
                });
    }

    public boolean shouldShow() {
        return isDebug || (!isDisabled && showsCount >= 20 * ((dismissesCount + 1) * (dismissesCount == 0 ? 1 : dismissesCount)));
    }

    public void increment() {
        showsCount++;
        save();
    }

    public void accept() {
        isDisabled = true;
        save();
    }

    public void dismiss() {
        dismissesCount++;
        save();
    }

    private void save() {
        preferences.edit()
                .putInt(PrefsShowsCount, showsCount)
                .putInt(PrefsDismissesCount, dismissesCount)
                .putBoolean(PrefsIsDisabled, isDisabled)
                .apply();
    }
}
