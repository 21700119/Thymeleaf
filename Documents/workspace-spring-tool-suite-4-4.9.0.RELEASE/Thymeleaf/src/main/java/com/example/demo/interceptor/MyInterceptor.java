package com.example.demo.interceptor;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.bean.UserVO;
import com.example.demo.service.UserService;

@Component
public class MyInterceptor implements HandlerInterceptor{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UserService userService;
	
	 @Override
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
	    		Object handler ) throws Exception {
		 
	        //logger.info("[MYTEST] preHandle");
		 	HttpSession session = request.getSession(); 
	        UserVO ud = (UserVO) session.getAttribute("user");
	        
	        int id = 0;
			id = userService.readUserIDByEmail(ud.getEmail());
			if(id > 0) {
				ud.setId(id);
				ud.setIs_admin(userService.readIsAdminByUserID(id));
				session.setAttribute("user", ud);
			}
	        
	        return true;
	    }

	    @Override
	    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
	            ModelAndView modelAndView ) throws Exception {
	    	
	        //logger.info("[MYTEST] postHandle");
			
			HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	    }

	    @Override
	    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
	            Object object, Exception ex) throws Exception {
	    	
	        //logger.info("[MYTEST] afterCompletion");
	    }
	
}
