package zpark.ext.web;


import zpark.ext.annotations.web.RequestMethod;
import zpark.ext.annotations.web.RequestParam;
import zpark.ext.annotations.web.WebServlet;
import zpark.ext.web.Action;

@WebServlet(name = "log", urlPattern ="/user" )
public class UserAction implements Action {

	@RequestMethod
	public String execute(@RequestParam(name="name", defaultValue="10") String name, @RequestParam(name="age") Integer age, Integer test) {
		System.out.println("user....");
		return null;
	}

}
