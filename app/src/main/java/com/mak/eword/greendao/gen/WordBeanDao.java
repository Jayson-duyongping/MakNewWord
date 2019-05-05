package com.mak.eword.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.mak.eword.mvp.model.converter.MeanListConverer;
import com.mak.eword.mvp.model.converter.StringListConverer;
import java.util.List;

import com.mak.eword.mvp.model.WordBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "word_table".
*/
public class WordBeanDao extends AbstractDao<WordBean, Long> {

    public static final String TABLENAME = "word_table";

    /**
     * Properties of entity WordBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Content = new Property(1, String.class, "content", false, "CONTENT");
        public final static Property Means = new Property(2, String.class, "means", false, "MEANS");
        public final static Property Method = new Property(3, String.class, "method", false, "METHOD");
        public final static Property ExampleEn = new Property(4, String.class, "exampleEn", false, "EXAMPLE_EN");
        public final static Property ExampleZh = new Property(5, String.class, "exampleZh", false, "EXAMPLE_ZH");
        public final static Property Relations = new Property(6, String.class, "relations", false, "RELATIONS");
        public final static Property IsRemember = new Property(7, boolean.class, "isRemember", false, "IS_REMEMBER");
        public final static Property LookTime = new Property(8, int.class, "lookTime", false, "LOOK_TIME");
    };

    private final MeanListConverer meansConverter = new MeanListConverer();
    private final StringListConverer relationsConverter = new StringListConverer();

    public WordBeanDao(DaoConfig config) {
        super(config);
    }
    
    public WordBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"word_table\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"CONTENT\" TEXT NOT NULL ," + // 1: content
                "\"MEANS\" TEXT," + // 2: means
                "\"METHOD\" TEXT," + // 3: method
                "\"EXAMPLE_EN\" TEXT," + // 4: exampleEn
                "\"EXAMPLE_ZH\" TEXT," + // 5: exampleZh
                "\"RELATIONS\" TEXT," + // 6: relations
                "\"IS_REMEMBER\" INTEGER NOT NULL ," + // 7: isRemember
                "\"LOOK_TIME\" INTEGER NOT NULL );"); // 8: lookTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"word_table\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, WordBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getContent());
 
        List means = entity.getMeans();
        if (means != null) {
            stmt.bindString(3, meansConverter.convertToDatabaseValue(means));
        }
 
        String method = entity.getMethod();
        if (method != null) {
            stmt.bindString(4, method);
        }
 
        String exampleEn = entity.getExampleEn();
        if (exampleEn != null) {
            stmt.bindString(5, exampleEn);
        }
 
        String exampleZh = entity.getExampleZh();
        if (exampleZh != null) {
            stmt.bindString(6, exampleZh);
        }
 
        List relations = entity.getRelations();
        if (relations != null) {
            stmt.bindString(7, relationsConverter.convertToDatabaseValue(relations));
        }
        stmt.bindLong(8, entity.getIsRemember() ? 1L: 0L);
        stmt.bindLong(9, entity.getLookTime());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, WordBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getContent());
 
        List means = entity.getMeans();
        if (means != null) {
            stmt.bindString(3, meansConverter.convertToDatabaseValue(means));
        }
 
        String method = entity.getMethod();
        if (method != null) {
            stmt.bindString(4, method);
        }
 
        String exampleEn = entity.getExampleEn();
        if (exampleEn != null) {
            stmt.bindString(5, exampleEn);
        }
 
        String exampleZh = entity.getExampleZh();
        if (exampleZh != null) {
            stmt.bindString(6, exampleZh);
        }
 
        List relations = entity.getRelations();
        if (relations != null) {
            stmt.bindString(7, relationsConverter.convertToDatabaseValue(relations));
        }
        stmt.bindLong(8, entity.getIsRemember() ? 1L: 0L);
        stmt.bindLong(9, entity.getLookTime());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public WordBean readEntity(Cursor cursor, int offset) {
        WordBean entity = new WordBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // content
            cursor.isNull(offset + 2) ? null : meansConverter.convertToEntityProperty(cursor.getString(offset + 2)), // means
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // method
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // exampleEn
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // exampleZh
            cursor.isNull(offset + 6) ? null : relationsConverter.convertToEntityProperty(cursor.getString(offset + 6)), // relations
            cursor.getShort(offset + 7) != 0, // isRemember
            cursor.getInt(offset + 8) // lookTime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, WordBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setContent(cursor.getString(offset + 1));
        entity.setMeans(cursor.isNull(offset + 2) ? null : meansConverter.convertToEntityProperty(cursor.getString(offset + 2)));
        entity.setMethod(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setExampleEn(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setExampleZh(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setRelations(cursor.isNull(offset + 6) ? null : relationsConverter.convertToEntityProperty(cursor.getString(offset + 6)));
        entity.setIsRemember(cursor.getShort(offset + 7) != 0);
        entity.setLookTime(cursor.getInt(offset + 8));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(WordBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(WordBean entity) {
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