package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MemoryMealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Vladimir on 11.12.2016.
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);

    private MemoryMealDao mealDao = new MemoryMealDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to meals");

        String action = request.getParameter("action");
        String path;

        switch (action) {
            case "delete":
                int mealId = Integer.parseInt(request.getParameter("mealId"));
                mealDao.delete(mealId);
                request.setAttribute("mealWithExceedList", mealDao.getAll());
                path = "/meals.jsp";
                break;
            case "edit":
                mealId = Integer.parseInt(request.getParameter("mealId"));
                request.setAttribute("meal", mealDao.getMealById(mealId));
                path = "/meal.jsp";
                break;
            default:
                request.setAttribute("mealWithExceedList", mealDao.getAll());
                path = "/meals.jsp";
                break;
        }
        request.getRequestDispatcher(path).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String dateTime = request.getParameter("DateTime");
        LocalDateTime localDateTime = TimeUtil.stringToLocalDateTime(dateTime);
        String description = request.getParameter("Description");
        int calories = Integer.parseInt(request.getParameter("Calories"));

        try {
            int id = Integer.parseInt(request.getParameter("Id"));
            mealDao.update(id, localDateTime, description, calories);
        } catch (NumberFormatException e){
            Meal meal = new Meal(localDateTime, description, calories);
            mealDao.add(meal);
        }

        response.sendRedirect("meals?action=");
    }
}
