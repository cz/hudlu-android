package zheng.craig.hudlu.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cz on 11/23/15.
 */
public class MashableNews {
    // Set serialized JSON field name
    @SerializedName("new") public List<MashableNewsItem> newsItems;
}
