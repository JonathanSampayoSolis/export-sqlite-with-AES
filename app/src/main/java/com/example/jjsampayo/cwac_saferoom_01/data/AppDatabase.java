package com.example.jjsampayo.cwac_saferoom_01.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.Environment;
import android.text.Editable;
import android.util.Log;

import com.commonsware.cwac.saferoom.SafeHelperFactory;
import com.example.jjsampayo.cwac_saferoom_01.Crypto;
import com.example.jjsampayo.cwac_saferoom_01.data.daos.UserDao;
import com.example.jjsampayo.cwac_saferoom_01.data.entities.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import javax.crypto.Cipher;

@Database(entities = User.class, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "app_encrypted_db";

    private static AppDatabase appDatabase;

    private static SafeHelperFactory factory;

    public static AppDatabase getAppDatabase(Context context, Editable editable) {
        factory = SafeHelperFactory.fromUser(editable);
        return appDatabase == null ?
                appDatabase = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).build() : appDatabase;
    }

    public static boolean exportDB(Context context) throws IOException {
        boolean isSaved;

        File sd = Environment.getExternalStorageDirectory();

        File folder = new File(sd, "ANDROIDDATAB");
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                Log.i("export", "Directorio creado");
            } else {
                Log.i("export", "Directorio no creado");
            }
        }

        if (folder.canWrite()) {
            String backupDBPath = "DB_CUALQUIERA";

            File currentDB = context.getDatabasePath(DB_NAME);
            File backupDB = new File(folder, backupDBPath);

            if (backupDB.exists()) {
                boolean isDeleted = backupDB.delete();

                if (isDeleted) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();

                    isSaved = true;
                } else
                    isSaved = false;
            } else{
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

                isSaved = true;
            }

            AppDatabase.encryptFile(backupDB, folder);
        } else
            isSaved = false;

        return  isSaved;
    }

    public abstract UserDao userDao();

    private static void encryptFile(File db, File folder) {
        // just accept pass of (128 || 192 || 256) / 8
        String key = "te_queremos_feru";
        File encryptedFile = new File(folder,"db.encrypted");

        try {
            Crypto.fileProcessor(Cipher.ENCRYPT_MODE,key, db,encryptedFile);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

}
