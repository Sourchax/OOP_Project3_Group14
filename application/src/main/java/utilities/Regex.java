package utilities;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
                throw new Exception("E1");
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
            if(f != fields[fields.length-1])
                columnBuilder.append(f + ", ");
            else
                columnBuilder.append(f + ")");
        }

        StringBuilder valuesBuilder = new StringBuilder();
        columnBuilder.append("(");

        for(Field f : fields){
            if(f != fields[fields.length-1])
                columnBuilder.append("?, ");
            else
                columnBuilder.append("?)");
        }

        return new String[] {columnBuilder.toString(), valuesBuilder.toString()};
    }

    /*
    public static String getValidName() {
        String name = null;
        boolean isNameValid = false;
        Graphics.clearTerminal();
        while(!isNameValid)
        {
            System.out.println("**********************************************************************");
            System.out.println("                                 NAME                                 ");
            System.out.println("**********************************************************************");
            System.out.println();
            System.out.println("- It can include Turkish characters.");
            System.out.println("- Must be 2 to 30 characters long.");
            System.out.println("- Cannot contain numbers, special characters, or multiple spaces.");
            System.out.println("- If you have two names, separate them with a single space.");
            System.out.println();
            System.out.println("Please enter a name: ");
            UserInput.takeInput("String");
            name = (String) UserInput.item.getItem(); 
            isNameValid = validateName(name);

            if(!isNameValid){
                Graphics.clearTerminal();
                System.out.println("Invalid name! Only letters and spaces are allowed, 2 to 30 characters. Please try again.");
            }

        }
        return name;
    }

    public static String getValidSurname() {
        String surname = null;
        boolean isSurnameValid = false;
        Graphics.clearTerminal();
        while(!isSurnameValid)
        {
            
            System.out.println("**********************************************************************");
            System.out.println("                                SURNAME                               ");
            System.out.println("**********************************************************************");
            System.out.println();
            System.out.println("- It can include Turkish characters.");
            System.out.println("- Must be 2 to 30 characters long.");
            System.out.println("- Cannot contain numbers, special characters, or multiple spaces.");
            System.out.println("- If you have two surname, separate them with a single space.");
            System.out.println();
            System.out.println("Please enter a surname: ");
            UserInput.takeInput("String");
            surname = (String) UserInput.item.getItem(); 
            isSurnameValid = validateSurname(surname);

            if(!isSurnameValid){
                Graphics.clearTerminal();
                System.out.println("Invalid surname! Only letters and spaces are allowed, 2 to 30 characters. Please try again.");
            }

        }
        return surname;
    }

    public static String getValidUsername() {
        String username = null;
        boolean isUsernameValid = false;
        Graphics.clearTerminal();
        while(!isUsernameValid)
        {
            System.out.println("**********************************************************************");
            System.out.println("                               USERNAME                               ");
            System.out.println("**********************************************************************");
            System.out.println();
            System.out.println("- It can include English letters (A-Z, a-z), numbers (0-9), dots (.), underscores (_), and hyphens (-).");
            System.out.println("- Must be 3 to 20 characters long.");
            System.out.println("- Cannot contain spaces or special characters other than ., _, or -.");
            System.out.println();
            System.out.println("Please enter a username: ");
            UserInput.takeInput("String");
            username = (String) UserInput.item.getItem(); 
            isUsernameValid = validateUsername(username);

            if(!isUsernameValid){
                Graphics.clearTerminal();
                System.out.println("Invalid username! 3 to 20 characters, alphanumeric, underscores, periods, and hyphens are allowed. Please try again.");
            }

        }
        return username;
    }


    public static String getValidPassword() {
        String password = null;
        boolean isPasswordValid = false;
        Graphics.clearTerminal();
        while(!isPasswordValid)
        {
            System.out.println("**********************************************************************");
            System.out.println("                               PASSWORD                               ");
            System.out.println("**********************************************************************");
            System.out.println();
            System.out.println("- At least one uppercase English letter (A-Z).");
            System.out.println("- At least one lowercase English letter (a-z).");
            System.out.println("- At least one digit (0-9).");
            System.out.println("- At least one special character from the set (-, _, ., $, !, %, *, ?, &).");
            System.out.println("- Cannot contain spaces or characters outside the allowed set.");
            System.out.println("- Must be between 8 and 64 characters long.");
            System.out.println();
            System.out.println("Please enter a valid password: ");
            UserInput.takeInput("String");
            password = (String) UserInput.item.getItem(); 
            isPasswordValid = validatePassword(password);

            if(!isPasswordValid){
                Graphics.clearTerminal();
                System.out.println("Invalid password! At least 8 characters, must contain at least one uppercase letter, one lowercase letter, one number, and one special character. Please try again.");
            }

        }
        return password;
    }

    public static String getValidEmail() {
        String email = null;
        boolean isEmailValid = false;
        Graphics.clearTerminal();
        while(!isEmailValid)
        {
            System.out.println("**********************************************************************");
            System.out.println("                                  EMAIL                               ");
            System.out.println("**********************************************************************");
            System.out.println();
            System.out.println("- Must include exactly one '@' symbol.");
            System.out.println("- Allowed characters: letters (A-Z, a-z), digits (0-9), '.', '_', '%', '+', '-'.");
            System.out.println("- Must have a valid domain (example: gmail.com).");
            System.out.println();
            System.out.println("Example: example_axample1@gmail.com");
            System.out.println();
            System.out.println("Please enter a valid email: ");
            UserInput.takeInput("String");
            email = (String) UserInput.item.getItem(); 
            isEmailValid = validateEmail(email);

            if(!isEmailValid){
                Graphics.clearTerminal();
                System.out.println("Invalid email! Please try again.");
            }

        }
        return email;
    }

    public static String getValidPhoneNumber() {
        String phoneNumber = null;
        boolean isPhoneNumberValid = false;
        Graphics.clearTerminal();
        while(!isPhoneNumberValid)
        {
            System.out.println("**********************************************************************");
            System.out.println("                            PHONE NUMBER                              ");
            System.out.println("**********************************************************************");
            System.out.println();
            System.out.println("- First enter the country code using the '+' symbol");
            System.out.println("- Then a space and 10 digits.");
            System.out.println();
            System.out.println("Example: +90 1234567890");
            System.out.println();
            System.out.println("Please enter a valid phone number: ");
            UserInput.takeInput("String");
            phoneNumber = (String) UserInput.item.getItem(); 
            isPhoneNumberValid = validatePhoneNumber(phoneNumber);

            if(!isPhoneNumberValid){
                Graphics.clearTerminal();
                System.out.println("Invalid phone number! Please try again.");
            }
        }
        return phoneNumber;
    }
    
    public static boolean validateDateOfBirth(String dateOfBirth) {
        try {
            DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate birthday = LocalDate.parse(dateOfBirth, f);
            LocalDate today = LocalDate.now();
            
            int age = Period.between(birthday, today).getYears();
            Graphics.clearTerminal();
            if(age <= 0)
            {
                System.out.println("Employee cannot be younger than 0 years old.");
                return false;
            }
            
            if(age < 18)
            {
                System.out.println("Employee must be at least 18 years old.");
                return false;
            }
            if(age > 120)
            {
                System.out.println("Employee cannot be over 120 years old.");
                return false;
            }
            return true;
        } catch (DateTimeParseException e) {
            dateOfBirth = null;
            return false;
        }
    }
    
    public static Date promptUserForDateOfBirth() {
        boolean flag = false;
        Date dateOfBirth = null;
        Graphics.clearTerminal();
        while(!flag) {
            try{
                System.out.println("Enter the employee's date of birth (yyyy-MM-dd): ");
                UserInput.takeInput("String");
                String inputDate = (String) UserInput.item.getItem();  
                Graphics.clearTerminal();
                if(validateDateOfBirth(inputDate)) {
                    LocalDate birthday = LocalDate.parse(inputDate);
                    dateOfBirth = Date.valueOf(birthday);
                    flag = true;
                }
                    
            } catch (DateTimeParseException e){
                Graphics.clearTerminal();
                System.out.println("Invalid date! Please enter a valid date (yyyy-MM-dd format).");
            }
        }
        return dateOfBirth;
    }

    public static boolean validateDateOfStart(String dateOfStart, Date dateOfBirth) {
        try {
            DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(dateOfStart, f);
            LocalDate today = LocalDate.now();

            LocalDate birthday = dateOfBirth.toLocalDate();
            int startAge = Period.between(birthday, startDate).getYears();
            Graphics.clearTerminal();
            if(startAge <= 0)
            {
                System.out.println("Employee cannot be hired before he/she was born.");
                return false;
            }
            if(startAge < 18)
            {
                System.out.println("Employee cannot be less than 18 when he/she started.");
                return false;
            }
            if(startDate.isAfter(today)) 
                return false;
            else
                return true;
        } catch (DateTimeParseException e){
            return false;
        }
    }  

    public static Date promptUserForDateOfStart(Date dateOfBirth) {
        boolean flag = false;
        Date dateOfStart = null;
        Graphics.clearTerminal();
        while(!flag) {
            try {
                System.out.println("Enter the employee's start date (yyyy-MM-dd):");
                UserInput.takeInput("String");
                String inputDate = (String) UserInput.item.getItem(); 
                Graphics.clearTerminal();
                if(validateDateOfStart(inputDate, dateOfBirth)) {
                    LocalDate startDay = LocalDate.parse(inputDate);
                    dateOfStart = Date.valueOf(startDay);
                    flag = true;
                }
                
            } catch (DateTimeParseException e) {
                Graphics.clearTerminal();
                System.out.println("Invalid date! Please enter a valid date (yyyy-MM-dd format).");
            }
        }
        return dateOfStart;
    }

    public static String getValidStringField(String field) {
        switch (field) {
        case "name":
            return getValidName();
        case "surname":
            return getValidSurname();
        case "username":
            return getValidUsername();
        case "mail":
            return getValidEmail();
        case "passwd":
            return getValidPassword();
        case "phone_num":
            return getValidPhoneNumber();
        default:
            break;
        }
        return "";
    }
        */
}