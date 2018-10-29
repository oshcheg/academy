package ksu.com.academy.news;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ksu.com.academy.R;
import ksu.com.academy.Utils;
import ksu.com.academy.about.AboutActivity;
import ksu.com.academy.data.NewsData;

public class NewsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        RecyclerView list = findViewById(R.id.list);
        list.setAdapter(new NewsListAdapter(this, NewsData.generateNews(), newsItem -> {
            NewsDetailsActivity.show(this, newsItem);
        }));
        list.setLayoutManager(new GridLayoutManager(this, getColumnCount()));

        RecyclerView.ItemDecoration dividerItemDecoration =
                new NewsItemDecoration((int) getResources().getDimension(R.dimen.spacing_around_news_item));
        list.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                AboutActivity.show(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private int getColumnCount() {
        int minSizeInPixels = getResources().getDimensionPixelSize(R.dimen.size_news_item_min_width);
        int columnCount = Utils.pxDisplayWidth(this) / minSizeInPixels;
        return columnCount < 1 ? 1 : columnCount;
    }
}
