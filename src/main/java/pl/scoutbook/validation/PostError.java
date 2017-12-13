package pl.scoutbook.validation;

public enum PostError {
    CONTENTEMPTY("contentEmpty"),
    CATEGORYEMPTY("categoryEmpty"),
    OWNEREMPTY("ownerEmpty"),
    TOOMANYCATEGORIES("tooManyCategories");
	
    private String description;
    private PostError(String description) {
        this.description = description;
    }
    
    @Override 
    public String toString() {
        return description;
    }
}