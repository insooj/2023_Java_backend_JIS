/**
 *  js/cookieFunc.js
 */
onload=function(){
	alert("aaa");
}
 function setCookie(name,value,expires,domain,path,secure){
		
		var cookies='';
		// 인코딩처리 3가지
		// escape() : ASCII코드로 변환, 나머지는 유니코드로 변환 -> 이제 안씀
		// encodeURI() : 주소와 관련된 특수문자 https://hankyung.com?key=value&key/value
		//               들을 제외하고 인코딩 처리 -> URI전체를 처리할때 사용
		// encodeURIComponent() : 모든 문자를 인코딩 처리 -> 파라미터를 처리할때 사용
		cookies+=name+"="+encodeURIComponent(value);//1.인코딩처리: ASCII코드로 변환 'A' = 65
		var date = new Date();
		date.setDate(date.getDate()+expires);
		cookies+=";expires="+date.toUTCString();// 2.유효기간설정: 세계표준시로 설정  
		//cookies+=";max-age="+(1000*1*60*60*24)
		
		if(domain){
			cookies+=";domain="+domain;//특정 도메인, 하위도메인에서만 사용: hk.com, sub.hk.com
		}
		if(path){
			cookies+=";path="+path;//경로 : hk.com/admin/board
		}
		if(secure){
			cookies+=";secure="+secure;//https로만 접근할 수 있는지 여부 true/false
		}
		document.cookie=cookies;//최종 저장하는 코드
	}
	
	function removeCookie(name){
		var date=new Date();
		date.setDate(date.getDate()-1);// 현재날짜의 1일전으로 셋팅
		document.cookie=name+"=;expires="+date.toUTCString();
		//document.cookie=name+"=;max-age=0";
	}
	
	//실습문제: 원하는 쿠키 값을 가져오기 -> "name=value;name=value;name=value"
	function getCookie(cookieName){
		
		//String.split(";"): ";"기준으로 잘라서 배열로 반환 	
		var cookieSplit=document.cookie.split(";");//["name=value","name=value",...]
		var value="";
		for(var i=0;i<cookieSplit.length;i++){
			if(cookieSplit[i].indexOf(cookieName)!=-1){// 존재여부 판단
			//        "name=value". [name,value][1]
				value=cookieSplit[i].split("=")[1];  // "name=value" -> [name,value]
				console.log("저장된 쿠키값:"+value);
				return  decodeURIComponent(value);
			}
		}
		console.log("저장된 쿠키값:"+value);
		return null;
	}