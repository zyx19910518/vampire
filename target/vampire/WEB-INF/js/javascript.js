$(document).ready(function() {
	//模态框
	$("#myModal").on('show.bs.modal', function(){//显示前回调
		appentToShow("show.bs.modal");
	});
	$("#myModal").on('shown.bs.modal', function(){//显示后回调
		appentToShow("shown.bs.modal");
	});
	$("#myModal").on('hide.bs.modal', function(){//隐藏前回调
		appentToShow("hide.bs.modal");
	});
	$("#myModal").on('hidden.bs.modal', function(){//隐藏后回调
		appentToShow("hidden.bs.modal");
	});
	$("#jsModalButton").click(function(){
		$("#myModal").modal({
			keyboard: true,//默认为true，按esc按键的时候时候会将模态框关闭（没起效）
			backdrop: false,//为false的时候不会出现背景，点击背景模态框不会消失
			show: true,	//为false的时候点击按钮不展现模态框
			remote: '/load.do'	//通过jquery的load函数加载远程页面
		});
	});

	//标签页
	$("#tabs a").click(function(e){
		e.preventDefault();
		$(this).tab('show');
	});

	//提示框
	$("#alertBtn").click(function(){
		$("#waringAlert").alert('close');
	});
});

//添加显示的信息
function appentToShow(content){
	var text = $('#show').html();
	$('#show').html(text + content +'<br>');
}