<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="pageContent">
	<form method="post" action="saveUpdateUser.do" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<input type="hidden" name/>
		<div class="pageFormContent" layoutH="57">
			<p>
				<label>登录ID：</label>
					<input name="userlist.state" type="hidden" value="A"/>
					<input name="type" type="hidden" value="${param.type}"/>
					<input name="userlist.userId" id="userlist.userId" value="${userlist.userId}" type="text" readonly="readonly"/>
			</p>
			<p>
				<label>昵称：</label>
					<input name="userlist.nickName" class="required" type="text"  value="${userlist.nickName }"/>
			</p>
			<p>
				<label>用户姓名：</label>
				<input name="userlist.userName"  class="required" type="text" size="30" value="${userlist.userName }" alt="请输入客户名称"/>
			</p>

			<p>
				<label>移动电话：</label>
				<input name="userlist.mobileTelephone" class="phone" value="${userlist.mobileTelephone }" type="text" size="30" alt="请输入您的移动电话"/>
			</p>
			<p>
				<label>办公室电话：</label>
				<input type="text" name="userlist.officeTel" value="${userlist.officeTel }" class="phone" alt="请输入您的办公室电话"/>
			</p>
			<p>
				<label>生日：</label>
				<input name="userlist.birthday" id="birthday" value="<fmt:formatDate value="${userlist.birthday }" pattern="yyyy-MM-dd"/>" type="text" class="date" size="30"/><a class="inputDateButton" href="javascript:;">选择</a>
			</p>
			<div class="divider"></div>
			<p>
				<label>email：</label>
				<input type="text" name="userlist.email" value="${userlist.email }" class="email" alt="请输入您的电子邮件"/>
			</p>
		</div>
		
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>

<script language="javascript">
  			$("#idNumber").blur(function(){
             	 var birthdayValue;
             	 var sex;
             	 var val =$("#idNumber").val();
   				 if (15 == val.length) { //15位身份证号码
        			birthdayValue = val.charAt(6) + val.charAt(7);
       				 if (parseInt(birthdayValue) < 10) {
            			birthdayValue = '20' + birthdayValue;
       			 	 }else {
            			birthdayValue = '19' + birthdayValue;
        		 	}
       			 	birthdayValue = birthdayValue + '-' + val.charAt(8) + val.charAt(9) + '-' + val.charAt(10) + val.charAt(11);
        			if (parseInt(val.charAt(14) / 2) * 2 != val.charAt(14)){
           			 	sex = '0';
        			}else{
            		 	sex = '1';
            		 }
    			}
    			if (18 == val.length) { //18位身份证号码
        			birthdayValue = val.charAt(6) + val.charAt(7) + val.charAt(8) + val.charAt(9) + '-' + val.charAt(10) + val.charAt(11)+ '-' + val.charAt(12) + val.charAt(13);
        			if (parseInt(val.charAt(16) / 2) * 2 != val.charAt(16)){
            			sex = '0';
       				}else{
            			sex = '1';
            		}
            		$("#birthday").val(birthdayValue);
            		$("#sex").val(sex);
    		    }
     		})
</script>
