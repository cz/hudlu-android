package zheng.craig.hudlu;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import zheng.craig.hudlu.models.Favorite;
import zheng.craig.hudlu.models.MashableNewsItem;

/**
 * Created by cz on 11/29/15.
 */
public class FavoriteUtil {
    public static void addFavorite(Context context, MashableNewsItem newsItem) {
        Favorite favorite = new Favorite();
        favorite.setTitle(newsItem.title);
        favorite.setImage(newsItem.image);
        favorite.setAuthor(newsItem.author);
        favorite.setLink(newsItem.link);

        Realm realm = Realm.getInstance(context);

        realm.beginTransaction();
        realm.copyToRealm(favorite);
        realm.commitTransaction();
    }

    public static void removeFavorite(Context context, MashableNewsItem newsItem) {
    }

    public static boolean isFavorite(Context context, MashableNewsItem newsItem) {
        return false;
    }

    public static RealmResults<Favorite> getAllFavorites(Context context) {
        Realm realm = Realm.getInstance(context);
        RealmQuery<Favorite> query = realm.where(Favorite.class);
        return query.findAll();
    }
}
