/**
 * 아이디, 닉네임, 이메일, 휴대폰 중복체크
 */
var isUidOk   = false;
var isNickOk  = false;
var isEmailOk = false;
var isHpOk    = false;

$(function(){
				
	//아이디 중복체크
	$('input[name=uid]').focusout(function() {
		var tag = $(this);
		var uid = tag.val();
				
		$.ajax({
			url: './proc/checkUid.jsp?uid='+uid,
			type: 'get',
			dataType: 'json',
			success: function(data){
				if(data.result == 1) {
					$('.resultId').css('color', 'red').text('이미 사용중인 아이디 입니다.');
					isUidOk = false;
					tag.focus();
				} else {
					$('.resultId').css('color', 'green').text('사용 가능한 아이디 입니다.');
					isUidOk = true;
				}	
					
			}
		});
	});
				
	//닉네임 중복체크
	$('input[name=nick]').focusout(function(){
		var tag = $(this);
		var nick = tag.val();
		
		$.ajax({
			url: './proc/checkNick.jsp?nick='+nick,
			type: 'get',
			dataType: 'json',
			success: function(data) {
				if(data.result == 1) {
					$('.resultNick').css('color', 'red').text('이미 사용중인 닉네임 입니다.');
					isNickOk = false;
					tag.focus();
				} else {
					$('.resultNick').css('color', 'green').text('사용 가능한 닉네임 입니다.');
					isNickOk = true;
				}
			}
						
		});
	});
				
	//이메일 중복체크
	$('input[name=email]').focusout(function(){
		var tag = $(this);
		var email = tag.val();
			
		$.ajax({
			url: './proc/checkEmail.jsp?email='+email,
			type: 'get',
			dataType: 'json',
			success: function(data) {
				if(data.result == 1) {
					$('.resultEmail').css('color', 'red').text('이미 사용중인 이메일 입니다.');
					isEmailOk = false;
					tag.focus();
				} else {
					$('.resultEmail').css('color', 'green').text('사용 가능한 이메일 입니다.');
					isEmailOk = true;
				}
			}
					
		});
	});
				
	//휴대폰 중복체크
	$('input[name=hp]').focusout(function(){
		var tag = $(this);
		var hp = tag.val();
			
		$.ajax({
			url: './proc/checkHp.jsp?hp='+hp,
			type: 'get',
			dataType: 'json',
			success: function(data) {
				if(data.result == 1) {
					$('.resultHp').css('color', 'red').text('이미 사용중인 전화번호 입니다.');
					isHpOk = false;
					tag.focus();
				} else {
					$('.resultHp').css('color', 'green').text('사용 가능한 전화번호 입니다.');
					isHpOk = true;
				}
			}
						
		});
	});
});