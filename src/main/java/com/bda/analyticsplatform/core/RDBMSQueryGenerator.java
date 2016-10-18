package com.bda.analyticsplatform.core;

import com.bda.analyticsplatform.models.Chart;

import com.bda.analyticsplatform.models.Query;
import com.bda.analyticsplatform.utils.BDAException;

public abstract class RDBMSQueryGenerator {

	public abstract Query generateQuery(Chart params) throws BDAException, Exception;
	
	

}
