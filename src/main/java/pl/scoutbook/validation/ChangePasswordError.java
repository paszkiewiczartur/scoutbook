package pl.scoutbook.validation;

public enum ChangePasswordError {
    CODEEMPTY("codeEmpty"),
    CODENOTFOUND("codeNotFound"),
    PASSWORDEMPTY("passwordEmpty"),
    HOURPASSED("hourPassed");
	
    private String description;
    private ChangePasswordError(String description) {
        this.description = description;
    }
    
    @Override 
    public String toString() {
        return description;
    }
}