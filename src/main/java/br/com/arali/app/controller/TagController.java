package br.com.arali.app.controller;

import br.com.arali.app.model.Card;
import br.com.arali.app.model.Tag;
import br.com.arali.app.model.dao.DAOCard;
import br.com.arali.app.model.dao.DAODeck;
import br.com.arali.app.model.dao.DAOTag;
import br.com.arali.app.util.Controller;
import br.com.arali.app.util.DAO;
import br.com.arali.app.util.DefaultController;
import com.google.gson.Gson;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import javax.xml.bind.JAXBException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Controller(resource = "tags")
public class TagController extends DefaultController {

    public TagController(){
        Spark.get("/tag", (req, res) -> {
            return new ModelAndView(new HashMap(), "src/main/resources/template/tag.html");
        }, new MustacheTemplateEngine());
    }

    @Override
    public Object create(Request req, Response res) {
        DAO<Tag> dao = new DAOTag();
        Gson gson    = new Gson();
        Tag tag      = gson.fromJson(req.body(), Tag.class);
        try {
            tag = dao.insert(tag);
            res.status(202);
        } catch (SQLException | ClassNotFoundException | JAXBException e) {
            e.printStackTrace();
            res.status(505);
        }
        return gson.toJson(tag);
    }

    @Override
    public Object update(Request req, Response res) {
        return null;
    }

    @Override
    public Object delete(Request req, Response res, Long id) {
        Gson gson      = new Gson();
        DAOTag dao     = new DAOTag();
        boolean result = false;
        try {
            result = dao.delete(id);
        } catch (SQLException | ClassNotFoundException | JAXBException e) {
            e.printStackTrace();
        }
        return gson.toJson(result);
    }

    @Override
    public Object find(Request req, Response res, Long id) {
        Tag tag = null;
        try {
            tag = new DAOTag().find(id);
        } catch (SQLException | ClassNotFoundException | JAXBException e) {
            e.printStackTrace();
        }
        return new Gson().toJson(tag);
    }

    @Override
    public Object findAll(Request req, Response res) {
        List<Tag> tags = null;
        try {
            tags = new DAOTag().findAll();
        } catch (SQLException | ClassNotFoundException | JAXBException e) {
            e.printStackTrace();
        }
        return new Gson().toJson(tags);
    }
}
