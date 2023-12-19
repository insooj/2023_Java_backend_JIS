<%@page import="com.hk.fintech.apidto.UserMeAccountDto"%>
<%@page import="java.util.List"%>
<%@page import="com.hk.fintech.apidto.UserMeDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>
<%response.setContentType("text/html; charset=UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>Full Width Pics - Start Bootstrap Template</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
    <!-- Favicon-->
    <link rel="icon" type="image/x-icon" href="resources/assets/favicon.ico" />
    <!-- Core theme CSS (includes Bootstrap)-->
    <link href="/resources/css/styles.css" rel="stylesheet" />
	<style type="text/css">
		.box{border-bottom: 1px solid gray; margin-bottom: 10px;}
		.box > .sub_menu{text-align: right;}
		.addAccount{text-align: right;}
	</style>
	<script type="text/javascript">

		//나의정보2 feign 사용
		function myInfo_feign(){
			location.href="/banking/myinfo_feign";
		}
	
		//나의 정보조회[계좌목록]
		function myInfo(){
			$.ajax({
// 				url:"https://testapi.openbanking.or.kr/v2.0/user/me",
// 				headers:{"Authorization":"Bearer token"},
// 				data:{"user_seq_no":"사용자번호"}
				url:"/banking/myinfo",
				method:"get",
				dataType:"json",
				success:function(data){
					console.log(data.res_list);
					var res_list=data.res_list;//나의 계좌목록 저장
					
					
					//계좌등록 버튼
					$("#list").html("<div class='addAccount'>"
									+"  <button onclick='addAccount()'>계좌등록</button>"
									+"</div>"
							     	);
					//카드등록 버튼
					$("#list").html("<div class='addCard'>"
									+"  <button onclick='addCard()'>카드등록</button>"
									+"</div>"
							     	);
					
					//출력할 내용
					//계좌이름
					//핀테크이용번호 [은행이름]
					for (var i = 0; i < res_list.length; i++) {
						$("#list").append(
							'<div class="box container">'
	                       +'	<div>'
	                       +'	   <h1>'+res_list[i].account_alias+'</h1>'
	                       +'	   <p>'+res_list[i].fintech_use_num+' ['+res_list[i].bank_name+']</p>'
	                       +'	</div>'
	                       +'	<div class="sub_menu"> '
	                       +'		<button  onclick="balance(\''+res_list[i].fintech_use_num+'\',this)" class="balance">잔액조회</button>'
	                       +'	</div>'
	                       +'	<div class="balance_amt"></div>'
	                       +'</div>	'
						)
					}
				}
			});
		}
		
		//잔액조회하기
		function balance(fintech_use_num,btnEle){
			$.ajax({
				url:"/banking/balance",
				method:"get",
				data:{"fintech_use_num":fintech_use_num},
				dataType:"json",
				success:function(data){
					console.log(data);
					var box=$(btnEle).parents(".box").eq(0);
					box.find(".balance_amt").html(
											 "<p>잔액:"+data.balance_amt+"</p>"
											+"<p><button onclick='transactionList(\""+fintech_use_num+"\",this)'>거래내역조회</button></p>"
											+"<div class='transaction_list'></div>"  //거래내역이 출력될 div 
											);          
				},
				error:function(){
					alert("통신실패");
				}
			});
		}
		
		//거래내역조회
		function transactionList(fintech_use_num,btnEle){
			$.ajax({
				url:"/banking/transactionList",
				method:"get",
				data:{"fintech_use_num":fintech_use_num},
				dataType:"json",
				success:function(data){ //data: 응답결과을 받을 변수
					console.log(data.res_list);
					var list="<ul>";
					// data.res_list  -->  배열
					for (var i = 0; i < data.res_list.length; i++) {
						var res=data.res_list[i];// json객체를 가져온다 {key:value,...}
						list+="<li>"+res.tran_date
						            +" ["+res.branch_name+"] "
						            +res.inout_type+" "
						            +res.print_content+":"
						            +res.tran_amt+"</li>"
					}
					list+="</ul>";// <ul><li>거래내역1</li><li>거래내역2</li>..</ul>
					//button .   p    . <div> 
					$(btnEle).parent().next(".transaction_list").html(list);
				}
			});
		}
		
		//계좌등록하기(센터인증 이용기관용: 사용자 인증후에 계좌 등록 가능)
		function addAccount(){
			var url="https://testapi.openbanking.or.kr/oauth/2.0/authorize?"
				   +"response_type=code&" //고정값 code: 인증요청시 반환되는 값의 형식의미
				   +"client_id=4987e938-f84b-4e23-b0a2-3b15b00f4ffd&" //이용기관의 ID
				   +"redirect_uri=http://localhost:8087/banking/addaccount&"//응답URL
				   +"scope=login inquiry transfer&" //토큰의 권한
				   +"state=12345678123456781234567812345678&" //32자리 난수 설정
				   +"auth_type=0"; //0:최초 한번 인증, 2:인증생략
				   
			window.open(url,"인증하기","width=400px,height=600px");	   
		}
		
		//카드등록하기(센터인증 이용기관용: 사용자 인증후에 계좌 등록 가능)
		function addCard(){
			var url="https://testapi.openbanking.or.kr/oauth/2.0/authorize?"
				   +"response_type=code&" //고정값 code: 인증요청시 반환되는 값의 형식의미
				   +"client_id=4987e938-f84b-4e23-b0a2-3b15b00f4ffd&" //이용기관의 ID
				   +"redirect_uri=http://localhost:8087/banking/addaccount&"//응답URL
				   +"scope=login cardinfo&" //토큰의 권한
				   +"state=12345678123456781234567812345678&" //32자리 난수 설정
				   +"auth_type=0"; //0:최초 한번 인증, 2:인증생략
				   
			window.open(url,"인증하기","width=400px,height=600px");	   
		}
	</script>

</head>
<body>
<!-- Responsive navbar-->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="#!">Start Fintech</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                    <li class="nav-item"><a class="nav-link active" aria-current="page" href="/">Home</a></li>
                    <li class="nav-item"><a class="nav-link" href="#!">About</a></li>
                    <li class="nav-item"><a class="nav-link" href="#!">Contact</a></li>
                    <li class="nav-item"><a class="nav-link" href="#!">${sessionScope.ldto.username}님</a></li>
                    <li class="nav-item"><a class="nav-link" href="/user/logout">로그아웃</a></li>
                    <li class="nav-item"><a class="nav-link" href="#!" onclick="myInfo()">나의정보</a></li>
                    <li class="nav-item"><a class="nav-link" href="#!" onclick="myInfo_feign()">나의정보2</a></li>
                </ul>
            </div>
        </div>
    </nav>
    <!-- Content section-->
    <section class="py-5">
        <div class="container my-5">
            <div class="row justify-content-center">
                <div class="col-lg-12">
                    <div id="list">
<!--                     	<div class="box container"> -->
<!-- 	                       	<div> -->
<!-- 	                     	   <h1>이름</h1> -->
<!-- 	                     	   <p>번호 [은행이름]</p> -->
<!-- 	                      	</div> -->
<!-- 	                      	<div class="sub_menu"> -->
<!-- 	                      		<button  onclick="balance(fintech_user_num,this)" class="balance">잔액조회</button> -->
<!-- 	                      	</div> -->
<!-- 	                      	<div class="balance_amt"></div> -->
<!-- 	                    </div>	 -->
                    </div>
                    <div id="feignList">
                    	<%
                    		UserMeDto dto=(UserMeDto)request.getAttribute("userMeDto");
                    		if(dto!=null){
	                    		List<UserMeAccountDto>list=dto.getRes_list();
	                    		
	                    		for(UserMeAccountDto udto:list){
	                    			%>
	                    			<p><%=udto.getAccount_alias() %><br>
	                    			   <%=udto.getFintech_use_num() %>
	                    			   [<%=udto.getBank_name() %>]
	                    			</p>
	                    			<%
	                    		}//for
                    		}//if
                    	%>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- Footer-->
    <footer class="py-5 bg-dark">
        <div class="container"><p class="m-0 text-center text-white">Copyright &copy; Your Website 2022</p></div>
    </footer>
    <!-- Bootstrap core JS-->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <!-- Core theme JS-->
<!--     <script src="resources/js/scripts.js"></script> -->
</body>
</html>
