package com.bda.analyticsplatform.core;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

import javax.validation.metadata.ReturnValueDescriptor;

import com.bda.analyticsplatform.models.Chart;
import com.bda.analyticsplatform.models.ChartParams;
import com.bda.analyticsplatform.models.Criteria;
import com.bda.analyticsplatform.models.FileDSObject;
import com.bda.analyticsplatform.utils.ApplicationUtils;
import com.bda.analyticsplatform.utils.BDAConstants;
import com.bda.analyticsplatform.utils.BDAException;

import flexjson.JSONSerializer;

public class FileDataProcessor {

	public static String getChartData(Chart chart,FileDSObject object) throws Exception{
			
		Map<String,Map<String,Double>> data = new LinkedHashMap<String,Map<String,Double>>();
		Map<String,Double> data2D = new LinkedHashMap<String,Double>();
		Map<String,Double> data2D_avg_count = new LinkedHashMap<String,Double>();
		Object reader = object.initializeReader();
		String[] record = object.readRecord(reader);
		record = object.readRecord(reader);
		
		
		String x_axis = chart.getDimensions().get(0);
		String y_axis = chart.getExpressions().get(0).get("expField");
		String y_axis_agg_fn = chart.getExpressions().get(0).get("aggregate");
		String z_axis = null;
		if(chart.getDimensions().size() > 1){
			z_axis = chart.getDimensions().get(1);
		}
		
		if(z_axis == null){
			int flag = 0;
			while (record!=null) {
				
				flag=0;
				for(Criteria criteria : chart.getFilterConditions()){
					
					if(!(object.getField(criteria.left, record).equalsIgnoreCase(criteria.right.toString()))){
						record = object.readRecord(reader);
						
						flag=1;
						break;
					}
					
				}
				if(flag == 1){
					flag=0;
					
					if(record!=null){
						
						continue;
					}
					else {
						
						break;
					}
				}
				
				String xValue = object.getField(x_axis, record);
				String yValue = object.getField(y_axis, record);
				if(data2D.get(xValue)!=null){
					
				
					
				
				if(!y_axis_agg_fn.equalsIgnoreCase("avg"))
					data2D.put(xValue,getExpressionValue( 
						data2D.get(xValue) ,
						yValue,y_axis_agg_fn));
				else{
					if(ApplicationUtils.isDouble(yValue)){
					data2D.put(xValue, 
							data2D.get(xValue) + 
							Double.parseDouble(yValue));
					data2D_avg_count.put(xValue, 
							data2D.get(xValue) + 
							1);
					}else{
						throw new BDAException("Expression with avg must be numeric");
					}
				}
				}else{
					if(!y_axis_agg_fn.equals("avg"))
						data2D.put(xValue,getExpressionValue( 
							null ,
							yValue,y_axis_agg_fn));
					else{
						if(ApplicationUtils.isDouble(yValue)){
						data2D.put(xValue, 
								Double.parseDouble(yValue));
						data2D_avg_count.put(xValue, 
								1.0);
						}else{
							throw new BDAException("Expression with avg must be numeric");
						}
					}
				}
				record = object.readRecord(reader);
			}
			List<Map<String,Object>> output = new ArrayList<Map<String,Object>>();
			Set<Entry<String,Double>> value = data2D.entrySet();
			Integer limit = Integer.parseInt(chart.getNoOfRecords().trim());
			Integer recs = 0;
			for (Entry<String, Double> entry : value) {
				
				Map<String,Object> finalRecord = new LinkedHashMap<String,Object>();
				finalRecord.put(x_axis, entry.getKey());
				if(y_axis_agg_fn.equals("avg")){
					System.out.println(data2D_avg_count);
					finalRecord.put(x_axis, entry.getValue()/data2D_avg_count.get(entry.getKey()));
				}else{
					finalRecord.put(y_axis, entry.getValue());
				}
				output.add(finalRecord);
				if(recs++ == limit-1)
					break;
			}
			JSONSerializer s = new JSONSerializer();
		
			return s.serialize(output);
		}else{
			while (record!=null) {
				String xValue = object.getField(x_axis, record);
				String zValue = object.getField(z_axis, record);
				String yValue = object.getField(y_axis, record);
				if(data.get(zValue)!=null){
					data2D = data.get(zValue);
					if(data2D.get(xValue)!=null){

						if(y_axis_agg_fn != "avg")
							data2D.put(zValue,getExpressionValue( 
								data2D.get(xValue) ,
								yValue,y_axis_agg_fn));
						else{
							if(!ApplicationUtils.isDouble(yValue)){
							data2D.put(zValue, 
									data2D.get(xValue) + 
									Double.parseDouble(yValue));
							data2D_avg_count.put(zValue, 
									data2D.get(xValue) + 
									1);
							}else{
								throw new BDAException("Expression with avg must be numeric");
							}
						}
							
					}
				data.put(zValue, data2D);
				data.put(zValue+"_avg_count", data2D_avg_count);
				
				}else{
					data2D = new HashMap<String,Double>();
					data2D_avg_count = new HashMap<String,Double>();

					if(y_axis_agg_fn != "avg")
						data2D.put(xValue,getExpressionValue( 
							null ,
							yValue,y_axis_agg_fn));
					else{
						if(!ApplicationUtils.isDouble(yValue)){
						data2D.put(xValue, 
								Double.parseDouble(yValue));
						data2D_avg_count.put(xValue, 
								1.0);
						}else{
							throw new BDAException("Expression with avg must be numeric");
						}
					}
		
					data.put(zValue, data2D);
					data.put(zValue+"_avg_count", data2D_avg_count);

				

				}
				
				
				record = object.readRecord(reader);
			}
			List<Map<String,Object>> output = new ArrayList<Map<String,Object>>();
			Set<Entry<String,Map<String,Double>>> value = data.entrySet();
			Integer limit = Integer.parseInt(chart.getNoOfRecords().trim());
			Integer recs = 0;
			for (Entry<String, Map<String, Double>> entry : value) {
				
				Set<Entry<String,Double>> output2D = entry.getValue().entrySet();
				for (Entry<String, Double> entry2 : output2D) {
					if(!entry2.getKey().endsWith("_avg_count")){
					Map<String,Object> finalRecord = new HashMap<String,Object>();
					finalRecord.put(z_axis, entry.getKey());
					finalRecord.put(x_axis, entry2.getKey());
					if(y_axis_agg_fn.equals("avg")){
						finalRecord.put(y_axis, entry2.getValue()/entry.getValue().get(entry2.getKey()+"_avg_count"));
					}else{
						finalRecord.put(y_axis, entry2.getValue());
					}
					output.add(finalRecord);
					}
				}
				if(recs++ == limit-1)
					break;
			}
			
			JSONSerializer s = new JSONSerializer();
			return s.serialize(output);
		}
	}

	public static Double getExpressionValue(Double previousValue, String value,String aggFn) throws  Exception{
		switch(aggFn){
		case "sum" : 
			if(!ApplicationUtils.isDouble(value)){
				throw new BDAException("Expression with sum must be numeric");
			}else if(previousValue!=null){
				return previousValue + Double.parseDouble(value);
			}else{
				return Double.parseDouble(value);
			}
		case "max" : 
			if(!ApplicationUtils.isDouble(value)){
				throw new BDAException("Expression with max must be numeric");
			}else if(previousValue==null || Double.parseDouble(value)>previousValue){
				return Double.parseDouble(value);
			}else{
				return previousValue;
			}
		case "min" : 
			if(!ApplicationUtils.isDouble(value)){
				throw new BDAException("Expression with min must be numeric");
			}else if(previousValue==null || Double.parseDouble(value)<previousValue){
				return Double.parseDouble(value);
			}else{
				return previousValue;
			}
		case "count" :
			if (previousValue == null) 	return 1.0;
			return previousValue+1;					
		
		
		 default : 
			return null;
		}
		
	}
}
