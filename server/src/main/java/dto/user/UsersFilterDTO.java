package dto.user;

public class UsersFilterDTO {
    public UserFilterDTO userFilter;
    public CustomerFilterDTO customerFilter;
    public SortDTO sort;
    public SearchDTO search;

    public static class UserFilterDTO {
        public String type;
    }
    public static class CustomerFilterDTO {
        public String type;
    }
    public static class SortDTO {
        public String type;
        public boolean reverse;
    }
    public static class SearchDTO {
        public String username;
        public String firstname;
        public String lastname;
    }
}
