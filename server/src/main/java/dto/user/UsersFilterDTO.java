package dto.user;

public class UsersFilterDTO {
    public UserFilterDTO userFilter;
    public CustomerFilterDTO customerFilter;
    public SortDTO sort;
    public SearchDTO search;

    public static class UserFilterDTO {
        public boolean customer;
        public boolean trainer;
        public boolean admin;
        public boolean manager;
    }
    public static class CustomerFilterDTO {
        public boolean bronze;
        public boolean silver;
        public boolean gold;
    }
    public static class SortDTO {
        public String type;
        public boolean reverse;
    }
    public static class SearchDTO {
        public boolean username;
        public boolean firstname;
        public boolean lastname;
    }
}
