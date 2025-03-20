package travel.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import travel.model.TravelBean;
import travel.model.TravelDao;

@Controller
public class TravelUpdateController {
	
	private final String command = "update.tv";
	private final String gotoPage = "redirect:/list.tv";
	private final String getPage = "TravelUpdateForm";
	
	@Autowired
	TravelDao tdao;
	
	@RequestMapping(value=command, method= RequestMethod.GET)
	public String doAction(@RequestParam(value="num", required = true) int num, Model model) {
		
		TravelBean travel = tdao.getOneSelect(num);
		model.addAttribute("travel",travel);
		return getPage;
	}
	@RequestMapping(value=command, method= RequestMethod.POST)
	public String doAction(@ModelAttribute("travel") @Valid TravelBean travel,BindingResult result) {
		
		if(result.hasErrors()) {
			return getPage;
		}
		
		int cnt = tdao.updateTravel(travel);
		
		return gotoPage;
	
	
	}
}