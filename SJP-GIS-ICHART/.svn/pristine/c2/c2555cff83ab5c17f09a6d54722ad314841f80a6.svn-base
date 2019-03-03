/**
 * 封装echarts 工具 
 */
/**
 * 数组是否存在
 */

Array.prototype.contains = function (obj) {  
    var i = this.length;  
    while (i--) {  
        if (this[i] === obj) {  
            return true;  
        }  
    }  
    return false;  
} ;
/**
 * 数组中最大值 最小值
 * @param array
 * @returns
 */
Array.prototype.max = function(){ 
	return Math.max.apply({},this) ;
} ;
Array.prototype.min = function(){ 
	return Math.min.apply({},this) ;
} ;
/**
 * 判断是否为整数
 * @param obj
 * @returns {Boolean}
 */
function isInteger(obj) {
	 return obj%1 === 0
	}
var MyEcharts = {
		//整理数据没有分组类型的，适合饼图
		EchartsDataFormate : {
			/**
			 * 
			 */
			NoGroupFormate : function (data){
				//category 的数据存储
				var categorys = [];
				//data 的数据存储
				var datas = [];
				//subData 子数据
				var subDatas = [];
				//遍历
				for(var i=0;i<data.length;i++){
					categorys.push(data[i].name || "");
					//定义一个中间变量
					var temp_data = {value:data[i].value || 0 , name : data[i].name || ""};
					datas.push(temp_data);
					if(data[i].sub){
						var subAllData = data[i].sub;
						for(var j = 0;j < subAllData.length;j++){
							var subVal = {value:subAllData[j].value || 0 , name : subAllData[j].name || ""};
							subDatas.push(subVal);
							if(!categorys.contains(subAllData[j].name)){
								categorys.push(subAllData[j].name || "");
							}
						}
					}
				}
				return {categorys:categorys,data:datas,subData:subDatas};
			},
			//整理数据有分组类型的，适合折线图、柱形图（分组，堆积） 
			//数据格式：group：XXX，name：XXX，value：XXX
			/**
			 * @param data : json数据<br>
			 * @param type : 图表类型<br>
			 * var data1 = [ <br>
			 *	  { group:'类型1' , name: '1月', value: 10 }, <br>
			 *    { group:'类型2' , name: '1月', value: 15 }, <br>
		     *    { group:'类型1' , name: '2月', value: 25 }, <br>
		     *    { group:'类型2' , name: '2月', value: 12 }, <br>
		     *    { group:'类型1' , name: '3月', value: 22 }, <br>
		     *    { group:'类型2' , name: '3月', value: 12 }, <br>
		     *    ];
			 * 
			 */
			GroupFormate : function (data,type) {
				//用于存储类型名称
				var groups = new Array();
				//用于存储data.name数据
				var names = new Array();
				//存储返回series数据 （一个或者多个）
				var series = new Array();
				
				for(var i=0; i<data.length; i++){
					//判断data[i].group是否存在数租groups中
					if (!groups.contains(data[i].group)) {
						//不存在则跳进 存放
						groups.push(data[i].group);
					}
					
					//判断name数据是否存在 数组names中
					if (!names.contains(data[i].name)) {
						//不存在则跳进 存放
		                 names.push(data[i].name);
					}
				}
				
				//遍历分类
				for (var i=0; i<groups.length; i++){
					//定义一个series中间变量
					var temp_series = {};
					//定义data.value数据存储
					var temp_data = new  Array();
					//遍历所有数据
					for(var j=0; j<data.length; j++){
						//遍历data.name数据
						for(var k=0; k<names.length; k++){
							//判断所有分类中的所有数据含name数据分开
							if(groups[i] == data[j].group && names[k] == data[j].name){
								temp_data.push(data[j].value);
							}
						}
					}
					temp_series = {name:groups[i],type:type,data:temp_data};
					series.push(temp_series);
				
				}
				return {groups : groups ,category : names , series : series};
			},
			//整理数据有分组类型的，适合双柱双线，单柱单线（分组，堆积） 
			//数据格式：group：XXX，name：XXX，value：XXX，type：XXX
			/**
			 * @param data : json数据<br>
			 * @param type : 图表类型<br>
			 * var data1 = [ <br>
			 *	  { group:'类型1' , name: '1月', value: 10 ,type: 'bar' }, <br>
			 *    { group:'类型2' , name: '1月', value: 15 ,type: 'bar' }, <br>
			 *	  { group:'类型3' , name: '1月', value: 11 ,type: 'line' }, <br>
			 *    { group:'类型4' , name: '1月', value: 15 ,type: 'line' }, <br>
		     *    { group:'类型1' , name: '2月', value: 25 ,type: 'bar' }, <br>
		     *    { group:'类型2' , name: '2月', value: 12 ,type: 'bar' }, <br>
		     *    { group:'类型3' , name: '2月', value: 33 ,type: 'line' }, <br>
		     *    { group:'类型4' , name: '2月', value: 42 ,type: 'line' }, <br>
		     *    { group:'类型1' , name: '3月', value: 22 ,type: 'bar' }, <br>
		     *    { group:'类型2' , name: '3月', value: 12 ,type: 'bar' }, <br>
		     *    { group:'类型3' , name: '3月', value: 22 ,type: 'line' }, <br>
		     *    { group:'类型4' , name: '3月', value: 10 ,type: 'line' }, <br>
		     *    ];
			 * 
			 */
			GroupFormateMore : function (data) {
				//用于存储类型名称
				var groups = new Array();
				//用于存储data.name数据
				var names = new Array();
				//存储返回series数据 （一个或者多个）
				var series = new Array();
				//存储图形显示类型
				var types = new Array();
				
				for(var i=0; i<data.length; i++){
					//判断data[i].group是否存在数租groups中
					if (!groups.contains(data[i].group)) {
						//不存在则跳进 存放
						groups.push(data[i].group);
					}
					
					//判断name数据是否存在 数组names中
					if (!names.contains(data[i].name)) {
						//不存在则跳进 存放
		                 names.push(data[i].name);
					}
					types.push(data[i].type);
				}
				
				//遍历分类
				for (var i=0; i<groups.length; i++){
					//定义一个series中间变量
					var temp_series = {};
					//定义data.value数据存储
					var temp_data = new  Array();
					//遍历所有数据
					for(var j=0; j<data.length; j++){
						//遍历data.name数据
						for(var k=0; k<names.length; k++){
							//判断所有分类中的所有数据含name数据分开
							if(groups[i] == data[j].group && names[k] == data[j].name){
								temp_data.push(data[j].value);
							}
						}
					}
					temp_series = {name:groups[i],type:types[i],data:temp_data};
					series.push(temp_series);
				
				}
				return {groups : groups ,category : names , series : series};
			},
			/**
			 * 雷达图数据格式化
			 */
			RadarFormate : function(data,type){
				//用于存储类型名称
				var groups = new Array();
				//用于存储data.name数据
				var names = new Array();
				//存储最大值数组
				var indicators = new Array();
				//定义data.value数据存储
				var temp_data = new  Array();
				for(var i=0; i<data.length; i++){
					//判断data[i].group是否存在数租groups中
					if (!groups.contains(data[i].group)) {
						//不存在则跳进 存放
						groups.push(data[i].group);
					}
					
					//判断name数据是否存在 数组names中
					if (!names.contains(data[i].name)) {
						//不存在则跳进 存放
		                 names.push(data[i].name);
					}
				}
				
				for(var i=0; i<names.length; i++){
					//中
					var temp_maxValue = new Array();
					for(var j=0;j<data.length;j++){
						if(names[i] == data[j].name){
							temp_maxValue.push(data[j].value);
						}
					}
					indicators.push({name:names[i],max:Number(temp_maxValue.max() * 2 / 1.5).toFixed(2)})
				}
				//遍历分类
				for (var i=0; i<groups.length; i++){
					//定义一个series中间变量
					var temp_series = {};
					//定义datavalue数组
					var dataValues = new Array();
					//遍历所有数据
					for(var j=0; j<data.length; j++){
						if(groups[i] == data[j].group){
							dataValues.push(data[j].value);
						}
					}
					temp_data.push({value:dataValues,name:groups[i]});
				}
				series = {type:type,data:temp_data};
				return { indicators : indicators ,groups : groups ,category : names , series : series};
			},
			/**
			 * 漏斗图数据格式化
			 */
			FunnelFormate : function(data,type){
				//用于存储类型名称
				var groups = new Array();
				//用于存储data.name数据
				var names = new Array();
				//定义一个存放series的数组
				var series = new Array();
				for(var i=0; i<data.length; i++){
					//判断data[i].group是否存在数租groups中
					if (!groups.contains(data[i].group)) {
						//不存在则跳进 存放
						groups.push(data[i].group);
					}
					
					//判断name数据是否存在 数组names中
					if (!names.contains(data[i].name)) {
						//不存在则跳进 存放
		                 names.push(data[i].name);
					}
				}
				var width = parseInt(100/groups.length);
				//遍历分类
				for (var i=0; i<groups.length; i++){
					//定义data.value数据存储
					var temp_data = new  Array();
					var k = 0;
					//遍历所有数据
					for(var j=0; j<data.length; j++){
						//判断所有分类中的所有数据含name数据分开
						if(groups[i] == data[j].group){
							k++;
//							temp_data.push({value:k,name:data[j].name+":"+data[j].value});
							temp_data.push({value:data[j].value,name:data[j].name+":"+data[j].value});
						}
					}
					var left = width*i;
					series.push({
						name:groups[i],
						type:type,
						sort:'ascending',
						grap:2,
						left: left+"%",
						width: width-5+"%",
						label: {
			                normal: {
			                    show: true,
			                    position: 'inside'
			                },
			                emphasis: {
			                    textStyle: {
			                        fontSize: 20
			                    }
			                }
			            },
			            data:temp_data
					});
				}
				return { groups : groups ,category : names , series : series};
			},
			/**
			 * 仪表盘图数据格式化
			 */
			GaugeFormate : function (data,type){
					var temp_datas = [{value:data.value,name:data.name}];
					var names = data.name;
					//判断最大值和最小值几位数
					maxNum = Number(parseInt(data.value)).toString().length;
					minNum = Number(parseInt(data.value)).toString().length;
					if(minNum <= 2){
						min = 0;
					}else{
						//最小值
						min = Math.pow(10,(maxNum-1));
					}
					//最大值
					max = Math.pow(10,maxNum);
					var series = new Array();
					series.push({
						name:data.group,
						type:type,
			            min:min,
			            max:max,
			            radius: '70%',
			            startAngle:180,
			            endAngle:-0,
			            axisLine: {            // 坐标轴线
			                lineStyle: {       // 属性lineStyle控制线条样式
			                    color: [[0.09, 'lime'],[0.82, '#1e90ff'],[1, '#ff4500']],
			                    width: 3,
			                    shadowColor : '#fff', //默认透明
			                    shadowBlur: 10
			                }
			            },
			            axisLabel: {            // 坐标轴小标记
			                textStyle: {       // 属性lineStyle控制线条样式
			                    fontWeight: 'bolder',
			                    color: '#444',
			                    shadowColor : '#fff', //默认透明
			                    shadowBlur: 10
			                }
			            },
			            axisTick: {            // 坐标轴小标记
			                length :15,        // 属性length控制线长
			                lineStyle: {       // 属性lineStyle控制线条样式
			                    color: 'auto',
			                    shadowColor : '#fff', //默认透明
			                    shadowBlur: 10
			                }
			            },
			            splitLine: {           // 分隔线
			                length :25,         // 属性length控制线长
			                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
			                    width:3,
			                    color: 'auto',
			                    shadowColor : '#fff', //默认透明
			                    shadowBlur: 10
			                }
			            },
			            pointer: {           // 分隔线
			                shadowColor : '#fff', //默认透明
			                shadowBlur: 5
			            },
			            title : {
			                offsetCenter :['-10%','30%'],
			                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
			                    fontWeight: 'bolder',
			                    fontSize:14,
			                    fontStyle: 'italic',
			                    color: '#',
			                    shadowColor : '#fff', //默认透明
			                    shadowBlur: 10
			                }
			            },
			            detail : {
			                backgroundColor: 'rgba(30,144,255,0.8)',
			                borderWidth: 1,
			                borderColor: '#fff',
			                shadowColor : '#fff', //默认透明
			                shadowBlur: 5,
			                fontSize:14,
			                offsetCenter: ['20%', '30%'],       // x, y，单位px
			                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
			                    fontWeight: 'bolder',
			                    color: '#fff'
			                }
			            },
			            data:temp_datas
					});
				return {category : names , series : series};
			},
			/**
			 * 散点图图数据格式化
			 */
			ScatterFormate : function (data,type){
				[{
			        symbolSize: 20,
			        data: [
			            [10.0, 8.04],
			            [8.0, 6.95],
			            [13.0, 7.58],
			            [9.0, 8.81],
			            [11.0, 8.33],
			            [14.0, 9.96],
			            [6.0, 7.24],
			            [4.0, 4.26],
			            [12.0, 10.84],
			            [7.0, 4.82],
			            [5.0, 5.68]
			        ],
			        type: 'scatter'
			    }]
				var temp_datas = [{value:data.value,name:data.name}];
				var names = data.name;
				//判断最大值和最小值几位数
				maxNum = Number(parseInt(data.value)).toString().length;
				minNum = Number(parseInt(data.value)).toString().length;
				if(minNum <= 2){
					min = 0;
				}else{
					//最小值
					min = Math.pow(10,(maxNum-1));
				}
				//最大值
				max = Math.pow(10,maxNum);
				var series = new Array();
				series.push({
					name: '1990',
			        data: temp_datas,
			        type: type,
			        symbolSize: function (data) {
			            return Math.sqrt(data[2]) / 5e2;
			        },
			        label: {
			            emphasis: {
			                show: true,
			                formatter: function (param) {
			                    return param.data[3];
			                },
			                position: 'top'
			            }
			        },
			        itemStyle: {
			            normal: {
			                shadowBlur: 10,
			                shadowColor: 'rgba(120, 36, 50, 0.5)',
			                shadowOffsetY: 5,
			                color: new echarts.graphic.RadialGradient(0.4, 0.3, 1, [{
			                    offset: 0,
			                    color: 'rgb(251, 118, 123)'
			                }, {
			                    offset: 1,
			                    color: 'rgb(204, 46, 72)'
			                }])
			            }
			        }
				});
				return {category : names , series : series};
			}
			
		},
	
		//生成图形option
		EchartsOption : {
			/**
			 * 饼图
			 * @param title ： 标题<br>
			 * @param subtext ：副标题<br>
			 * @param data : json 数据
			 * @param type : 1:实心饼图，2:空心饼图，3:嵌套饼图
			 */
			pie : function (title,subtext,data,type){
				//数据格式
				var datas = MyEcharts.EchartsDataFormate.NoGroupFormate(data);
				var series_txt = "";
				if(type == 2){
					series_txt = [
				        {
				            name:'访问来源',
				            type:'pie',
				            radius: ['50%', '70%'],
				            avoidLabelOverlap: false,
				            label: {
				                normal: {
				                    show: false,
				                    position: 'center'
				                },
				                emphasis: {
				                    show: true,
				                    textStyle: {
				                        fontSize: '30',
				                        fontWeight: 'bold'
				                    }
				                }
				            },
				            labelLine: {
				                normal: {
				                    show: false
				                }
				            },
				            data:datas.data
				        }
				    ]
				}else if(type == 3){
					series_txt = [
				        {
				            name:'访问来源',
				            type:'pie',
				            selectedMode: 'single',
				            radius: [0, '30%'],

				            label: {
				                normal: {
				                    position: 'inner'
				                }
				            },
				            labelLine: {
				                normal: {
				                    show: false
				                }
				            },
				            data:datas.data
				        },
				        {
				            name:'访问来源',
				            type:'pie',
				            radius: ['40%', '55%'],
				            label: {
				                normal: {
				                    formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}  ',
				                    backgroundColor: '#eee',
				                    borderColor: '#aaa',
				                    borderWidth: 1,
				                    borderRadius: 4,
				                    // shadowBlur:3,
				                    // shadowOffsetX: 2,
				                    // shadowOffsetY: 2,
				                    // shadowColor: '#999',
				                    // padding: [0, 7],
				                    rich: {
				                        a: {
				                            color: '#999',
				                            lineHeight: 22,
				                            align: 'center'
				                        },
				                        // abg: {
				                        //     backgroundColor: '#333',
				                        //     width: '100%',
				                        //     align: 'right',
				                        //     height: 22,
				                        //     borderRadius: [4, 4, 0, 0]
				                        // },
				                        hr: {
				                            borderColor: '#aaa',
				                            width: '100%',
				                            borderWidth: 0.5,
				                            height: 0
				                        },
				                        b: {
				                            fontSize: 16,
				                            lineHeight: 33
				                        },
				                        per: {
				                            color: '#eee',
				                            backgroundColor: '#334455',
				                            padding: [2, 4],
				                            borderRadius: 2
				                        }
				                    }
				                }
				            },
				            data:datas.subData
				        }
				    ]
				}else{
					series_txt = [
				        {
				        	name : title || "",
				            type : 'pie',	//类型
				            radius : '48%', //圆的大小
				            center : ['50%', '50%'],//位置居中
				            data : datas.data,
				            itemStyle: {
				                emphasis: {
				                    shadowBlur: 10,
				                    shadowOffsetX: 0,
				                    shadowColor: 'rgba(0, 0, 0, 0.5)'
				                }
				            },
				            //引导线
				            labelLine :{
				                normal :{
				                    show: true,
				                    length:2,
				                }
				            }
				        }
				    ]
				}
				option = {
					//标题
					title :{
						text : title || "",	//标题
						subtext : subtext || "", //副标题
						x : 'center',	//位置默认居中
					},
					//提示
					tooltip: {
						show: true,
						trigger: 'item',
						formatter: "{a} <br/>{b} : {c} ({d}%)"
			        },
			        //下载，刷新等
			        toolbox: {
			            feature: {
			                dataView: {show: true, readOnly: false},
			                restore: {show: true},
			                saveAsImage: {show: true}
			            }
			        },
			        //组建
			        legend : {
				    	orient: 'horizontal', //垂直：vertical； 水平 horizontal
			           // top: 'center',	//位置默认左
			            bottom:'5%',
				        data:datas.categorys
				    },
				    series: series_txt,
				};
				return option;
			},
			/**
			 * 柱形图
			 * @param title ： 标题<br>
			 * @param subtext ：副标题<br>
			 * @param data : json 数据
			 */
			bar : function (title,subtext,data){
				var datas = MyEcharts.EchartsDataFormate.GroupFormate(data, 'bar');
				var option = {
					//标题
					title :{
						text : title || "",	//标题
						subtext : subtext || "", //副标题
						x : 'center',	//位置默认居中
					},
					label:{
				        show:true
				    },
					grid: {
				        right: '20%'
				    },
					//提示
					tooltip: {
						show: true,
						trigger: 'item',
						formatter: "{a} <br/>{b} : {c}"
			        },
			        //下载，刷新等
			        toolbox: {
			            feature: {
			                dataView: {show: true, readOnly: false},
			                restore: {show: true},
			                saveAsImage: {show: true}
			            }
			        },
			        //组建
				    legend: {
//				    	orient: 'vertical', //垂直：vertical； 水平 horizontal
//			            left: 'left',	//位置默认左
				    	x : 'center',
				        y : 'bottom',
				        data : datas.groups
				    },
			        //水平坐标
			        xAxis : [
		                 {
		                     type : 'category',
		                     data : datas.category
		                 }
		             ],
		             //垂直坐标
		             yAxis : [
	                      {
	                          type : 'value'
	                      }
	                  ],
	                  //series数据
	                  series: datas.series
				};
				return option;
			},
			
			/**
			 * 折线图
			 * @param title ： 标题<br>
			 * @param subtext ：副标题<br>
			 * @param data : json 数据
			 */
			Line : function (title,subtext,data){
				var datas = MyEcharts.EchartsDataFormate.GroupFormate(data, 'line');
				var option = {
					//标题
					title :{
						text : title || "",	//标题
						subtext : subtext || "", //副标题
						x : 'center',	//位置默认居中
					},
					label:{
				        show:true
				    },
					//提示
					tooltip: {
						show: true,
						trigger: 'axis',
			        },
			        //下载，刷新等
			        toolbox: {
			            feature: {
			                dataView: {show: true, readOnly: false},
			                restore: {show: true},
			                saveAsImage: {show: true}
			            }
			        },
			        //组建
				    legend: {
//				    	orient: 'vertical', //垂直：vertical； 水平 horizontal
//			            left: 'right',	//位置默认右
				    	x : 'center',
				        y : 'bottom',
				        data : datas.groups
				    },
				    grid:{
						  left:'10%',
						  top:'25%',
						  right:'10%',
						  bottom:'25%',
					 },
			        //水平坐标
			        xAxis : [
		                 {
		                     type : 'category',
		                     data : datas.category,
		                     boundaryGap: false,
		                     splitLine:{  
		                         show:true,
		                     },
		                 }
		             ],
		             //垂直坐标
		             yAxis : [
	                      {
	                          type : 'value'
	                      }
	                  ],
	                  //series数据
	                  series: datas.series
				};
				return option;
			},
			/**
			 * 雷达图
			 * @param title ： 标题<br>
			 * @param subtext ：副标题<br>
			 * @param data : json 数据
			 */
			Radar : function (title,subtext,data){
				var datas = MyEcharts.EchartsDataFormate.RadarFormate(data, 'radar');
				var option = {
					//标题
					title :{
						text : title || "标题名",	//标题
						subtext : subtext || "", //副标题
						x : 'left',	//位置默认居中
					},
					//提示
					tooltip: {
						show: true,
			        },
			        //下载，刷新等
			        toolbox: {
			            feature: {
			                dataView: {show: true, readOnly: false},
			                restore: {show: true},
			                saveAsImage: {show: true}
			            }
			        },
			        //组建
				    legend: {
//				    	orient: 'vertical', //垂直：vertical； 水平 horizontal
//			            left: 'left',	//位置默认左
				    	x : 'center',
				        y : 'bottom',
				        data : datas.groups
				    },
				    radar: {
				        name: {
				            textStyle: {
				                color: '#fff',
				                backgroundColor: '#999',
				                borderRadius: 3,
				                padding: [3, 5]
				           }
				        },
				        indicator: datas.indicators
				    },
				    series: datas.series
				};
				return option;
			},
			/**
			 * 漏斗图
			 * @param title ： 标题<br>
			 * @param subtext ：副标题<br>
			 * @param data : json 数据
			 */
			Funnel : function (title,subtext,data){
				var datas = MyEcharts.EchartsDataFormate.FunnelFormate(data, 'funnel');
				var option = {
					//标题
					title :{
						text : title || "",	//标题
						subtext : subtext || "", //副标题
						x : 'center',	//位置默认居中
					},
					//提示
					tooltip: {
						show: true,
						trigger: 'item',
				        formatter: "{a} <br/>{b} ({c}%)"
			        },
			        //下载，刷新等
			        toolbox: {
			            feature: {
			                dataView: {show: true, readOnly: false},
			                restore: {show: true},
			                saveAsImage: {show: true}
			            }
			        },
			        //组建
				    legend: {
//				    	orient: 'vertical', //垂直：vertical； 水平 horizontal
//			            left: 'left',	//位置默认左
				    	x : 'center',
				        y : 'bottom',
				        data : datas.groups
				    },
				    series: datas.series
				};
				return option;
			},
			/**
			 * 仪表图
			 * @param title ： 标题<br>
			 * @param subtext ：副标题<br>
			 * @param data : json 数据
			 */
			Gauge : function (title,subtext,data){
				var datas = MyEcharts.EchartsDataFormate.GaugeFormate(data, 'gauge');
				var option =  {
					//标题
					title :{
						text : title || "",	//标题
						subtext : subtext || "", //副标题
						x : 'center',	//位置默认居中
					},
					//提示
					tooltip: {
						show: true,
						formatter: "{a} <br/>{b}:{c}"
			        },
			        //下载，刷新等
			        toolbox: {
			            feature: {
			                dataView: {show: true, readOnly: false},
			                restore: {show: true},
			                saveAsImage: {show: true}
			            }
			        },
			        series :datas.series
				};
				return option;
			},
			/**
			 * 散点图
			 * @param title ： 标题<br>
			 * @param subtext ：副标题<br>
			 * @param data : json 数据
			 */
			Scatter : function (title,subtext,data){
				var datas = MyEcharts.EchartsDataFormate.ScatterFormate(data, 'scatter');
				option = {
				    xAxis: {},
				    yAxis: {},
				    series: [{
				        symbolSize: 20,
				        data: [
				            [10.0, 8.04],
				            [8.0, 6.95],
				            [13.0, 7.58],
				            [9.0, 8.81],
				            [11.0, 8.33],
				            [14.0, 9.96],
				            [6.0, 7.24],
				            [4.0, 4.26],
				            [12.0, 10.84],
				            [7.0, 4.82],
				            [5.0, 5.68]
				        ],
				        type: 'scatter'
				    }]
				};
				return option;
			},
			/**
			 * 双柱双线
			 * @param title ： 标题<br>
			 * @param subtext ：副标题<br>
			 * @param data : json 数据
			 */
			barAndLine : function (title,subtext,data){
				var datas = MyEcharts.EchartsDataFormate.GroupFormateMore(data);
				var option = {
					//标题
					title :{
						text : title || "",	//标题
						subtext : subtext || "", //副标题
						x : 'center',	//位置默认居中
					},
					label:{
				        show:true
				    },
					//提示
					tooltip: {
						show: true,
						trigger: 'item',
						formatter: "{a} <br/>{b} : {c}"
			        },
			        //下载，刷新等
			        toolbox: {
			            feature: {
			                dataView: {show: true, readOnly: false},
			                restore: {show: true},
			                saveAsImage: {show: true}
			            }
			        },
			        //组建
				    legend: {
//				    	orient: 'vertical', //垂直：vertical； 水平 horizontal
//			            left: 'left',	//位置默认左
				    	x : 'center',
				        y : 'bottom',
				        data : datas.groups
				    },
			        //水平坐标
			        xAxis : [
		                 {
		                     type : 'category',
		                     data : datas.category
		                 }
		             ],
		             //垂直坐标
		             yAxis : [
	                      {
	                          type : 'value'
	                      }
	                  ],
	                  //series数据
	                  series: datas.series
				};
				return option;
			},
		},
		
		/**
		 * 
		 * @param option : option
		 * @param echartId : 图表的id 需要加引号
		 */
		initChart : function (option,echartId,theme){
			var container = eval("document.getElementById('" + echartId + "')");
			var myChart = echarts.init(container,theme);
			myChart.setOption(option,true);	// 为echarts对象加载数据 
			return myChart;
		}
		
	
};
