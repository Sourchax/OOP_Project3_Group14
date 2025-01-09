package utilities;

import java.lang.reflect.Field;

public class Regex {
    private static final String Regex_NAME = "^[A-Za-zÇçĞğİıÖöŞşÜü]{2,30}(?: [A-Za-zÇçĞğİıÖöŞşÜü]{2,30})*$";
    private static final String Regex_SURNAME = "^[A-Za-zÇçĞğİıÖöŞşÜü]{2,30}(?: [A-Za-zÇçĞğİıÖöŞşÜü]{2,30})*$";
    private static final String Regex_USERNAME = "^[A-Za-z0-9._-]{3,20}$";
    private static final String Regex_PASSWORD = "(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[-_.$!%*?&])[A-Za-z\\d-_.$!%*?&]{8,64}$";
    private static final String Regex_EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,}$";
    private static final String Regex_PHONENUMBER = "^\\+\\d{1,4} \\d{10}$";


    public static boolean validateName(String name) {
        return name.matches(Regex_NAME);
    }

    public static boolean validateSurname(String surname) {
        return surname.matches(Regex_SURNAME);
    }

    public static boolean validateUsername(String username) {
        return username.matches(Regex_USERNAME);
    }

    public static boolean validatePassword(String password) {
        return password.matches(Regex_PASSWORD);
    }

    public static boolean validateEmail(String email) {
        return email.matches(Regex_EMAIL);
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        return phoneNumber.matches(Regex_PHONENUMBER);
    }

    public static String formatColumns(String columns, String purpose) throws Exception {
        String[] str = columns.split(",");
        StringBuilder strBuilder = new StringBuilder();
        String midString;

        switch(purpose) {
            case "filter":
                midString = " AND";
                break;
            case "update":
                midString = ",";
                break;
            default:
                throw new Exception("ERROR at Regex.formatColumns");
        }
        
        for(String s : str){
            if(s != str[str.length-1])
                strBuilder.append(s + " = ?" + midString);
            else
                strBuilder.append(s + " = ?");
        }

        return strBuilder.toString();
    }

    public static String[] formatColumns(Object obj){
        Class<?> cla = obj.getClass();
        Field[] fields = cla.getDeclaredFields();
        
        StringBuilder columnBuilder = new StringBuilder();
        columnBuilder.append(" (");
        
        for(Field f : fields){
            System.out.println(f.getName());
            if(f.getName().contains("id")){
                continue;
            }
            if(f != fields[fields.length-1])
                columnBuilder.append(f.getName() + ", ");
            else
                columnBuilder.append(f.getName() + ")");
        }

        StringBuilder valuesBuilder = new StringBuilder();
        valuesBuilder.append("(");

        for(Field f : fields){
            System.out.println(f.getName());
            if(f.getName().contains("id")){
                continue;
            }
            if(f != fields[fields.length-1])
                valuesBuilder.append("?, ");
            else
                valuesBuilder.append("?)");
        }

        return new String[] {columnBuilder.toString(), valuesBuilder.toString()};
    }

}