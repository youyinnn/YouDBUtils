package com.lab.service;

import com.lab.dao.BDao;
import com.github.youyinnn.youdbutils.ioc.ServiceIocBean;
import com.github.youyinnn.youdbutils.ioc.annotations.Autowired;
import com.github.youyinnn.youdbutils.ioc.annotations.Transaction;
import com.github.youyinnn.youdbutils.ioc.annotations.YouService;

/**
 *
 * @author youyinnn
 *
 */
@YouService(scope = ServiceIocBean.SINGLETON)
public class BService {

    @Autowired
    private CService service;

    @Autowired
    private BDao bDao;

    @Transaction(allowNoneffectiveUpdate = false)
    public void aaa() {

        bDao.c();
        bDao.d();

    }

    @Transaction
    public void e() {
        bDao.e();
    }

    public BDao getbDao() {
        return bDao;
    }

    public void getCService() {
        System.out.println(service);
    }
}
