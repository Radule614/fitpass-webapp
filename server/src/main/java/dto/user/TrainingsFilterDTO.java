package dto.user;

public class TrainingsFilterDTO {
	public SearchDTO search;
	public SortDTO sort;
	public FilterDTO filter;
	
	public static class SearchDTO {
		public String facilityName;
	}
	
	public static class SortDTO {
		public boolean reverse;
	}
	
	public static class FilterDTO {
		public String facilityType;
		public String trainingType;
	}
}
