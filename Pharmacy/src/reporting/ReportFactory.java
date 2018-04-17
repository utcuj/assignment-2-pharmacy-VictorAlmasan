package reporting;

public class ReportFactory {

	public Report getReport(String reportType) {
		if (reportType == null) {
			return null;
		}
		if (reportType.equalsIgnoreCase("pdf")) {
			return new PdfReport();

		} else if (reportType.equalsIgnoreCase("csv")) {
			return new CsvReport();
		}
		return null;
	}
}
