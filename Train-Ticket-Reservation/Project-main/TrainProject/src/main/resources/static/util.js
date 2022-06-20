
// register API call
console.log("home page loaded")


var regform = document.getElementById('regform')
regform.addEventListener('submit', function(e){
    e.preventDefault()
    console.log('Clicked on register ...')

     newpass = regform.passwd.value
     cnfPass = regform.cnfpasswd.value

     if (newpass == cnfPass ){

         Bool = CheckLength(newpass)
         if (Bool){
             register()
         }
         else{
         alert('Password should be of at least 8 character')
         }

     }
     else{
     alert("New Password not matched with confirm password Try again")
     }

//    document.getElementById('form-button').classList.add("hidden");

})


// Change Password API Call
function CheckLength(NewPassword){

 if (NewPassword.length  < 8){

     return false;
 }
 else{
     return true;
 }
}


function register(){

    const userdata = {};
    userdata["email"] = regform.Email.value;
        //userFormData["email"] = "sachin6734@gmisl.com"
        //userFormData["password"] = "password"
   userdata["password"] = regform.passwd.value;
       

    console.log('userFormData ka  data  :', userdata)

    var urlreg = "http://localhost:8085/register"
   
    fetch(urlreg, {
        method:'POST',
        headers:{
            "Content-Type": "application/json",
            'dataType': 'json'
            
        },
        body:JSON.stringify(userdata)
        

    })
    .then((response) => response.json())
    .then((data) =>CheckRegister(data))
    
    
    function CheckRegister(data){
		console.log(data)
		if (data == 'OK'){
			window.location.href = "http://localhost:8085"
		}
		else{
			alert("User with this Email already registered")
		}
	}

}



/*// Login API Call

var loginform = document.getElementById('loginform')

loginform.addEventListener('submit', function(e){
    e.preventDefault()
    console.log('Clicked on login...')
    login()
//    document.getElementById('form-button').classList.add("hidden");

})
var loginformData = {
        'password':null,
        'email': null
    }

//var Auth_Token

function login(){

    loginformData.password = loginform.password.value
    loginformData.email = loginform.email.value

    console.log(loginformData)

	var urllog = "http://localhost:8085/user/loginDataBack"
	   
	    fetch(urllog, {
	        method:'POST',
	        headers:{
	            "Content-Type": "application/json",
	            'dataType': 'json'
	            
	        },
	        body:JSON.stringify(loginformData)
	        
	
	    })
	    .then((response) => response.json())
	    .then((data) =>printdata(data))
	    
	    
	    function printdata(data){
		
		if(data["logStatus"] == true){
			/*document.getElementById("logstat").innerHTML=data["email"];
			document.getElementById("logoutbtn").style.visibility = 'visible';
			document.getElementById("signupbtn").style.visibility = 'hidden';
			document.getElementById("logInbtn").style.visibility = 'hidden';
			localStorage.setItem("logStatus",true)
			localStorage.setItem("email",data["email"])
			localStorage.setItem("admin",data["admin"])
			if(data["admin"] == false){
				document.getElementById("trAction").style.visibility = 'hidden';
			}
		}
		
		console.log(data,"came from login controller")
		console.log(data["email"],"came from login controller")
		console.log(data["logStatus"],"came from login controller")
		console.log(data["admin"],"came from login controller")
		
		$('#submitLogin').click(function() {
		    $('#myModal').modal('hide');
		});
		window.location.href="http://localhost:8085"
	}*/

// search Train By Source DeSTINATION

var searchTrainForm = document.getElementById('searchTrain')

searchTrainForm.addEventListener('submit', function(e){
    e.preventDefault()
    console.log('Clicked on login...')
    searchTrainByDestSource()
//    document.getElementById('form-button').classList.add("hidden");

})
var searchTrainData = {
        'Source':null,
        'Destination':null,
        'TravelDate': null
    }

//var Auth_Token

function searchTrainByDestSource(){
	
	

    searchTrainData.Source = searchTrainForm.source.value
    searchTrainData.Destination = searchTrainForm.dest.value
    searchTrainData.TravelDate = searchTrainForm.TravelDate.value
    
    todayDate = new Date();
    selectedDate = new Date(searchTrainData.TravelDate);
    
    if(todayDate > selectedDate){
	alert(" Select future date only ")
    }
    else{
    
	window.localStorage.setItem("travelDate",searchTrainData.TravelDate);
    console.log(searchTrainData)
    var urltrainsearch = "http://localhost:8085/searchTrain/"+searchTrainData.Source+"/"+searchTrainData.Destination
    window.location.href=urltrainsearch
    
   

    }
    }


// search Train By Train Number
var searchTrainNumberForm = document.getElementById('searchTrainNo')

searchTrainNumberForm.addEventListener('submit', function(e){
    e.preventDefault()
    console.log('Clicked on login...')
    searchTrain()
//    document.getElementById('form-button').classList.add("hidden");

})
var TrainformData = {
        'trNo':null,
        'TravelDate': null
    }

//var Auth_Token

function searchTrain(){

    TrainformData.trNo = searchTrainNumberForm.trNo.value
    TrainformData.TravelDate = searchTrainNumberForm.jdate.value

	var trNo=TrainformData.trNo
	console.log(typeof(trNo),"Type of TrNo")
	
	todayDate = new Date();
    selectedDate = new Date(TrainformData.TravelDate);
    
    if(todayDate > selectedDate){
	alert(" Select future date only ")
    }
    else{
	
	window.localStorage.setItem("travelDate",TrainformData.TravelDate);
	window.localStorage.setItem("trainNo",TrainformData.trNo);
    var url = "http://localhost:8085/train/getTrain/"+trNo

    console.log(TrainformData)
    window.location.href=url
    
  }  
}