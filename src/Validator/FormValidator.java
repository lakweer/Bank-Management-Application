package Validator;

import java.util.regex.Pattern;

public class FormValidator {

    //Check the email
    public static boolean emailValidate(String emailString) {
        if (emailString != null) {
            return Pattern.matches("^^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$", emailString);
        } else {
            return false;
        }
    }

    /***
     * Checking the password. Constraints are,
     * It should be between 8-20 letters
     * Must have at least one digit, capital letter and a special character #$&@*!
     */
    public static boolean passwordValidate(String passwordString) {
        if (passwordString != null) {
            return Pattern.matches("((?=.*\\d)(?=.*[A-Z])(?=.*[#$&@*!]).{8,20})", passwordString);
        } else {
            return false;
        }
    }

    // Assuming that # of digits in a phone number should be exactly 10
    public boolean phoneValidate(String phoneNumberString){
        if (phoneNumberString != null){
            return ((phoneNumberString.charAt(0) == '0') && (phoneNumberString.length() == 10));
        }
        return false;
    }

    public static boolean nicNumberValidate(String nic){
        if(nic != null){
            return ((nic.charAt(9) == 'V') && (nic.length() == 10) && (nic.substring(0,8).matches("\\d+")));
        }else{
            return false;
        }
    }

    public boolean isPositive(float value){
        return (value >= 0);
    }

}
