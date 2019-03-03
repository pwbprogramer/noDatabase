package com.berheley.ichart.service.impl;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.berheley.ichart.dao.echartsRepository;
import com.berheley.ichart.domain.TEcharts;
import com.berheley.ichart.domain.SysUser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("deprecation")
@Service
public class EchartsService {

	
	@SuppressWarnings("deprecation")
	@Autowired
	private echartsRepository hibernateTemplate;
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	/**
	 * 下载模板
	 * @param options 
	 * @param chartType 
	 * @param chartType
	 * @return
	 * @throws IOException
	 */
	public HSSFWorkbook downLoadTemplet(Map<String,Object> optionData, JSONObject options, String chartType) throws IOException {
		//创建下载模板
		HSSFWorkbook workBook = this.createWorkbook(optionData.get("name").toString(),options.get("columns"),chartType);
		return workBook;
	}
	
	/**
	 * 根据选择图形类型 返回图形信息
	 * @param chartType
	 * @return
	 * @throws IOException
	 */
	public Map<String,Object> getPropertiesValues(String chartType) throws IOException{
		Map<String,Object> values = new HashMap<String,Object>();
		Properties properties = new Properties();
		properties.load(EchartsService.class.getClassLoader().getResourceAsStream("echarts.properties"));
		values.put("param", properties.getProperty("echarts_"+chartType+"_param"));
		values.put("type", properties.getProperty("echarts_"+chartType+"_type"));
		values.put("name", properties.getProperty("echarts_"+chartType+"_name"));
		return values;
	}
	
	/**
	 * 创建数据导入模板
	 * @param chartType 
	 * @param columns 
	 */
	@SuppressWarnings("deprecation")
	private HSSFWorkbook createWorkbook(String names, Object columnsValue, String chartType){
		String[] title = names.split(",");
		JSONArray columns=(JSONArray)columnsValue;
		//创建Excel
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("数据导入");
		HSSFRow dataRowOne = sheet.createRow(0);
		//自定义样式
		HSSFFont boldFont = workbook.createFont();
		boldFont.setFontHeight((short) 220);
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFont(boldFont);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//拼接excel表头
		for (int i = 0; i < title.length; i++) {
			HSSFCell cell = dataRowOne.createCell(i);
			cell.setCellStyle(style);
			if(i==0){
				cell.setCellValue(title[i]);
			}else{
				switch(chartType){
				case "doublebarandline":
				case "barandline1":
				case "barandline2":
				case "barandline3":
				case "barandline4":
				case "barandline5":
				case "barandline6":
				case "barandline7":
				case "radar2":
				case "funnel":
				case "funnel1":
				case "scatter":
//					cell.setCellValue(columns.get(i-1)+title[i]);
					cell.setCellValue(columns.get(i-1)+"数据");
					break;
				case "pie":
				case "pie1":
				case "radar":
					cell.setCellValue(title[i]);
					break;
				}
			}
		}
		//自适应列宽
		int num1 = sheet.getRow(0).getPhysicalNumberOfCells();
		for(int i=0;i<num1;i++){
			sheet.setColumnWidth(i, sheet.getRow(0).getCell(i).getStringCellValue().getBytes().length*2*256);
		}
		return workbook;
	}
    
	/**
	 * 处理上传数据
	 * @param file
	 * @param optionData
	 * @param chartType 
	 * @param options 
	 * @throws IOException
	 */
	@SuppressWarnings({ "deprecation", "resource" })
	public JSONObject dealUploadData(MultipartFile file, Map<String, Object> optionData, String chartType, JSONObject options) throws IOException {
		//数据结果集
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		//获取Excel数据
		HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
		HSSFSheet sheet = workbook.getSheetAt(0);
		for(int i=1;i<=sheet.getLastRowNum();i++){
			HSSFRow row = sheet.getRow(i);
			//每行值的集合
			Map<String,Object> map = new HashMap<String,Object>();
			for(int j=0;j<row.getLastCellNum();j++){
				HSSFCell cell = row.getCell(j);
				switch(cell.getCellType()){
				  case HSSFCell.CELL_TYPE_STRING :
					 map.put("value"+j+"", cell.getStringCellValue());
					 break;
				  case HSSFCell.CELL_TYPE_NUMERIC:
					 map.put("value"+j+"", new DecimalFormat("0").format(cell.getNumericCellValue()));
					 break;
				  default:
					 map.put("value"+j+"", cell.getStringCellValue()); 
				}
			}
			list.add(map);
		};
		/**
		 * 最终结果集
		 */
		JSONObject dataResult = this.formatData(list,optionData,chartType,options);
		return dataResult;
	}
	
	/**
	 * 处理为前台数据格式
	 * @param optionData 
	 * @param list 
	 * @param chartType 
	 * @param options 
	 * @return
	 */
	public JSONObject formatData(List<Map<String, Object>> list, Map<String, Object> optionData, String chartType, JSONObject options){
		JSONObject dataResult = new JSONObject();
		JSONArray array = new JSONArray();
		switch(chartType){
		case "doublebarandline":
		case "barandline1":
		case "barandline2":
		case "barandline3":
		case "barandline4":
		case "barandline5":
		case "barandline6":
		case "barandline7":
		case "radar2":
		case "funnel":
		case "funnel1":
		case "scatter":
			array = this.formatBarLineData(list, optionData, options);
			break;
		case "pie":
		case "pie1":
			options.put("type", optionData.get("type"));
			array = this.formatPieData(list, optionData, options);
			break;
		case "radar":
			array = this.formatPieData(list, optionData, options);
			break;
		}
		
		dataResult.put("data", array);
		dataResult.put("options", options);
		return dataResult;
	}

	/**
	 * {group:'类型1',name:'1月',value:10,type:'bar'},
	 * {group:'类型2',name:'1月',value:15,type:'bar'},
	 * {group:'类型3',name:'1月',value:11,type:'line'},
	 * {group:'类型4',name:'1月',value:15,type:'line'},
	 * @param list
	 * @param optionData
	 * @param options 
	 * @return
	 */
	private JSONArray formatBarLineData(List<Map<String, Object>> list, Map<String, Object> optionData, JSONObject columnsValue) {
		String[] type = optionData.get("type").toString().split(",");
		JSONArray array = new JSONArray();
		for(int i=0;i<list.size();i++){
			Map<String,Object> map = list.get(i);
			for(int j=0;j<type.length;j++){
				JSONObject json = new JSONObject();
				JSONArray columns=(JSONArray)columnsValue.get("columns");
				json.put("group", columns.get(j));
				json.put("name", map.get("value0"));
				json.put("value", map.get("value"+(j+1)+""));
				json.put("type", type[j]);
				array.add(json);
			}
		}
		
		return array;
	}

	/**
	 * [{name: '1月', value: 10 }, 
        {name: '2月', value: 25 }, 
        {name: '3月', value: 10 }]; 
	 * @param list
	 * @param optionData
	 * @param options 
	 * @return
	 */
	private JSONArray formatPieData(List<Map<String, Object>> list, Map<String, Object> optionData, JSONObject options) {
		JSONArray array = new JSONArray();
		for(int i=0;i<list.size();i++){
			Map<String, Object> map = list.get(i);
			JSONObject json = new JSONObject();
			json.put("value", map.get("value0"));
			json.put("name", map.get("value1"));
			array.add(json);
		}
		return array;
	}
    
	/**
	 * 获取图形
	 * @param id
	 * @return
	 */
	public JSONObject getEchartsMesg(String id) {
		TEcharts tEcharts = hibernateTemplate.findOne(id);
		JSONObject json=JSONObject.fromObject(tEcharts);
		return json;
	}
    
	/**
	 * 获取图形所需配置信息
	 * @return
	 * @throws Exception
	 */
	public JSONArray getEchartType() throws Exception {
		Properties properties = new Properties();
		properties.load(EchartsService.class.getClassLoader().getResourceAsStream("echarts.properties"));
		String echartTypeMesg=properties.getProperty("echarts_type");
		String echartNameMesg=properties.getProperty("echarts_type_name");
		String[] type=echartTypeMesg.split(",");
		String[] typename=echartNameMesg.split(",");
		JSONArray result=new JSONArray();
		for (int i=0;i<type.length;i++) {
			String typeMesg=properties.getProperty("echarts_"+type[i]+"_detail");
			String nameMesg=properties.getProperty("echarts_"+type[i]+"_detail_name");
			JSONObject mesg=new JSONObject();
			mesg.put("name", typename[i]);
			mesg.put("type", type[i]);
			JSONArray detailMesgarr=new JSONArray();
			String[] detailType=typeMesg.split(",");
			String[] detailTypename=nameMesg.split(",");
			for(int j=0;j<detailType.length;j++){
				JSONObject detailMesg=new JSONObject();
				detailMesg.put("name", detailTypename[j]);
				detailMesg.put("type", detailType[j]);
				detailMesgarr.add(detailMesg);
			}
			mesg.put("detail",detailMesgarr);
			result.add(mesg);
		}
		return result;
	}
    
	/**
	 * 将图形信息存入数据库
	 * @param typeOptionData
	 * @param chartType
	 */
	public String saveEchartsMesg(JSONObject typeOptionData, String chartType) throws Exception{
		String id = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		TEcharts tEcharts = new TEcharts();
		tEcharts.setId(id);
		tEcharts.setType(chartType);
		tEcharts.setOptions(typeOptionData.get("options").toString());
		tEcharts.setDatas(typeOptionData.get("data").toString());
		SysUser user = (SysUser) UserServiceImpl.currentSession.getAttribute("loginUser");
		tEcharts.setCreate_user_(user.getId());
		hibernateTemplate.save(tEcharts);
		return id;
	}

	public JSONObject getdataStart(String chartsType, String[] inputs) throws IOException {
		JSONObject json=new JSONObject();
		Properties properties = new Properties();
		properties.load(EchartsService.class.getClassLoader().getResourceAsStream("echarts.properties"));
		String typeMesg=properties.getProperty("echarts_"+chartsType+"_type");
		String[] types=typeMesg.split(",");
		switch(chartsType){
			case "doublebarandline":
			case "barandline1":
			case "barandline2":
			case "barandline3":
			case "barandline4":
			case "barandline5":
			case "barandline6":
			case "barandline7":
			case "radar2":
			case "funnel":
			case "funnel1":
			case "scatter":
				JSONArray jsonarr=new JSONArray();
				for(int i=1;i<13;i++){
					int k=0;
					for(int j=0;j<inputs.length;j++){
						if(inputs[j].equals("标题名")){
							json.put("title",inputs[j] );
						}else if(inputs[j].equals("副标题")){
							json.put("subTitle",inputs[j] );
						}else{
							JSONObject barlin=new JSONObject();
							barlin.put("group", inputs[j]);
							barlin.put("name", i+"月");
							barlin.put("value", (int)(Math.random()*99+1));
							barlin.put("type", types[k]);
							jsonarr.add(barlin);
							k++;
						}
					}
				}
				json.put("data",jsonarr );
				break;
			case "pie":
			case "pie1":
			case "radar":
				JSONArray piearr=new JSONArray();
				for(int j=0;j<inputs.length;j++){
					if(inputs[j].equals("标题")){
						json.put("title",inputs[j] );
					}else if(inputs[j].equals("副标题")){
						json.put("subTitle",inputs[j] );
					}
				}
				json.put("type", typeMesg);
				//{name: '1月', value: 10 }
				for(int i=1;i<13;i++){
					JSONObject barlin=new JSONObject();
					barlin.put("name", i+"月");
					barlin.put("value", (int)(Math.random()*99+1));
					piearr.add(barlin);
				}
				json.put("data",piearr);
				break;
			}
		return json;
	}
    
	/**
	 * 处理页面提交数据
	 * @param optionData
	 * @param chartType
	 * @param options
	 * @param tableData
	 * @return
	 */
	public JSONObject dealUploadDataHtml(Map<String, Object> optionData, String chartType, JSONObject options, String[] tableData) {
		//数据结果集
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(int i=0;i<tableData.length;i++){
			//每行值的集合
			Map<String,Object> map = new HashMap<String,Object>();
			String[] value = tableData[i].split(",");
			for(int j=0;j<value.length;j++){
				map.put("value"+j+"", value[j]);
			}
			list.add(map);
		}
		/**
		 * 最终结果集
		 */
		JSONObject dataResult = this.formatData(list,optionData,chartType,options);
		return dataResult;
	}

	public JSONArray getIChartList() {
		SysUser user = (SysUser) UserServiceImpl.currentSession.getAttribute("loginUser");
		List<TEcharts> findListByUserId = hibernateTemplate.findListByUserId(user.getId());
		return JSONArray.fromObject(findListByUserId);
		
	}
}
