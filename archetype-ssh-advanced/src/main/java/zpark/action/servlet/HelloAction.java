package zpark.action.servlet;

import zpark.ext.annotations.web.PathParam;
import zpark.ext.annotations.web.RequestMethod;
import zpark.ext.annotations.web.RequestMethod.METHOD;
import zpark.ext.annotations.web.RequestParam;
import zpark.ext.annotations.web.WebServlet;
import zpark.ext.web.Action;
import zpark.ext.web.view.FreeMarkerView;
import zpark.ext.web.view.View;

@WebServlet(name = "hello", urlPattern = "/hello/*")
public class HelloAction implements Action {

    @RequestMethod(path = "a", cacheable = true)
    public View a() {
        System.out.println("a");
        return new FreeMarkerView("user.ftl", null);
    }

    @RequestMethod(path = "b", evictcache = true, method = METHOD.POST)
    public View b() {
        System.out.println("b");
        return null;
    }

    @RequestMethod(path = "c")
    public View c() {
        System.out.println("c");
        return new FreeMarkerView("user.ftl", null);
    }

    @RequestMethod(path = "d", method = METHOD.POST)
    public View d() {
        System.out.println("d");
        return null;
    }

    @RequestMethod(path = "e/{name}")
    public View e(@PathParam(name = "name") String name) {
        System.out.println("e");
        return new FreeMarkerView("user.ftl", null);
    }

    @RequestMethod(path = "f/{id}/page/{num}")
    public View f(@PathParam(name = "id") Integer id, @PathParam(name = "num") Integer num,
            @RequestParam(name = "name") String name) {
        System.out.println("f");
        return new FreeMarkerView("user.ftl", null);
    }

}
