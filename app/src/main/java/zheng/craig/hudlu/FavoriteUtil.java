package zheng.craig.hudlu;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmResults;
import zheng.craig.hudlu.models.Favorite;
import zheng.craig.hudlu.models.MashableNewsItem;

/**
 * Created by cz on 11/29/15.
 */
public class FavoriteUtil {
    public static void addFavorite(Context context, MashableNewsItem newsItem) {
        Favorite favorite = new Favorite();
        favorite.title = newsItem.title;
        favorite.image = newsItem.image;
        favorite.author = newsItem.author;
        favorite.link = newsItem.link;

        Realm realm = Realm.getInstance(context);

        realm.beginTransaction();
        realm.copyToRealm(favorite);
        realm.commitTransaction();
    }

    public static void removeFavorite(Context context, MashableNewsItem newsItem) {

    }

    public static boolean isFavorite(Context context, MashableNewsItem newsItem) {

    }

    public static RealmResults<Favorite> getAllFavorites(Context context) {

    }
}
