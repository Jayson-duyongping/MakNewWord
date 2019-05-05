package com.mak.eword.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.mak.eword.mvp.model.SentenceBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "sentence_table".
*/
public class SentenceBeanDao extends AbstractDao<SentenceBean, Long> {

    public static final String TABLENAME = "sentence_table";

    /**
     * Properties of entity SentenceBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ImgUrl = new Property(1, String.class, "imgUrl", false, "IMG_URL");
        public final static Property EnContent = new Property(2, String.class, "enContent", false, "EN_CONTENT");
        public final static Property ZhContent = new Property(3, String.class, "zhContent", false, "ZH_CONTENT");
    };


    public SentenceBeanDao(DaoConfig config) {
        super(config);
    }
    
    public SentenceBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"sentence_table\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"IMG_URL\" TEXT," + // 1: imgUrl
                "\"EN_CONTENT\" TEXT," + // 2: enContent
                "\"ZH_CONTENT\" TEXT);"); // 3: zhContent
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"sentence_table\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, SentenceBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String imgUrl = entity.getImgUrl();
        if (imgUrl != null) {
            stmt.bindString(2, imgUrl);
        }
 
        String enContent = entity.getEnContent();
        if (enContent != null) {
            stmt.bindString(3, enContent);
        }
 
        String zhContent = entity.getZhContent();
        if (zhContent != null) {
            stmt.bindString(4, zhContent);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, SentenceBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String imgUrl = entity.getImgUrl();
        if (imgUrl != null) {
            stmt.bindString(2, imgUrl);
        }
 
        String enContent = entity.getEnContent();
        if (enContent != null) {
            stmt.bindString(3, enContent);
        }
 
        String zhContent = entity.getZhContent();
        if (zhContent != null) {
            stmt.bindString(4, zhContent);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public SentenceBean readEntity(Cursor cursor, int offset) {
        SentenceBean entity = new SentenceBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // imgUrl
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // enContent
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // zhContent
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, SentenceBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setImgUrl(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setEnContent(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setZhContent(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(SentenceBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(SentenceBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}