package com.bda.analyticsplatform.models;

import java.io.Serializable;

public class Criteria implements Serializable{
	
		private static final long serialVersionUID = 4144184711853860158L;
		public static final String EQUALS = "=";
	    public static final String GREATER = ">";
	    public static final String GREATEREQUAL = ">=";
	    public static final String LESS = "<";
	    public static final String LESSEQUAL = "<=";
	    public static final String LIKE = "LIKE";
	    public static final String NOTEQUAL = "<>";
	    public static final String IN = "IN";
	    public static final String OUTER_JOIN = "(+)";

	    public static final String[] operations = {EQUALS,GREATER,GREATEREQUAL,LESS,LESSEQUAL,LIKE,NOTEQUAL,IN};
	    public  String left;
	    public  String operator;
	    public  Object right;

	    public Criteria(String left, String operator, Object right) {
	        this.left = left;
	        this.operator = operator;
	        this.right = right;
	    }
	   
	    public String toString(){
	      StringBuilder criteria = new StringBuilder();
	      criteria.append(left);
	      criteria.append(" ");
	      criteria.append(operator);
	      criteria.append(" '");
	      criteria.append(right);
	      criteria.append("'");
	      return criteria.toString();
	    }

}
