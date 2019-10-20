package kz.porcuon.projectfirstpart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoActivity extends AppCompatActivity {

    private static final String EXTRA_ID = "extra_id";

    private News news;

    private static final String FILE_NAME = "example.txt";

    EditText mEditText;

    @BindView(R.id.iv_category_image)
    protected ImageView ivCategoryImage;
    @BindView(R.id.tv_category)
    protected TextView tvCategory;
    @BindView(R.id.tv_author)
    protected TextView tvAuthor;
    @BindView(R.id.tv_date_published)
    protected TextView tvDatePublished;
    @BindView(R.id.tv_views_count)
    protected TextView tvViewsCount;
    @BindView(R.id.tv_title)
    protected TextView tvTitle;
    @BindView(R.id.tv_short_description)
    protected TextView tvShortDescription;
    @BindView(R.id.iv_image)
    protected ImageView ivImage;
    @BindView(R.id.tv_content)
    protected TextView tvContent;
    @BindView(R.id.tv_tags)
    protected TextView tvTags;
    @BindView(R.id.iv_share)
    protected ImageView ivShare;
    @BindView(R.id.tv_likes_count)
    protected TextView tvLikesCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_layout);
        ButterKnife.bind(this);

        int id = getIntent().getIntExtra(EXTRA_ID, 0);
        news = NewsData.getInstance().getNewsById(id);
        prepareUI();


        mEditText = findViewById(R.id.edit_text);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_main_share:
                shareUrl();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void prepareUI() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(R.string.details_title);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        tvCategory.setText(news.getCategory().getName());
        tvAuthor.setText(news.getAuthor());
        tvDatePublished.setText(news.getDatePublished());
        tvViewsCount.setText(String.valueOf(news.getViewsCount()));
        tvTitle.setText(news.getTitle());
        tvShortDescription.setText(news.getShortDescription());
        tvContent.setText(news.getContent());
        tvTags.setText(news.getTags());
        tvLikesCount.setText(String.valueOf(news.getLikesCount()));
        ivShare.setOnClickListener(view -> shareUrl());

        Glide.with(this)
                .load(news.getCategory().getImageUrl())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivCategoryImage);

        Glide.with(this)
                .load(news.getImageUrl())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivImage);
    }



    public static void open(Context context, int id) {
        Intent intent = new Intent(context, InfoActivity.class);
        intent.putExtra(EXTRA_ID, id);
        context.startActivity(intent);
    }


    public void save(View v) {
        String text = mEditText.getText().toString();
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());

            mEditText.getText().clear();
            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME,
                    Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void load(View v) {
        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            mEditText.setText(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void shareUrl() {
        FileInputStream fis = null;
        Intent intent = new Intent(Intent.ACTION_SEND);

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
            intent.putExtra(Intent.EXTRA_TEXT, sb.toString());
            intent.setType("text/plain");
            startActivity(intent);

            Intent shareIntent = Intent.createChooser(intent, null);
            startActivity(shareIntent);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
