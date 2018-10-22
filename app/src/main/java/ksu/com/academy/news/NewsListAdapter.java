package ksu.com.academy.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import ksu.com.academy.R;
import ksu.com.academy.Utils;
import ksu.com.academy.data.NewsItem;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {
    @NonNull
    private final List<NewsItem> newsList;
    @NonNull
    private final LayoutInflater inflater;
    @Nullable
    private final OnItemClickListener clickListener;
    @NonNull
    private final RequestManager imageLoader;

    NewsListAdapter(@NonNull Context context, @NonNull List<NewsItem> newsList,
                           @Nullable OnItemClickListener clickListener) {
        this.newsList = newsList;
        this.inflater = LayoutInflater.from(context);
        this.clickListener = clickListener;

        RequestOptions imageOption = new RequestOptions().centerCrop();
        this.imageLoader = Glide.with(context).applyDefaultRequestOptions(imageOption);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_news, parent, false), clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(newsList.get(position));
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(NewsItem news);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivImage;
        private final TextView tvCategory;
        private final TextView tvTitle;
        private final TextView tvText;
        private final TextView tvPublishedDate;

        ViewHolder(@NonNull View itemView, @Nullable OnItemClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(newsList.get(position));
                }
            });
            ivImage = itemView.findViewById(R.id.image_iv);
            tvCategory = itemView.findViewById(R.id.category_tv);
            tvTitle = itemView.findViewById(R.id.title_tv);
            tvText = itemView.findViewById(R.id.text_tv);
            tvPublishedDate = itemView.findViewById(R.id.published_date_tv);
        }

        void bind(NewsItem news) {
            imageLoader.load(news.getImageUrl()).into(ivImage);
            tvCategory.setText(news.getCategory().getName());
            tvTitle.setText(news.getTitle());
            tvText.setText(news.getPreviewText());
            tvPublishedDate.setText(Utils.formatDateTime(itemView.getContext(), news.getPublishDate()));
        }
    }
}