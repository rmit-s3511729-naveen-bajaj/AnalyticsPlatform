package com.bda.analyticsplatform.core.rdbms.queryGenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.bda.analyticsplatform.core.RDBMSQueryGenerator;
import com.bda.analyticsplatform.core.rdbms.Table;
import com.bda.analyticsplatform.models.Chart;
import com.bda.analyticsplatform.models.Criteria;
import com.bda.analyticsplatform.models.Query;
import com.bda.analyticsplatform.utils.BDAConstants;
import com.bda.analyticsplatform.utils.BDAException;

public class MySQLQueryGenerator extends RDBMSQueryGenerator {

	private  List<Table> tables = new ArrayList<Table>();
    private  List<Criteria> criterias = new ArrayList<Criteria>();
    private  List<String> groupByColumns = new ArrayList<String>();
    private  List<String> orderByColumns = new ArrayList<String>();
    private String noOfRecords;

    private boolean isDistinct = false;

    public void setDistinct(boolean isDistinct)
    {
        this.isDistinct = isDistinct;
    }
    
    public String getNoOfRecords() {
		return noOfRecords;
	}

	public void setNoOfRecords(String noOfRecords) {
		this.noOfRecords = noOfRecords;
	}

    public void addRightOuterJoin(Table table1, String column1, Table table2,
                        String column2)
    {
        String left = getColumnWithAlias(table1, column1);
        String right = getColumnWithAlias(table2, column2)
                       + Criteria.OUTER_JOIN;
        Criteria joinCriteria = new Criteria(left, Criteria.EQUALS, right);
        criterias.add(joinCriteria);
    }

    public void addJoin(Table table1, String column1, Table table2,
                        String column2)
    {
        String left = getColumnWithAlias(table1, column1);
        String right = getColumnWithAlias(table2, column2);
        Criteria joinCriteria = new Criteria(left, Criteria.EQUALS, right);
        criterias.add(joinCriteria);
    }

    public void addCriteria(Table table, String column, String operator,
                            Object value)
    {
        String left = getColumnWithAlias(table, column);
        Criteria simpleCriteria =
            new Criteria(left, operator, value.toString());
        criterias.add(simpleCriteria);
    }

    public void addCriteria(Table table, String column, String operator,
                            List values)
    {
        String left = getColumnWithAlias(table, column);
        StringBuilder right = new StringBuilder();
        ListIterator<Object> valueIterator = values.listIterator();
        right.append("(");
        while (valueIterator.hasNext())
        {
            right.append(valueIterator.next().toString());
            if (valueIterator.hasNext())
            {
                right.append(",");
            }
        }
        right.append(")");

        Criteria simpleCriteria =
            new Criteria(left, operator, right.toString());
        criterias.add(simpleCriteria);
    }

    public void addGroupByColumn(Table table, String column)
    {
        String columnWithAlias = getColumnWithAlias(table, column);
        groupByColumns.add(columnWithAlias);
    }
    
    public void addOrderByColumn(Table table, String column)
    {
        String columnWithAlias = getColumnWithAlias(table, column);
        orderByColumns.add(columnWithAlias);
    }

    public void addTable(Table table)
    {
        tables.add(table);
    }

    public String toString()
    {
        StringBuilder sql = new StringBuilder();
        appendColumnSelect(sql);
        appendTables(sql);
        appendCriterias(sql);
        appendGroupBy(sql);
        appendOrderBy(sql);
        appendLimit(sql);
        return sql.toString();
    }

    private void appendLimit(StringBuilder sql) {
		
		 if(!this.getNoOfRecords().equalsIgnoreCase(BDAConstants.NULL_INDICATOR)){
			 sql.append("LIMIT "); 
			 sql.append(this.getNoOfRecords());
		 }
	}

	private void appendColumnSelect(StringBuilder sql)
    {
        sql.append("SELECT ");
        if (isDistinct)
        {
            sql.append("DISTINCT ");
        }
        ListIterator<Table> tableIterator = tables.listIterator();
        List<String> selectValues = new ArrayList<String>();
        while (tableIterator.hasNext())
        {
            Table table = tableIterator.next();
            selectValues.addAll(table.getColumnsWithAlias());
            selectValues.addAll(table.getGroupFunctions());
        }
        appendSelectValues(sql, selectValues);
    }

    private void appendSelectValues(StringBuilder sql,
                                    List<String> selectValues)
    {
        ListIterator<String> selectValueIterator = selectValues.listIterator();
        while (selectValueIterator.hasNext())
        {
            String selectValue = selectValueIterator.next();
            sql.append(selectValue);
            if (selectValueIterator.hasNext())
            {
                sql.append(",");
            }
            sql.append(" ");
        }
    }
  
    private void appendTables(StringBuilder sql)
    {
        sql.append(" ");
        sql.append("FROM ");
        ListIterator<Table> tableIterator = tables.listIterator();
        while (tableIterator.hasNext())
        {
            Table table = tableIterator.next();
            sql.append(table.getName());
            sql.append(" ");
            sql.append(table.getAlias());
            if (tableIterator.hasNext())
            {
                sql.append(",");
            }
            sql.append(" ");
        }
    }

    private void appendCriterias(StringBuilder sql)
    {
        if (criterias.size() > 0)
        {
            sql.append(" ");
            sql.append("WHERE ");
        }
        ListIterator<Criteria> criteriaIterator = criterias.listIterator();
        while (criteriaIterator.hasNext())
        {
            Criteria criteria = criteriaIterator.next();
            sql.append(criteria);
            sql.append("\n");
            if (criteriaIterator.hasNext())
            {
                sql.append(" AND ");
            }
        }
    }

    private void appendGroupBy(StringBuilder sql)
    {
        if (groupByColumns.size() > 0)
        {
            sql.append(" GROUP BY ");
        }
        ListIterator<String> columnIterator =
            groupByColumns.listIterator();
        while (columnIterator.hasNext())
        {
            String column = columnIterator.next();
            sql.append(column);
            if (columnIterator.hasNext())
            {
                sql.append(",");
            }
            sql.append(" ");
        }
    }
    
    private void appendOrderBy(StringBuilder sql)
    {
        if (orderByColumns.size() > 0)
        {
            sql.append(" ORDER BY ");
        }
        ListIterator<String> columnIterator =
           orderByColumns.listIterator();
        while (columnIterator.hasNext())
        {
            String column = columnIterator.next();
            sql.append(column);
            if (columnIterator.hasNext())
            {
                sql.append(",");
            }
            sql.append(" ");
        }
    }

    private String getColumnWithAlias(Table table1, String column1)
    {
        return table1.getAlias() + "." + column1;
    }
    
    private static String WILDCARD = "%";

	@Override
	public Query generateQuery(Chart params) throws BDAException, Exception {
	
		   	MySQLQueryGenerator sql = new MySQLQueryGenerator(); 
		   	String tableName= params.getTableColumns().keySet().iterator().next();
		    Table table = null;
		        table = new Table(tableName, "t1");
		        table.addColumnsToSelect(params.getChartParams().getxAxis(),params.getChartParams().getxAxisLabel());
		        table.addColumnsToSelect(params.getChartParams().getyAxis(),params.getChartParams().getAggregateFn(),params.getChartParams().getyAxisLabel());
		        if(!params.getChartParams().getzAxis().equalsIgnoreCase(BDAConstants.NULL_INDICATOR))
		        	table.addColumnsToSelect(params.getChartParams().getzAxis(),params.getChartParams().getzAxisLabel());
		     
		        sql.setNoOfRecords(params.getChartParams().getNoOfRecords());   
		        
	        sql.addTable(table);
	        sql.criterias = params.getChartParams().getFilterConditions();
	        if(!params.getChartParams().getAggregateFn().equalsIgnoreCase(BDAConstants.NONE)){
	        	
	        	  	sql.addGroupByColumn(table, params.getChartParams().getxAxis());
	        	  	if(!params.getChartParams().getzAxis().equalsIgnoreCase(BDAConstants.NULL_INDICATOR))
		        		sql.addGroupByColumn(table, params.getChartParams().getzAxis());
	        }
	        System.out.println(params.getChartParams().getOrderBy());
	        if(!params.getChartParams().getOrderBy().equalsIgnoreCase(BDAConstants.NONE)
	        		&& !params.getChartParams().getOrderBy().equalsIgnoreCase(BDAConstants.NULL_INDICATOR)){
	        	
	        	sql.addOrderByColumn(table, params.getChartParams().getOrderBy());
	        }
	       
	       System.out.println(sql.toString());
	        return new Query(sql.toString());

		
	}


}
