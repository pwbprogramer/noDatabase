package com.berheley.ichart.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.berheley.ichart.service.impl.EchartsService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Echarts数据类
 * @author pwb
 *
 */
@Controller
@RequestMapping("/echartsUploadData")
public class EchartsController {

	/**
	 * 图形类型
	 */
	private static String chartType;
	
	/**
	 * 图形类型信息
	 */
	private static Map<String,Object> optionData;
	
	/**
	 * 图形自定义信息
	 */
	private static JSONObject options = new JSONObject();
	
	@Autowired
	private EchartsService echartsService;
	
	/**
	 * 下载数据模板
	 * @param req
	 * @param res
	 */
	@RequestMapping("/downLoadTemplet")
	public void downLoadTemplet(HttpServletRequest req,HttpServletResponse res){
		try {
			chartType = req.getParameter("chartType");
			options.put("columns", JSONArray.fromObject(req.getParameter("mesg")));
			options.put("title", req.getParameter("title"));
			options.put("subTitle", req.getParameter("subTitle"));
			optionData = echartsService.getPropertiesValues(chartType);
			HSSFWorkbook workbook = echartsService.downLoadTemplet(optionData,options,chartType);
			//文件名
			String tabelName = "数据上传"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			res.reset();
			res.setCharacterEncoding("UTF-8");
			res.setContentType("application/x-msdownload");
			res.setHeader("Content-disposition", "attachment;filename="
						+ new String(tabelName.getBytes("GBK"), "ISO-8859-1")
						+ ".xls");
			//导出excel
			workbook.write(res.getOutputStream());
			res.getOutputStream().flush();
			res.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 数据上传
	 * @param req
	 * @param res
	 */
	@ResponseBody
	@RequestMapping("/uploadData")
	public JSONObject uploadData(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		try{
			//获取上传文件
			MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) req;
			MultipartFile file = multipart.getFile("importFile");
			JSONObject dataResult = echartsService.dealUploadData(file,optionData,chartType,options);
			//将数据源放入session
			HttpSession session = req.getSession();
			session.setAttribute("typeOptionData", dataResult);
			json.put("result", true);
			json.put("typeOptionData",dataResult);
		}catch(Exception e){
			json.put("result", false);
			e.printStackTrace();
		}
		return json;
	}
	
	
	/**
	 * 获取页面图形类型
	 * @param req
	 * @param res
	 */
	@ResponseBody
	@RequestMapping("/getEchartType")
	public JSONObject getEchartType(HttpServletRequest req,HttpServletResponse res){
		JSONObject json=new JSONObject();
		try {
			JSONArray result=echartsService.getEchartType();
			json.put("success", true);
			json.put("mesg", result);
		} catch (Exception e) {
			json.put("success", false);
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 获取页面input框
	 * @param req
	 * @param res
	 */
	@ResponseBody
	@RequestMapping("/getInputMesg")
	public JSONObject getInputMesg(HttpServletRequest req,HttpServletResponse res){
		JSONObject json=new JSONObject();
		try {
			String chartsType=req.getParameter("chartType");
			Properties properties = new Properties();
			properties.load(EchartsService.class.getClassLoader().getResourceAsStream("echarts.properties"));
			String inputMesg=properties.getProperty("echarts_"+chartsType+"_input");
			String tableHeadMesg=properties.getProperty("echarts_"+chartsType+"_name");
			String[] inputs=inputMesg.split(",");
			JSONObject data=echartsService.getdataStart(chartsType,inputs);
			json.put("success", true);
			json.put("inputMesg", inputs);
			json.put("tableHeadMesg", tableHeadMesg.split(","));
			json.put("data", data);
		} catch (Exception e) {
			json.put("success", false);
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 保存图片信息
	 * @param req
	 * @param res
	 */
	@ResponseBody
	@RequestMapping("/saveEchartsMesg")
	public JSONObject saveEchartsMesg(HttpServletRequest req,HttpServletResponse res){
		JSONObject json=new JSONObject();
		try {
			HttpSession session = req.getSession();
			JSONObject typeOptionData = (JSONObject)session.getAttribute("typeOptionData");
			String id=echartsService.saveEchartsMesg(typeOptionData,chartType);
			json.put("result", true);
			json.put("id", id);
		} catch (Exception e) {
			json.put("result", false);
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 保存图片信息
	 * @param req
	 * @param res
	 */
	@ResponseBody
	@RequestMapping("/getEchartsMesg")
	public JSONObject getEchartsMesg(HttpServletRequest req,HttpServletResponse res){
		JSONObject json=new JSONObject();
		try {
			String id=req.getParameter("id");
			JSONObject result=echartsService.getEchartsMesg(id);
			if(result.isEmpty()){
				json.put("seccess", false);
			}else{
				json.put("seccess", true);
				json.put("echartsMesg", result);
			}
		} catch (Exception e) {
			json.put("seccess", false);
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 页面提交数据
	 */
	@ResponseBody
	@RequestMapping("/uploadDataHtml")
	public JSONObject uploadDataHtml(HttpServletRequest req,HttpServletResponse res){
		String[] tableData= req.getParameterValues("excelTable");
		JSONObject json = new JSONObject();
		try{
			chartType = req.getParameter("chartType");
			options.put("columns", JSONArray.fromObject(req.getParameter("mesg")));
			options.put("title", req.getParameter("title"));
			options.put("subTitle", req.getParameter("subTitle"));
			optionData = echartsService.getPropertiesValues(chartType);
			JSONObject dataResult = echartsService.dealUploadDataHtml(optionData,chartType,options,tableData);
			//将数据源放入session
			HttpSession session = req.getSession();
			session.setAttribute("typeOptionData", dataResult);
			json.put("result", true);
			json.put("typeOptionData",dataResult);
		}catch(Exception e){
			json.put("result", false);
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 获取ichart列表
	 */
	@ResponseBody
	@RequestMapping("/getIChartList")
	public JSONArray getIChartList(HttpServletRequest req,HttpServletResponse res){
		JSONArray json = echartsService.getIChartList();
		return json;
	}
}
