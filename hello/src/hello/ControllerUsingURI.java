package hello;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerUsingURI extends HttpServlet{
	// <커맨드, 핸들러인스턴스> 매핑 정보 저장
	private Map commandHandlerMap = new java.util.HashMap();
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		String configFile = config.getInitParameter("configFile");
		Properties prop = new Properties();
		FileInputStream fis = null;
		try{
			String configFilePath = config.getServletContext().getRealPath(configFile);
			fis = new FileInputStream(configFilePath);
			prop.load(fis);
		} catch (IOException e) {
			throw new ServletException(e);
		} finally {
			if(fis != null) {
				try{
					fis.close();
				}catch (IOException ex){
					
				}
			}
		}
		
		Iterator keyIter = prop.keySet().iterator();
		while(keyIter.hasNext()){
			String command = (String) keyIter.next();
			String handlerClassName = prop.getProperty(command);
			try {
				Class handlerClass = Class.forName(handlerClassName);
				Object handlerInstance = handlerClass.newInstance();
				commandHandlerMap.put(command, handlerInstance);
			}catch(ClassNotFoundException e){
				throw new ServletException(e);
			}catch(InstantiationException e){
				throw new ServletException(e);
			}catch(IllegalAccessException e) {
				throw new ServletException(e);
			}
		}
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req,resp);
	}

	private void process(HttpServletRequest req, HttpServletResponse resp) {
		String command = req.getRequestURI(); // /hello.do
		if(command.indexOf(req.getContextPath()) == 0) {
			command = command.substring(req.getContextPath().length()); // /hello.do
		}
		
		CommandHandler handler = (CommandHandler) commandHandlerMap.get(command);
		if(handler == null) {
			handler = new NullHandler();
		}
		String viewPage = null;
		try{
			viewPage = handler.process(req,resp);
		}catch(Throwable e) {
			try {
				throw new ServletException(e);
			} catch (ServletException e1) {
				e1.printStackTrace();
			}
		}
		
		RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
		try {
			dispatcher.forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
