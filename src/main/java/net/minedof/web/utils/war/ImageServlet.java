package net.minedof.web.utils.war;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.minedof.web.model.dao.EnterpriseDao;
import net.minedof.web.model.entity.Enterprise;
import net.minedof.web.model.entity.Photo;

/**
 * Servlet implementation class ImageServlet
 */
@WebServlet("/image")
@Slf4j

//lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Inject
    EnterpriseDao enterpriseDao;

    /**
     * methode qui permet d'afficher une image
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UUID id = UUID.fromString(request.getParameter("id"));
            UUID idImg = UUID.fromString(request.getParameter("i"));
            Enterprise enterprise = enterpriseDao.read(id);

            response.setContentType("image.jpg");
            response.setContentType("image.png");

            Optional<Photo> opt = enterprise.getImage().stream().filter(p -> p.getId().equals(idImg)).findFirst();
            if (opt.isPresent()) {
                response.getOutputStream().write(opt.get().getImage());
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("An error occur to write image.", e);
            }
        }
    }
}


