package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Vladimir on 11.12.2016.
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to meals");


        List<MealWithExceed> mealWithExceedList = MealsUtil.getFilteredWithExceeded(MealsUtil.meals, LocalTime.of(0, 0), LocalTime.of(23, 0), 2000);

        request.setAttribute("mealWithExceedList", mealWithExceedList);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
