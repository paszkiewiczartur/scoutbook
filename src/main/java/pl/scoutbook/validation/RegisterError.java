package pl.scoutbook.validation;

public enum RegisterError {
    FIRSTNAMEEMPTY("firstnameEmpty"), 
    LASTNAMEEMPTY("lastnameEmpty"), 
    PASSWORDEMPTY("passwordEmpty"),
    EMAILEMPTY("emailEmpty"),
    EMAILWRONGPATTERN("emailWrongPattern"),
    EMAILDUPLICATE("emailDuplicate"),
    GENDEREMPTY("genderEmpty"),
    BIRTHDAYNOTPAST("birthdayNotPast"),
    BIRTHDAYEMPTY("birthdayEmpty");

    private String description;
    private RegisterError(String description) {
        this.description = description;
    }
    
    @Override 
    public String toString() {
        return description;
    }
}