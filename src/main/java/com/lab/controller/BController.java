package com.lab.controller;

import com.lab.service.BService;
import com.github.youyinnn.youdbutils.ioc.annotations.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author youyinnn
 */
@WebServlet("/b")
public class BController extends HttpServlet {

    @Autowired
    private BService bService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println(bService);
    }
}
