package kz.porcuon.projectfirstpart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsData {

    private static volatile NewsData instance;
    private List<News> news;

    private NewsData() {
        news = new ArrayList<>();
        for (int i = 0; i < 150; i++) {
            News n = new News();
            Date d = new Date();
            SimpleDateFormat postDate = new SimpleDateFormat("dd.MM.yyyy hh:mm");
            String newDateStr = postDate.format(d);
            n.setId(i);
            n.setCategory(new Category(null, "https://video-images.vice.com/articles/5a9971f3cc59160008d54a6c/lede/1520005620800-happyhacker.jpeg?crop=1xw%3A0.75xh%3Bcenter%2Ccenter&resize=2000%3A*"));
            n.setAuthor("Ha$ker_ToOL");
            n.setDatePublished(newDateStr);
            n.setTitle("Rio Hosts Largest Hacker Marathon in Latin America");
            n.setShortDescription("RIO DE JANEIRO, BRAZIL - Regarded as the largest hacker marathon in Latin America, it gathers until Sunday approximately 1.5 thousand participants, divided into 15 collective hackathons with specific topics.");
            n.setContent("There will be 42 hours of technological development, in addition to lectures on innovation, business, and entrepreneurship." +
                    "\n\nThe digital culture festival is held at Aqwa Corporate, in the port region of Rio de Janeiro, and includes topics such as education; energy; sports; food; construction, culture and creative economy; insurance; mobility; oil & gas; distribution and convenience; security, defense and cybersecurity; sustainability and oceans; platform cooperativism and tourism.");
            n.setImageUrl("https://miro.medium.com/max/3840/1*XZrU5omVCZPZXuYv2AZnog.jpeg");
            n.setCommentsCount(0);
            n.setBookmarksCount(0);
            n.setLikesCount(0);
            n.setViewsCount(0);
            news.add(n);
        }
    }

    public static synchronized NewsData getInstance() {
        if (instance == null) {
            instance = new NewsData();
        }
        return instance;
    }

    public List<News> getAllNews() {
        return news;
    }

    public News getNewsById(int id) {
        for (News n : news) {
            if (n.getId() == id) return n;
        }
        return null;
    }
}
