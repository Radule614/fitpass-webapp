package dto;

public class AvgGradeRangeDTO {
	public double lowerBound;
	public double upperBound;
	
	public AvgGradeRangeDTO() {}
	public AvgGradeRangeDTO(double lowerBound, double upperBound) {
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}
	
	// Text u formatu lowerBound - upperBound
	public static AvgGradeRangeDTO valueOf(String text) throws NumberFormatException {
		String[] parts = text.split("-");
		if(parts.length == 1) {
			return null;
		}
		
		double lowerBound = Double.valueOf(parts[0]);	
		double upperBound = Double.valueOf(parts[1]);
		return new AvgGradeRangeDTO(lowerBound, upperBound);
		
	}
}
