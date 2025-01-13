package utilities;

import java.lang.reflect.Field;

/**
 * Utility class providing regex validation and text formatting methods.
 * Includes methods for validating common input formats such as names, emails, phone numbers, and formatting database columns.
 */
public class Regex {

    private static final String Regex_NAME = "^[A-Za-zÇçĞğİıÖöŞşÜü]{2,30}(?: [A-Za-zÇçĞğİıÖöŞşÜü]{2,30})*$";
    private static final String Regex_SURNAME = "^[A-Za-zÇçĞğİıÖöŞşÜü]{2,30}(?: [A-Za-zÇçĞğİıÖöŞşÜü]{2,30})*$";
    private static final String Regex_USERNAME = "^[A-Za-z0-9._-]{3,20}$";
    private static final String Regex_PASSWORD = "(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[-_.$!%*?&])[A-Za-z\\d-_.$!%*?&]{8,64}$";
    private static final String Regex_EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,}$";
    private static final String Regex_PHONENUMBER = "^\\+\\d{1,4} \\d{10}$";

    /**
     * Validates a name string using predefined regex.
     * @param name the name to validate.
     * @return {@code true} if the name matches the regex; {@code false} otherwise.
     */
    public static boolean validateName(String name) {
        return name.matches(Regex_NAME);
    }

    /**
     * Validates a surname string using predefined regex.
     * @param surname the surname to validate.
     * @return {@code true} if the surname matches the regex; {@code false} otherwise.
     */
    public static boolean validateSurname(String surname) {
        return surname.matches(Regex_SURNAME);
    }

    /**
     * Validates a username string using predefined regex.
     * @param username the username to validate.
     * @return {@code true} if the username matches the regex; {@code false} otherwise.
     */
    public static boolean validateUsername(String username) {
        return username.matches(Regex_USERNAME);
    }

    /**
     * Validates a password string using predefined regex.
     * @param password the password to validate.
     * @return {@code true} if the password matches the regex; {@code false} otherwise.
     */
    public static boolean validatePassword(String password) {
        return password.matches(Regex_PASSWORD);
    }

    /**
     * Validates an email string using predefined regex.
     * @param email the email to validate.
     * @return {@code true} if the email matches the regex; {@code false} otherwise.
     */
    public static boolean validateEmail(String email) {
        return email.matches(Regex_EMAIL);
    }

    /**
     * Validates a phone number string using predefined regex.
     * @param phoneNumber the phone number to validate.
     * @return {@code true} if the phone number matches the regex; {@code false} otherwise.
     */
    public static boolean validatePhoneNumber(String phoneNumber) {
        return phoneNumber.matches(Regex_PHONENUMBER);
    }

    /**
     * Formats a list of column names into an SQL query fragment for filtering or updating.
     * @param columns a comma-separated string of column names.
     * @param purpose the purpose of formatting, either "filter" (for WHERE clause) or "update" (for SET clause).
     * @return the formatted SQL fragment.
     * @throws Exception if the purpose is invalid.
     */
    public static String formatColumns(String columns, String purpose) throws Exception {
        String[] str = columns.split(",");
        StringBuilder strBuilder = new StringBuilder();
        String midString;

        switch (purpose) {
            case "filter":
                midString = " AND";
                break;
            case "update":
                midString = ",";
                break;
            default:
                throw new Exception("ERROR at Regex.formatColumns: Invalid purpose specified.");
        }
        
        for (String s : str) {
            if (s != str[str.length - 1])
                strBuilder.append(s).append(" = ?").append(midString);
            else
                strBuilder.append(s).append(" = ?");
        }

        return strBuilder.toString();
    }

    /**
     * Generates SQL column and value placeholders for a given object's fields, excluding fields with "id" in their names.
     * Utilizes reflection to read the object's data.
     * @param obj the object to generate column and value placeholders from.
     * @return a string array with two elements: the column names and the value placeholders.
     */
    public static String[] formatColumns(Object obj) {
        Class<?> cla = obj.getClass();
        Field[] fields = cla.getDeclaredFields();
        
        StringBuilder columnBuilder = new StringBuilder();
        columnBuilder.append(" (");
        
        for (Field f : fields) {
            if (f.getName().contains("id")) {
                continue;
            }
            if (f != fields[fields.length - 1])
                columnBuilder.append(f.getName()).append(", ");
            else
                columnBuilder.append(f.getName()).append(")");
        }

        StringBuilder valuesBuilder = new StringBuilder();
        valuesBuilder.append("(");

        for (Field f : fields) {
            if (f.getName().contains("id")) {
                continue;
            }
            if (f != fields[fields.length - 1])
                valuesBuilder.append("?, ");
            else
                valuesBuilder.append("?)");
        }

        return new String[] {columnBuilder.toString(), valuesBuilder.toString()};
    }
}
