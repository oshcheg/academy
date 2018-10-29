package ksu.com.academy.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import ksu.com.academy.R;
import ksu.com.academy.Utils;
import ksu.com.academy.data.NewsItem;

public class NewsDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_NEWS_ITEM = "extra_news_item";

    public static void show(Context context, NewsItem newsItem) {
        Intent intent = new Intent(context, NewsDetailsActivity.class);
        intent.putExtra(EXTRA_NEWS_ITEM, newsItem);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        init();
    }

    private void init() {
        final NewsItem newsItem = (NewsItem) getIntent().getSerializableExtra(EXTRA_NEWS_ITEM);

        ImageView ivImage = findViewById(R.id.image_iv);
        TextView tvTitle = findViewById(R.id.title_tv);
        TextView tvText = findViewById(R.id.text_tv);
        TextView tvPublishedDate = findViewById(R.id.published_date_tv);

        Glide.with(this).load(newsItem.getImageUrl()).into(ivImage);
        tvTitle.setText(newsItem.getTitle());
        tvText.setText(newsItem.getFullText());
        tvPublishedDate.setText(Utils.formatDateTime(this, newsItem.getPublishDate()));

        Toolbar toolbar = findViewById(R.id.toolbar);

        if (toolbar != null) {
            toolbar.setTitle(newsItem.getCategory().getName());
        }
    }
}
