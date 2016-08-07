package cn.coolbhu.snailgo.dataloaders;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import cn.coolbhu.snailgo.beans.Album;
import cn.coolbhu.snailgo.utils.PreferencesUtils;

public class AlbumLoader {


    public static Album getAlbum(Cursor cursor) {
        Album album = new Album();
        if (cursor != null) {
            if (cursor.moveToFirst())
                album = new Album(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getLong(3), cursor.getInt(4), cursor.getInt(5));
        }
        if (cursor != null)
            cursor.close();
        return album;
    }


    public static List<Album> getAlbumsForCursor(Cursor cursor) {
        ArrayList arrayList = new ArrayList();
        if ((cursor != null) && (cursor.moveToFirst()))
            do {
                arrayList.add(new Album(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getLong(3), cursor.getInt(4), cursor.getInt(5)));
            }
            while (cursor.moveToNext());
        if (cursor != null)
            cursor.close();
        return arrayList;
    }

    public static List<Album> getAllAlbums(Context context) {
        return getAlbumsForCursor(makeAlbumCursor(context, null, null));
    }

    public static Album getAlbum(Context context, long id) {
        return getAlbum(makeAlbumCursor(context, "_id=?", new String[]{String.valueOf(id)}));
    }

    public static List<Album> getAlbums(Context context, String paramString) {
        return getAlbumsForCursor(makeAlbumCursor(context, "album LIKE ?", new String[]{"%" + paramString + "%"}));
    }


    public static Cursor makeAlbumCursor(Context context, String selection, String[] paramArrayOfString) {
        final String albumSortOrder = PreferencesUtils.getInstance(context).getAlbumSortOrder();
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, new String[]{"_id", "album", "artist", "artist_id", "numsongs", "minyear"}, selection, paramArrayOfString, albumSortOrder);

        return cursor;
    }
}