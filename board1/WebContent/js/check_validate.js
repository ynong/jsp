/**
 * 
 */
$(function(){
	$('#regForm').submit(function(){
		var uid = $('input[name=uid]').val();
		var pw1 = $('input[name=pw1]').val();
		var pw2 = $('input[name=pw2]').val();
		var name = $('input[name=name]').val();
				
		if(!isUidOk) {
			alert('아이디가 이미 사용중입니다.');
			return false;
		}			
					
		// 아이디 자리수 체크
		if(uid.length < 4) {
			alert('아이디는 최소 4자 이상이어야 합니다.');
			return false;
		}
		// 비밀번호 일치여부 확인
		if(pw1 != pw2) {
			alert('비밀번호가 서로 일치하지 않습니다.');
			return false;
		}
					
		return true;
	});
});