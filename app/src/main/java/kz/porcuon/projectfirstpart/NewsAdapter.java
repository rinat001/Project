package kz.porcuon.projectfirstpart;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.VHNews> {

    private Context context;
    private List<News> news;
    private OnClick onClick;

    NewsAdapter(@NonNull List<News> news,
                @NonNull Context context,
                @NonNull OnClick onClick) {
        this.news = news;
        this.context = context;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public VHNews onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_layout, parent, false);
        return new VHNews(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VHNews holder, int position) {
        holder.bind(news.get(position));
    }

    @Override
    public int getItemCount() {
        return news.size();
    }


    public class VHNews extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_category_image)
        protected ImageView ivCategoryImage;
        @BindView(R.id.tv_author)
        protected TextView tvAuthor;
        @BindView(R.id.tv_date_published)
        protected TextView tvDatePublished;
        @BindView(R.id.iv_share)
        protected ImageView ivShare;
        @BindView(R.id.tv_title)
        protected TextView tvTitle;
        @BindView(R.id.tv_short_description)
        protected TextView tvShortDescription;
        @BindView(R.id.iv_image)
        protected ImageView ivImage;
        @BindView(R.id.tv_comments_count)
        protected TextView tvCommentsCount;
        @BindView(R.id.tv_bookmarks_count)
        protected TextView tvBookmarksCount;
        @BindView(R.id.tv_likes_count)
        protected TextView tvLikesCount;

        private View itemView;

        public VHNews(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }

        public void bind(News news) {
            String countPlaceholder = context.getString(R.string.count_placeholder);
            String commentsCount = news.getCommentsCount() > 999 ? countPlaceholder : String.valueOf(news.getCommentsCount());
            String bookmarksCount = news.getBookmarksCount() > 999 ? countPlaceholder : String.valueOf(news.getBookmarksCount());
            String likesCount = news.getLikesCount() > 999 ? countPlaceholder : String.valueOf(news.getLikesCount());

            if (news.getLikesCount() < 0) {
                tvLikesCount.setTextColor(Color.RED);
            }

            tvAuthor.setText(news.getAuthor());
            tvDatePublished.setText(news.getDatePublished());
            tvTitle.setText(news.getTitle());
            tvShortDescription.setText(news.getShortDescription());
            tvCommentsCount.setText(commentsCount);
            tvBookmarksCount.setText(bookmarksCount);
            tvLikesCount.setText(likesCount);
            ivShare.setOnClickListener(view -> onClick.onShareClicked(news.getId()));
            itemView.setOnClickListener(view -> onClick.onItemViewClicked(news.getId()));

            Glide.with(context)
                    .load(news.getCategory().getImageUrl())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivCategoryImage);

            Glide.with(context)
                    .load(news.getImageUrl())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivImage);
        }
    }
}
