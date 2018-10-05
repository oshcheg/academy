package ksu.com.academy;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etMessage;
    private Button btnSend;
    private ImageView ivTelegram;
    private ImageView ivInstagram;
    private ImageView ivHabr;

    private final String TELEGRAM_PACKAGE = "org.telegram.messenger";
    private final String HABRAHABR_PACKAGE = "ru.habrahabr";
    private final String INSTAGRAM_PACKAGE = "com.instagram.android";

    private final String TELEGRAM_LINK = "https://telegram.org";
    private final String HABRAHABR_LINK = "https://m.habr.com";
    private final String INSTAGRAM_LINK = "https://instagram.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prepare();
        setListeners();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_btn:
                sendEmail(etMessage.getText().toString());
                break;
            case R.id.telegram:
                openLink(TELEGRAM_PACKAGE, TELEGRAM_LINK);
                break;
            case R.id.instagram:
                openLink(INSTAGRAM_PACKAGE, INSTAGRAM_LINK);
                break;
            case R.id.habr:
                openLink(HABRAHABR_PACKAGE, HABRAHABR_LINK);
                break;
        }
    }

    private void prepare() {
        etMessage = findViewById(R.id.message_et);
        btnSend = findViewById(R.id.send_btn);
        ivTelegram = findViewById(R.id.telegram);
        ivInstagram = findViewById(R.id.instagram);
        ivHabr = findViewById(R.id.habr);

        addCopyrightText();
    }

    private void setListeners() {
        btnSend.setOnClickListener(this);
        ivTelegram.setOnClickListener(this);
        ivInstagram.setOnClickListener(this);
        ivHabr.setOnClickListener(this);
    }

    private void addCopyrightText() {
        TextView tvCopyright = new TextView(this);
        tvCopyright.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        tvCopyright.setTextSize(Utils.pxToDp((int) getResources().getDimension(R.dimen.text_size_default)));
        tvCopyright.setText(R.string.copyright);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        tvCopyright.setLayoutParams(params);

        LinearLayout containerLayout = findViewById(R.id.container_ll);
        containerLayout.addView(tvCopyright);
    }

    private void sendEmail(String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(getString(R.string.mailto), "", null));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.default_email_subject));
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent, getString(R.string.choose_app)));
        } else {
            Toast.makeText(this, R.string.no_app_to_send_email, Toast.LENGTH_SHORT).show();
        }
    }

    private void openLink(String packageName, String link) {
        Intent intent;
        if (isPackageInstalled(packageName)) {
            intent = getPackageManager().getLaunchIntentForPackage(packageName);
            startActivity(intent);
        } else {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(Intent.createChooser(intent, getString(R.string.choose_app)));
            } else {
                Toast.makeText(this, R.string.no_app_to_open_link, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isPackageInstalled(String packagename) {
        PackageManager packageManager = getPackageManager();
        try {
            packageManager.getPackageInfo(packagename, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}
