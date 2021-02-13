package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm").withLocale(Locale.ENGLISH);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getRequestURI().endsWith("/meals/edit")) {
            log.debug("forward to add new meal");
            String parameterId = request.getParameter("id");
            if (parameterId != null)
                request.setAttribute("mealTo", MealsUtil.getMealToByIdWithoutExcess(Integer.parseInt(parameterId)));
            else
                request.setAttribute("mealTo", new MealTo(null, null, 0, 0, false));
            request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);
        } else {
            log.debug("forward to meals");
            request.setAttribute("listMealTo", MealsUtil.getAll());
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("save") != null) {
            LocalDateTime dateTime = null;
            String dateTimeString = request.getParameter("datetime");
            if (dateTimeString == null)
                throw new IllegalArgumentException("Wrong dateTime");
            dateTime = LocalDateTime.parse(dateTimeString, dtf);
            String description = request.getParameter("description");
            String caloriesString = request.getParameter("calories");
            int calories = 0;
            if (caloriesString == null)
                throw new IllegalArgumentException("Wrong calories");
            calories = Integer.parseInt(caloriesString);
            MealsUtil.createUpdateMeal(dateTime, description, calories, Integer.parseInt(request.getParameter("id")));
        }
        log.debug("forward to meals");
        response.sendRedirect("meals.jsp");
    }
}
