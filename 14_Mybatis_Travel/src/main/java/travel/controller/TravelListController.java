package travel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import travel.model.TravelBean;
import travel.model.TravelDao;
import utility.Paging;

@Controller
public class TravelListController {
		
		private final String command ="/list.tv";
		private final String gotoPage ="travelList";
		
		@Autowired
		TravelDao tdao;
		
		@RequestMapping(value = command)
		public ModelAndView doAction(@RequestParam(value = "keyword",required = false) String keyword,
									 @RequestParam(value = "whatColumn",required = false) String whatColumn,
									 @RequestParam(value = "pageNumber",required = false) String pageNumber,
									 HttpServletRequest request) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("whatColumn", whatColumn);
			map.put("keyword", "%"+keyword+"%");
			
			System.out.println("pageNumber:"+pageNumber);
			
			int totalCount = tdao.getTotalCount(map);
			System.out.println("totalCount:"+totalCount);
			
			String url= request.getContextPath() + command;
			System.out.println("url:"+url);//프로젝트이름과 패키지 이름까지 다 포함된 주소
			
			Paging pageInfo = new Paging(pageNumber,"2",totalCount,url,whatColumn,keyword);
			
			System.out.println("offset:"+pageInfo.getOffset());
			System.out.println("limit:"+pageInfo.getLimit());
			
			List<TravelBean> lists = tdao.getAllList(pageInfo,map);
			ModelAndView mav = new ModelAndView();
			mav.addObject("travelLists",lists);
			mav.addObject("totalCount",totalCount);
			mav.addObject("pageInfo",pageInfo);
			
			mav.setViewName(gotoPage);
			
		 return mav;
		 //kim 도 수정함
		 //kim 추가함
		 //kim 추가함
		}
		
}
