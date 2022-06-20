package com.train.controller;


import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.train.model.Passenger;
import com.train.model.Ticket;
import com.train.model.Train;
import com.train.model.User;
import com.train.repository.TrainRepository;
import com.train.repository.UserRepository;
import com.train.controller.UserController;


@RestController
@RequestMapping("/train")
public class TrainController {

	@Autowired
	private TrainRepository trp;
	
	@Autowired
	UserRepository userRepo;

	
	Train trraaaiin ;
	String trvldate;

	
	@RequestMapping(value="/trainData/{trNo}",method=RequestMethod.GET)
    public Train getTrain(@PathVariable int trNo)
    {
    	
		Optional<Train> tr = trp.findById(trNo);
    	
    	if(tr.isPresent())
    	{
    		Train t = tr.get();
    		trraaaiin = t;
    		return t;
    	}
    	else {
    		
    		return null;
    	}
    	
       	
    	
    }
	
	@RequestMapping(value="/trvelDate",method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus getTrain(@RequestBody String trvdate)
    {
		
		trvldate = trvdate.substring(15, 25);	
    	return (HttpStatus.OK);   	
    	
    }

	    
	    // trying through thymleaf feature and spring boot
	    
	    @RequestMapping(value="/getTrain/{trNo}", method=RequestMethod.GET)
		public ModelAndView gettrainlist(@PathVariable int trNo)
		{
	    	Optional<Train> tr = trp.findById(trNo);
	    	
	    	ModelAndView model = new ModelAndView("AvailableTrainList.html");

	    	if(tr.isPresent())
	    	{
	    		Train t = tr.get();
	    		
	    		 model.addObject("train",t);
				return model;
	    	}	
	    	else {
	    		ModelAndView model2 = new ModelAndView("loggedIn.html");
	    		return model2;
	    	}	    	
		}
	    
	    @RequestMapping(value="/bookTrain/{trNo}", method=RequestMethod.GET)
		public ModelAndView BookTicket(@PathVariable int trNo)
		{
	    	Optional<Train> tr = trp.findById(trNo);
	    	
	    	ModelAndView model = new ModelAndView("Booking.html");

	    	if(tr.isPresent())
	    	{
	    		Train t = tr.get();
	    		 model.addObject("train",t);
				return model;
	    	}	
	    	else {
	    		ModelAndView model2 = new ModelAndView("AvailableTrainList.html");
	    		Train t = tr.get();
	    		model2.addObject("train",t);
	    		return model2;
	    	}	    	
		}
	    
	    @RequestMapping(value="/addPassenger",method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	    public HttpStatus getTrain(@RequestBody List<Passenger> p)
	    {	
			
			System.out.println("train object ha ye :"+trraaaiin);
			System.out.println("String Format me javaScript se aya hua date ha "+trvldate);
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			
			try {
				Date travelllDate = format.parse(trvldate);
				System.out.println("conversion k bad "+travelllDate);
				Ticket tkkt = new Ticket(travelllDate,trraaaiin);
				for(Passenger psng:p) {
					tkkt.addPassenger(psng.getName(), psng.getAge(), psng.getGeneder());
				}
				try {
					tkkt.writeTicket();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
	    	System.out.println(p);
	    	
	    	return (HttpStatus.OK);   	
	    	
	    }
	    
	   
	    
	    @RequestMapping(value="/logout/{email}",method=RequestMethod.GET)
	    public  HttpStatus logOutUserData(@PathVariable String email)
	    {
			System.out.println(email);
			User usr = userRepo.getUser(email);
			usr.setLogStatus(false);
			userRepo.save(usr);
		
			return HttpStatus.OK; 	
	    	
	    }
	    
	    @RequestMapping(value="/getSource",method=RequestMethod.GET)
	    public ResponseEntity<List<String>> getTrainSource()
	    {
	    	
			List<String> sources = trp.getTrainsources();
			return new ResponseEntity<List<String>>(sources,HttpStatus.OK);
	
	    }
	    
	    @RequestMapping(value="/getDestinations",method=RequestMethod.GET)
	    public ResponseEntity<List<String>> getTrainDestinations()
	    {
	    	
			List<String> sources = trp.getTraindestination();
			return new ResponseEntity<List<String>>(sources,HttpStatus.OK);
	
	    }
	    
	    
	    @RequestMapping(value="/getAllTrainNo",method=RequestMethod.GET)
	    public ResponseEntity<List<Integer>> getAllTrainNumbers()
	    {
	    	
			List<Integer> TrainNolist = trp.getAllTrainNo();
			return new ResponseEntity<List<Integer>>(TrainNolist,HttpStatus.OK);
	
	    }
	    
	    
	    
	    
	    @GetMapping("/searchTrain/{source}/{dest}")
		public String gettrainlist(@PathVariable String source,@PathVariable String dest,Model model)
		{
	    
	    	List<Train> listtrains = trp.getAllTrain(source, dest);
	    	model.addAttribute("trains", listtrains);
	    	
	    	return "trainList.html";

	    	
		}
	    
	    
	    
	    
	   
}
