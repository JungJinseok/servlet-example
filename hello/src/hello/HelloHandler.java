package hello;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloHandler implements CommandHandler{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setAttribute("hello", "æ»≥Á«œººø‰!");
		return "/hello.jsp";
	}

}
