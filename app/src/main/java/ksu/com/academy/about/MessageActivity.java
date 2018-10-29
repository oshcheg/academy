package ksu.com.academy.about;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import ksu.com.academy.R;
import ksu.com.academy.Utils;

public class MessageActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String EXTRA_SUBJECT = "extra_subject";
    private static final String EXTRA_MESSAGE = "extra_message";

    private String subject;
    private String message;

    public static void show(Activity activity, String subject, String message) {
        Intent intent = new Intent(activity, MessageActivity.class);
        intent.putExtra(EXTRA_SUBJECT, subject);
        intent.putExtra(EXTRA_MESSAGE, message);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        subject = getIntent().getStringExtra(EXTRA_SUBJECT);
        message = getIntent().getStringExtra(EXTRA_MESSAGE);
        TextView tvMessage = findViewById(R.id.message_tv);
        tvMessage.setText(message);

        Button btnEmail = findViewById(R.id.email_btn);
        btnEmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.email_btn) {
            Utils.sendEmail(this, subject, message);
        }
    }
}
