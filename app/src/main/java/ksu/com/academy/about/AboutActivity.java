package ksu.com.academy.about;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import ksu.com.academy.Constants;
import ksu.com.academy.R;
import ksu.com.academy.Utils;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etMessage;
    private Button btnSend;
    private ImageView ivTelegram;

    public static void show(Activity activity) {
        Intent intent = new Intent(activity, AboutActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        prepare();
        setListeners();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_btn:
                sendEmail(getString(R.string.default_email_subject), etMessage.getText().toString());
                break;
            case R.id.telegram_iv:
                Utils.openLink(this,
                        Constants.TELEGRAM_PACKAGE, Constants.TELEGRAM_LINK, Constants.TELEGRAM_NIK);
                break;
        }
    }

    private void prepare() {
        etMessage = findViewById(R.id.message_et);
        btnSend = findViewById(R.id.send_btn);
        ivTelegram = findViewById(R.id.telegram_iv);

        addCopyrightText();
    }

    private void setListeners() {
        btnSend.setOnClickListener(this);
        ivTelegram.setOnClickListener(this);
    }

    private void addCopyrightText() {
        TextView tvCopyright = new TextView(this);
        tvCopyright.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        tvCopyright.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_default));
        tvCopyright.setText(R.string.copyright);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int spacingDefault = getResources().getDimensionPixelSize(R.dimen.spacing_default);
        params.topMargin = spacingDefault;
        params.bottomMargin = spacingDefault;
        params.gravity = Gravity.CENTER_HORIZONTAL;
        tvCopyright.setLayoutParams(params);

        LinearLayout containerLayout = findViewById(R.id.container_ll);
        containerLayout.addView(tvCopyright);
    }

    private void sendEmail(String subject, String message) {
        MessageActivity.show(this, subject, message);
    }
}
