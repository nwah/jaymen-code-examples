package com.jaymen.candidate.mvc.controller.candidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.jaymen.candidate.mvc.service.CandidateService;
import com.jaymen.cv.schemas.types.Candidate;

/**
 * JavaBean Form controller that is used to delete an existing <code>Candidate</code>.
 */
@Controller
@RequestMapping("/deleteCandidate.do")
@SessionAttributes(types = Candidate.class)
public class DeleteCandidateForm {
	
	private CandidateService candidateService;

	@Autowired
	public DeleteCandidateForm(CandidateService candidateService) {
		super();
		this.candidateService = candidateService;
	}

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields(new String[] {"id"});
    }
    
	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(@RequestParam("candidateId") Integer candidateId, Model model) {
		Candidate candidate = candidateService.get(candidateId);
		model.addAttribute(candidate);
		return "candidateDelete";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(@RequestParam("candidateId") Integer candidateId) {
		Boolean success = candidateService.delete(candidateId);	
		if(!success){
			return "error";
		}
		return "redirect:candidates.do";
	}

}
