package kz.porcuon.projectfirstpart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnClick {

    final String LOG_TAG = "myLogs";

    final String FILENAME = "file";

    final String DIR_SD = "MyFiles";
    final String FILENAME_SD = "fileSD";

    @BindView(R.id.rv_news)
    protected RecyclerView rvNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        prepareUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemViewClicked(int id) {
        InfoActivity.open(this, id);
    }

    @Override

    public void onShareClicked(int id) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        String message = "https://myapp.kz/" + id;
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setType("text/plain");
        startActivity(intent);
    }

    private void prepareUI() {
        ActionBar toolbar = getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle(getString(R.string.main_title));
        }

        List<News> news = NewsData.getInstance().getAllNews();
        NewsAdapter adapter = new NewsAdapter(news, this, this);
        rvNews.setLayoutManager(new LinearLayoutManager(this));
        rvNews.setAdapter(adapter);
    }
}