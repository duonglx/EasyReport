package org.easyframework.report.engine;

import org.easyframework.report.engine.data.LayoutType;
import org.easyframework.report.engine.data.ReportDataSet;
import org.easyframework.report.engine.data.ReportDataSource;
import org.easyframework.report.engine.data.ReportParameter;
import org.easyframework.report.engine.data.ReportTable;
import org.easyframework.report.engine.query.Queryer;

/**
 * 报表产生器类
 * 
 */
public class ReportGenerator {

	/**
	 * 
	 * @param ds
	 * @param parameter
	 * @return ReportTable
	 */
	public static ReportTable generate(ReportDataSource ds, ReportParameter parameter) {
		return generate(getDataSet(ds, parameter), parameter);
	}

	/**
	 * 
	 * @param dataSet
	 * @param parameter
	 * @return ReportTable
	 */
	public static ReportTable generate(ReportDataSet dataSet, ReportParameter parameter) {
		ReportBuilder builder = createBuilder(dataSet, parameter);
		ReportDirector director = new ReportDirector(builder);
		director.build();
		return builder.getTable();
	}

	/**
	 * 
	 * @param queryer
	 * @param parameter
	 * @return ReportTable
	 */
	public static ReportTable generate(Queryer queryer, ReportParameter parameter) {
		return generate(getDataSet(queryer, parameter), parameter);
	}

	/**
	 * 
	 * @param ds
	 * @param parameter
	 * @return
	 */
	public static ReportDataSet getDataSet(ReportDataSource ds, ReportParameter parameter) {
		return new DataExecutor(ds, parameter).execute();
	}

	/**
	 * 
	 * @param queryer
	 * @param parameter
	 * @return
	 */
	public static ReportDataSet getDataSet(Queryer queryer, ReportParameter parameter) {
		return new DataExecutor(queryer, parameter).execute();
	}

	private static ReportBuilder createBuilder(ReportDataSet reportDataSet, ReportParameter parameter) {
		if (parameter.getStatColumnLayout() == LayoutType.HORIZONTAL) {
			return new HorizontalStatColumnReportBuilder(reportDataSet, parameter);
		}
		return new VerticalStatColumnReportBuilder(reportDataSet, parameter);
	}
}
