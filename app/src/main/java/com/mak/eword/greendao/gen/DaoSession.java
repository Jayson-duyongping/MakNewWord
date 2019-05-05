package com.mak.eword.greendao.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.mak.eword.mvp.model.SentenceBean;
import com.mak.eword.mvp.model.WordBean;

import com.mak.eword.greendao.gen.SentenceBeanDao;
import com.mak.eword.greendao.gen.WordBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig sentenceBeanDaoConfig;
    private final DaoConfig wordBeanDaoConfig;

    private final SentenceBeanDao sentenceBeanDao;
    private final WordBeanDao wordBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        sentenceBeanDaoConfig = daoConfigMap.get(SentenceBeanDao.class).clone();
        sentenceBeanDaoConfig.initIdentityScope(type);

        wordBeanDaoConfig = daoConfigMap.get(WordBeanDao.class).clone();
        wordBeanDaoConfig.initIdentityScope(type);

        sentenceBeanDao = new SentenceBeanDao(sentenceBeanDaoConfig, this);
        wordBeanDao = new WordBeanDao(wordBeanDaoConfig, this);

        registerDao(SentenceBean.class, sentenceBeanDao);
        registerDao(WordBean.class, wordBeanDao);
    }
    
    public void clear() {
        sentenceBeanDaoConfig.getIdentityScope().clear();
        wordBeanDaoConfig.getIdentityScope().clear();
    }

    public SentenceBeanDao getSentenceBeanDao() {
        return sentenceBeanDao;
    }

    public WordBeanDao getWordBeanDao() {
        return wordBeanDao;
    }

}