package com.nullnothing.relationshipstats;


public class UtilsRelationshipStats {


    public static String stripPhoneNumber(String number) {

        String newNumber = number;
        String removeChar;
        // "+" needs to be first index to remove text Number format
        String[] removeCharacters = {"+", "-", " ", "(", ")"};

        for(int i=0; i<removeCharacters.length; i++) {

            removeChar = removeCharacters[i];
            if(removeCharacters[i] == "+") {
                removeChar = removeChar + "1";
            }

            newNumber = newNumber.replace(removeChar, "");
        }
        return newNumber;
    }

}
