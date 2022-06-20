package com.train.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.train.model.Train;
import com.train.model.User;
import com.train.repository.AdminRepository;
import com.train.repository.TrainRepository;

@RestController
@RequestMapping("/admin")
public class AdminController {

	
	@Autowired
	AdminRepository adminRepo;
	
	@Autowired
	private TrainRepository trp;
	
	@RequestMapping(value="/addTrain",method=RequestMethod.POST,produces = "application/json", consumes = "application/json")
    public  HttpStatus AddTrain(@RequestBody Train train)
    {
		if(trp.TrainExistByTrainNo(train.getTrainNo())) {
			System.out.println("Already Exist");
		
			return HttpStatus.BAD_REQUEST;
			
		}
		else {
			trp.save(train);
			System.out.println("Train Added SuccessFully !!!!");
			
			return HttpStatus.OK;
		}		
    	
    }
	
	@RequestMapping(value="/deleteTrain/{trNo}",method=RequestMethod.GET,produces = "application/json", consumes = "application/json")
    public  HttpStatus deleteTrainData(@PathVariable int trNo)
    {
		System.out.println(trNo);
		
		Optional<Train> tr = trp.findById(trNo);
    	
    	if(tr.isPresent())
    	{
    		trp.deleteById(trNo);
    		return HttpStatus.OK; 
    	}
		
		return HttpStatus.BAD_REQUEST; 	
    	
    }
	
	@RequestMapping(value="/addTrain",method=RequestMethod.GET)
    public  ModelAndView AddTrainData()
    {
		ModelAndView model = new ModelAndView("addTrain.html");
		Train train = new Train();
		model.addObject(train);
		return model; 	
    	
    }
	
	
	@RequestMapping(value="/updateTrain/{trNo}",method=RequestMethod.GET)
    public  ModelAndView UpdateTrainData(@PathVariable int trNo)
    {
		
		Optional<Train> train = trp.findById(trNo);
    	
    	if(train.isPresent())
    	{
    		ModelAndView model = new ModelAndView("updateTrain.html");
    		model.addObject("train",train);
    		return model; 
    	}
    	else {
    		ModelAndView model = new ModelAndView("index.html");
    		return model; 
    	}
		
			
    	
    }
	
	
	@RequestMapping(value="/updateTrain",method=RequestMethod.POST)
    public  String UpdateTrainData(@ModelAttribute Train tr)
    {
		trp.save(tr);
		
		
		return "redirect:/welcome";
		
    
    }
		
	
	
	
	
//	@RequestMapping(value="/addTrain",method=RequestMethod.POST,produces = "application/json", consumes = "application/json")
//    public  ModelAndView addTrainData(@RequestBody Train train)
//    {
//		if(trp.TrainExistByTrainNo(train.getTrainNo())) {
//			System.out.println("Already Exist");
//			ModelAndView  model = new ModelAndView("addTrain.html");
//			model.addObject(new Train());
//			return model;
//			
//		}
//		else {
//			trp.save(train);
//			System.out.println("Train Added SuccessFully !!!!");
//			ModelAndView  model = new ModelAndView("addTrain.html");
//			return 
//		}	
//    	
//    }
	
}
