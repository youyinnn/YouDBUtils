package com.lab.service;

import com.github.youyinnn.youdbutils.ioc.annotations.Autowired;
import com.github.youyinnn.youdbutils.ioc.annotations.YouService;

/**
 *
 * @author youyinnn
 *
 */
@YouService
public class AService {

    @Autowired
    private BService bService;

    public BService getbService() {
        return bService;
    }
}
