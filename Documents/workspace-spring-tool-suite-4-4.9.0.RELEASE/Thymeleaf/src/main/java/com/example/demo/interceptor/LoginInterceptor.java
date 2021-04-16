package com.example.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.bean.UserVO;
import com.example.demo.service.UserService;

@Component
public class LoginInterceptor  implements HandlerInterceptor{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UserService userService;
	
	 @Override
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
	    		Object handler ) throws Exception {
		 
	        //logger.info("[MYTEST] preHandle");
	        
	        if(request.getSession().getAttribute("tempUser") == null) {
			        return false;
			}else {
				//request.setAttribute("userID", ((UserVO)request.getSession().getAttribute("tempUser")).getId());
				HttpSession session = request.getSession(); 
			    UserVO ud = (UserVO) session.getAttribute("tempUser");
			    session.setAttribute("user", ud);
			}
	        HandlerInterceptor.super.preHandle(request, response, handler);
	        
	        return true;
	    }
}
