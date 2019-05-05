package com.mak.eword.greendao.service;

import android.content.Context;

import com.mak.eword.greendao.DBManager;
import com.mak.eword.greendao.gen.DaoMaster;
import com.mak.eword.greendao.gen.DaoSession;
import com.mak.eword.greendao.gen.SentenceBeanDao;
import com.mak.eword.mvp.model.SentenceBean;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by jayson on 2019/4/8.
 * Content: 一个句子的数据库操作类
 */
public class SentenceService {

    private static SentenceService mInstance;
    private DBManager dbManager;

    public SentenceService(Context context) {
        this.dbManager = DBManager.getInstance(context);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static SentenceService getInstance(Context context) {
        if (mInstance == null) {
            synchronized (SentenceService.class) {
                if (mInstance == null) {
                    mInstance = new SentenceService(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 插入一条记录
     *
     * @param user
     */
    public void insertSentence(SentenceBean user) {
        DaoMaster daoMaster = new DaoMaster(dbManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        SentenceBeanDao userDao = daoSession.getSentenceBeanDao();
        userDao.insert(user);
    }

    /**
     * 插入单词集合
     *
     * @param users
     */
    public void insertSentenceList(List<SentenceBean> users) {
        if (users == null || users.isEmpty()) {
            return;
        }
        DaoMaster daoMaster = new DaoMaster(dbManager.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        SentenceBeanDao userDao = daoSession.getSentenceBeanDao();
        userDao.insertInTx(users);
    }

    /**
     * 查询个数
     *
     * @return
     */
    public long querySentenceListCount() {
        DaoMaster daoMaster = new DaoMaster(dbManager.getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        SentenceBeanDao userDao = daoSession.getSentenceBeanDao();
        QueryBuilder<SentenceBean> qb = userDao.queryBuilder();
        return qb.count();
    }

    /**
     * 查询句子列表
     */
    public List<SentenceBean> querySentenceList(int position, int count) {
        DaoMaster daoMaster = new DaoMaster(dbManager.getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        SentenceBeanDao userDao = daoSession.getSentenceBeanDao();
        QueryBuilder<SentenceBean> qb = userDao.queryBuilder();
        qb.offset(position).limit(count);
        List<SentenceBean> list = qb.list();
        return list;
    }

    /**
     * 查询句子列表
     */
    public List<SentenceBean> querySentenceList(String wordKey) {
        DaoMaster daoMaster = new DaoMaster(dbManager.getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        SentenceBeanDao userDao = daoSession.getSentenceBeanDao();
        QueryBuilder<SentenceBean> qb = userDao.queryBuilder();
        qb.where(SentenceBeanDao.Properties.EnContent.like(wordKey));
        List<SentenceBean> list = qb.list();
        return list;
    }
}
