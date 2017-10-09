package lpm.feedback;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

public class MainActivity extends AppCompatActivity {

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Minimal config:
        FeedbackConfiguration.api = "http://apps-ghpsj.lespetitesmains.net/rate_douleur.php";
        FeedbackConfiguration.email = "android_email@foo.com";

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FeedbackConfiguration.manager.accept();
                startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
            }
        });


        // automatic counting
        FeedbackConfiguration.manager.increment();

        if (FeedbackConfiguration.manager.shouldShow()) {
            dialog = new MaterialDialog.Builder(this)
                    .content("Do you want to provide feedback?")
                    .positiveText("Yes")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            FeedbackConfiguration.manager.accept();
                            startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
                        }
                    })
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            FeedbackConfiguration.manager.dismiss();
                        }
                    })
                    .canceledOnTouchOutside(false)
                    .show();
        }
    }

    @Override
    protected void onDestroy() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }

        super.onDestroy();
    }
}
