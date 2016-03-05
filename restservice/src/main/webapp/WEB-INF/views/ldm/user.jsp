<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="edge">
    <meta name="renderer" content="ie-stand">
    <title>聚金资本_聚金金融旗下|中国首家o2o模式p2p网贷投资,p2p理财富平台</title>
    <meta name="Keywords" content="聚金资本,聚金金融,p2p理财,p2p网贷,聚金理财,聚金财富,聚金投资,聚金网贷,O2O网贷">
    <meta name="Description" content="河南聚金金融服务股份有限公司所有聚金资本O2O模式P2P理财网贷平台，是河南聚金金融旗下安全的p2p理财网贷平台，是帮助用户p2p理财以实现聚金财富增值的网贷平台，是安全稳健持续可靠的p2p网贷投资理财平台。
郑州聚金资本以其快速高效透明，持续安全稳健的经营特征赢得了良好的用户口碑。请记住我们:河南聚金金融服务股份有限公司；记住聚金资本O2O模式p2p网贷投资理财平台。聚金资本_帮您实现聚金财富梦想。">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/back-banner.css">
    <script type="text/javascript" src="<%=basePath %>js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/spin.min.js"></script>
    <script type="text/javascript">
    $(document).ready(function(){
    	
    });
    
    var opts = {            
            lines: 13, // 花瓣数目
            length: 20, // 花瓣长度
            width: 10, // 花瓣宽度
            radius: 30, // 花瓣距中心半径
            corners: 1, // 花瓣圆滑度 (0-1)
            rotate: 0, // 花瓣旋转角度
            direction: 1, // 花瓣旋转方向 1: 顺时针, -1: 逆时针
            color: '#cccccc', // 花瓣颜色
            speed: 1, // 花瓣旋转速度
            trail: 60, // 花瓣旋转时的拖影(百分比)
            shadow: false, // 花瓣是否显示阴影
            hwaccel: false, //spinner 是否启用硬件加速及高速旋转            
            className: 'spinner', // spinner css 样式名称
            zIndex: 2e9, // spinner的z轴 (默认是2000000000)
            top: 'auto', // spinner 相对父容器Top定位 单位 px
            left: 'auto'// spinner 相对父容器Left定位 单位 px
        };

        var spinner = new Spinner(opts);
    
    function loadUserData(){
    	var userId = $("#kw").val();
    	$.ajax({
    		type: "get",
    		url: '<%=basePath%>ldm/loadUserData',
        	data: {"userId":userId},
    		dataType:'json',
    		beforeSend:function(XMLHttpRequest){
    			 var target = $("#loading").get(0);
                 spinner.spin(target);    
            },  
            complete:function(XMLHttpRequest,textStatus){
            	spinner.spin();
            },
            error:function(XMLHttpRequest,textStatus,errorThrown){
                alert('error...状态文本值：'+textStatus+" 异常信息："+errorThrown);
                spinner.spin();
            },
    		success: function(data){
    			spinner.spin();
    			$("#allTimes").html(data.allTimes);
    			$("#yesAddTimes").html(data.yesAddTimes);
    			$("#oddTimes").html(data.oddTimes);
    			
    			var brList = data.brList;
    			$("#tendRecord").empty();
    			$("#tendRecord").append(
    	                '<tr bgcolor="#cccccc" id="tendRecord">'+
    	                '<th>标</th>'+
    	                '<th>累计投资金额</th>'+
    	                '<th>投资时间</th>'+
    	                '<th>计算抽奖</th>'+
    	            	'</tr>'
				);
    			$.each(brList, function(name, value) {
    				$("#tendRecord").append(
    						'<tr align=center>'+
    		                '<td>'+value.borrowTitle+'['+value.borrowId+']</td>'+
    		                '<td>'+value.amount+'</td>'+
    		                '<td>'+value.tdate+'</td>'+
    		                '<td>'+(parseFloat(value.amount)>=2000 ? '是':'否')+'</td>'+
    		            	'</tr>'
    				);
    			});
    			
    			var arList = data.arList;
    			$("#drawRecord").empty();
    			$("#drawRecord").append(
       				 	'<tr bgcolor="#cccccc" >'+
                     	'<th>奖品</th>'+
                     	'<th>抽奖时间</th>'+
                     	'<th>是否大奖</th>'+
                 		'</tr>'
				);	
    			$.each(arList, function(name, value) {
    				$("#drawRecord").append(
    			            '<tr align=center>'+
    		                '<td>'+value.awardMsg+'</td>'+
    		                '<td>'+value.winDate+'</td>'+
    		                '<td>'+(value.isBigAward == 1 ? '是':'否')+'</td>'+
    		            	'</tr>'
    				);
    			});
    		}
    	});
    }
    
    
    function resetCache(){
    	var userId = $("#kw").val();
    	$.ajax({
    		type: "get",
    		url: '<%=basePath%>ldm/resetUserCache',
        	data: {"userId":userId},
    		dataType:'json',
    		beforeSend:function(XMLHttpRequest){
    			 var target = $("#loading").get(0);
                 spinner.spin(target);    
            },  
            complete:function(XMLHttpRequest,textStatus){
            	spinner.spin();
            },
            error:function(XMLHttpRequest,textStatus,errorThrown){
                alert('error...状态文本值：'+textStatus+" 异常信息："+errorThrown);
                spinner.spin();
            },
    		success: function(data){
    			if(data == 1){
    				alert("重置成功");
    			}
    		}
    	});
    }
    
    </script>
<body >
<div class="s-skin-container" style="background-color:rgb(64, 64, 64);background-image:url(<%=basePath %>img/back-banner.jpg);">  </div>
<div id="head_wrapper" style="width:740px;padding-top:123px;">
    <p class="s-p-top">
        <img src="<%=basePath %>img/logo.png">
    </p>
    <div class="s_form">
            <form  class="fm">
                <span>
                    <input type="text" class="s_ipt" id="kw" maxlength="100" autocomplete="off" placeholder="用户名" value="firetw">
                </span>
                <span style="position: absolute;top: 1px;">
                    <input type="button" value="聚金搜搜" id="su" class="btn-yi" onclick="loadUserData();" style="cursor: pointer;">
                </span>
            </form>
    </div>
</div>

<div class="yi_list" id="loading" style="width: 100px;"></div>

   <div class="yi_list">
        <table border="7" width="800px" align="center" cellspacing="0" cellpadding="0" style="border:1px solid #d8d8d8;background-color: #FFF;padding: 5px;border-radius: 8px;">
            <tr bgcolor="#cccccc">
                <th>总抽奖次数</th>
                <th>昨日新增次数</th>
                <th>剩余抽奖次数</th>
            </tr>
            <tr align=center>
                <td id="allTimes">0</td>
                <td id="yesAddTimes">0</td>
                <td id="oddTimes">0</td>
            </tr>
        </table>
    </div>


    <div class="yi_list" style="float: left;margin-left: 100px;">
        <table id="tendRecord" border="7" width="800px" align="center" cellspacing="0" cellpadding="0" style="border:1px solid #d8d8d8;background-color: #FFF;padding: 5px;border-radius: 8px;" >
            <tr bgcolor="#cccccc" >
                <th>标</th>
                <th>累计投资金额</th>
                <th>投资时间</th>
                <th>计算抽奖</th>
            </tr>
        </table>
    </div>

    <div class="yi_list" style="float: right;margin-right: 100px;">
        <table id="drawRecord" border="7" width="800px" align="center" cellspacing="0" cellpadding="0" style="border:1px solid #d8d8d8;background-color: #FFF;padding: 5px;border-radius: 8px;">
            <tr bgcolor="#cccccc" >
                <th>奖品</th>
                <th>抽奖时间</th>
                <th>是否大奖</th>
            </tr>
        </table>
    </div>
    <div style="clear: both;"></div>
</body>
</html>
