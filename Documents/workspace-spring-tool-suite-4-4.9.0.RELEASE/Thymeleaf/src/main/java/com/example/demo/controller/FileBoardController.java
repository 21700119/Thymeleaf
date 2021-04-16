package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.bean.FileBoardVO;
import com.example.demo.bean.FileVO;
import com.example.demo.bean.GoogleOAuthRequestVO;
import com.example.demo.bean.GoogleOAuthResponseVO;
import com.example.demo.bean.ReservationVO;
import com.example.demo.bean.RoomVO;
import com.example.demo.bean.UserVO;
import com.example.demo.service.FileBoardService;
import com.example.demo.service.RoomService;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

@Controller
@RequestMapping("/fileBoard")
public class FileBoardController {

	@Autowired
	FileBoardService fboardService;
	
	@Autowired
	RoomService roomService;
	
	@Autowired
	UserService userService;

	@RequestMapping(value = "/list")
	private String fileBoardList(Model model, HttpServletRequest request) {

		List<RoomVO> testList = new ArrayList<>();
		int id = 1;
		testList = roomService.getRoomList(id);
		model.addAttribute("testlist", testList);
		model.addAttribute("id", id);
		return "/fileBoard/list";
	}
	
	@RequestMapping(value = "/list_")
	private String fileBoardList_Login(Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession(); 
	    UserVO ud = (UserVO) session.getAttribute("user");
	    UserVO newud = new UserVO();
	    newud.setEmail(ud.getEmail());
	    newud.setName(ud.getName());
	    
	    Integer user_id;
		user_id = userService.readUserIDByEmail(ud.getEmail());
		if( user_id == null) user_id = 0;
		
		if(user_id > 0) { session.setAttribute("user", ud); }
		else { userService.createUserInfo(ud); }

		List<RoomVO> testList = new ArrayList<>();
		int id = 1;
		testList = roomService.getRoomList(id);
		model.addAttribute("testlist", testList);
		model.addAttribute("id", id);
		return "/fileBoard/list";
	}
	
	@RequestMapping(value = "/blist")
	private ModelAndView bList(ModelAndView mv, HttpServletRequest request,Model model) {

		List<RoomVO> testList = new ArrayList<>();
		int id = Integer.parseInt(request.getParameter("id"));
		testList = roomService.getRoomList(id);
		mv.addObject("testlist", testList);
		model.addAttribute("id", id);
		mv.setViewName("ajaxContent/listContent");
		return mv;
	}

	@RequestMapping("/detail")
	private void getReservationList(Model model,HttpServletRequest request) {
		int u_id = ((UserVO)request.getSession().getAttribute("user")).getId();
		model.addAttribute("detail", roomService.getReservationList(u_id));
	}

	@RequestMapping("/insert/{id}")
	private String fileBoardInsertForm(@PathVariable("id") int id, Model model, 
			HttpServletRequest request) {
		
		List<RoomVO> testList = new ArrayList<>();
		testList = roomService.getRoomList(id);
		String room_name = testList.get(0).getName();
		model.addAttribute("id", id);
		model.addAttribute("room_name", room_name);
		
		return "fileBoard/insert";
	}

	@RequestMapping("/insertProc/{id}")
	private String fileBoardInsertProc(@PathVariable("id") int id, 
			HttpServletRequest request) throws ParseException{
		
		int r_id = id;
		int u_id = ((UserVO)request.getSession().getAttribute("user")).getId();
		String prof = request.getParameter("prof");
		String department = request.getParameter("department");
		String purpose = request.getParameter("purpose");
		String contact = request.getParameter("contact");
		String info = request.getParameter("info");
		String start_time = request.getParameter("start_time");
		String end_time = request.getParameter("end_time");
		
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date rdate = transFormat.parse(request.getParameter("r_date"));
		java.sql.Date r_date = new java.sql.Date(rdate.getTime());
		
		ReservationVO r = new ReservationVO();
		r.setR_id(r_id);
		r.setU_id(u_id);
		r.setProf(prof);
		r.setDepartment(department);
		r.setPurpose(purpose);
		r.setContact(contact);
		r.setInfo(info);
		r.setR_date(r_date);
		r.setStart_time(start_time);
		r.setEnd_time(end_time);

		roomService.createReservation(r);

		return "forward:/fileBoard/detail"; // 객체 재사용
	}

	@RequestMapping("/update")
	private String fileBoardUpdateForm(HttpServletRequest request, Model model) {
		int id = Integer.parseInt(request.getParameter("id"));
		model.addAttribute("detail", roomService.reservationDetail(id));
		return "ajaxContent/detailModal";
	}

	@RequestMapping("/updateProc")
	private String fileBoardUpdateProc(HttpServletRequest request) throws ParseException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		String purpose = request.getParameter("purpose");
		String prof = request.getParameter("prof");
		String info = request.getParameter("info");
		String contact = request.getParameter("contact");
		String start_time = request.getParameter("start_time");
		String end_time = request.getParameter("end_time");	
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date rdate = transFormat.parse(request.getParameter("r_date"));
		java.sql.Date r_date = new java.sql.Date(rdate.getTime());
		
		ReservationVO r = new ReservationVO();
		r.setId(id);
		r.setPurpose(purpose);
		r.setProf(prof);
		r.setInfo(info);
		r.setContact(contact);
		r.setR_date(r_date);
		r.setStart_time(start_time);
		r.setEnd_time(end_time);

		roomService.updateReservation(r);

		return "forward:/fileBoard/detail";
	}

	@RequestMapping("/delete/{id}")
	private String fileBoardDelete(@PathVariable("id") int id) {
		roomService.deleteReservation(id);
		
		return "forward:/fileBoard/detail";
	}
	
	@RequestMapping(value = "/search", method = {RequestMethod.POST})
	public ModelAndView searchProblem(@RequestParam(value="orderValue", defaultValue="") String orderValue, ModelAndView mv ) {
		List<ReservationVO> detail = roomService.readReservByOrder(orderValue);
		
		mv.addObject("detail", detail);
		mv.setViewName("ajaxContent/orderList");

		return mv;
	}
	
	@RequestMapping("/mypage")
	private String mypage() {
		
		return "/fileBoard/mypage";
	}
	
//	@RequestMapping(value = "/search", method = {RequestMethod.POST})
//	public ModelAndView searchProblem(
//		@RequestParam(value="page", defaultValue="1") int page,
//		@RequestParam(value="orderValue", defaultValue="") String orderValue, ModelAndView mv ) {
	
		// pagination
//		int listCnt = recommendService.readRecomListCnt();
//		int list = 10;
//		int block = 10;
//
//		int pageNum = (int) Math.ceil((float)listCnt/list);
//		int nowBlock = (int)Math.ceil((float)page/block);
//
//		int s_point = (page-1)*list;
//
//		int s_page = nowBlock*block - (block-1);
//		if (s_page <= 1) {
//			s_page = 1;
//		}
//		int e_page = nowBlock*block;
//			if (pageNum <= e_page) {
//				e_page = pageNum;
//		}
//		List<ReservationVO> detail = roomService.readReservByOrder(orderValue);
//		
//		mv.addObject("detail", detail);
//		mv.setViewName("ajaxContent/orderList");
//
//		return mv;
//	}
	

}