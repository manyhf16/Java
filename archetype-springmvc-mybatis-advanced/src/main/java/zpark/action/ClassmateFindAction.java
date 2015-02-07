package zpark.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import zpark.entity.Classmate;
import zpark.service.ClassmateService;

@Controller
@RequestMapping("classmate")
public class ClassmateFindAction {
	
	@Autowired
	private ClassmateService service;
	
	@RequestMapping("all")
	public void all(Model model) {		
		Map<String, String> map = new HashMap<String, String>();
		map.put("classmate_all", "active");
		model.addAttribute("activeMap", map);
		
		List<Classmate> list = service.findAll();
		model.addAttribute("list", list);
	}
	
	@RequestMapping("lost")
	public void lost(Model model) {		
		Map<String, String> map = new HashMap<String, String>();
		map.put("classmate_lost", "active");
		model.addAttribute("activeMap", map);
		
		List<Classmate> list = service.findAllLost();
		model.addAttribute("list", list);
	}

}
