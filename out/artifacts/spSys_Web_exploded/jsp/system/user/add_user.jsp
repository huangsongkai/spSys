<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="pageContent">
	<form method="post" action="saveUser.do"class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="57">
			<p>
				<label>登录ID：</label>
					<input name="userlist.state" type="hidden" value="A"/>
					<input name="type" type="hidden" value="${param.type}"/>
					<input name="userlist.userId" id="userlist.userId" type="text" value="${userId}" readonly="readonly"/>
			</p>
			<p>
				<label>昵称：</label>
					<input name="userlist.nickName" class="required" type="text" />
			</p>
			<p>
				<label>用户姓名：</label>
				<input name="userlist.userName" class="required" type="text" size="30" value="" alt="请输入用户名称"/>
			</p>
			<p>
			<label>密码：</label>
				<input id="password" type="password" name="userlist.password" class="required alphanumeric" minlength="4" maxlength="20" alt="字母、数字、下划线 4-20位"/>
			</p>
			<p>
			<label>确认密码：</label>
				<input type="password" name="repassword" class="required" equalto="#password"/>
			</p>
			<p>
				<label>移动电话：</label>
				<input name="userlist.mobileTelephone" class="phone" type="text" size="30" alt="请输入您的移动电话"/>
			</p>
			<p>
				<label>办公室电话：</label>
				<input type="text" name="userlist.officeTel" class="phone" alt="请输入您的办公室电话"/>
			</p>
			<p>
				<label>生日：</label>
				<input name="userlist.birthday" id="birthday" value="<fmt:formatDate value="${userlist.birthday }" pattern="yyyy-MM-dd"/>" type="text" class="date" size="30"/><a class="inputDateButton" href="javascript:;">选择</a>
			</p>
			<p>
				<label>email：</label>
				<input type="text" name="userlist.email" class="email" alt="请输入您的电子邮件"/>
			</p>
			<div class="divider"></div>
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

<!-- <script language="javascript">
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
 -->
