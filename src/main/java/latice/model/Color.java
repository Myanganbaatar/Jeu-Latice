package latice.model;

public enum Color {
    YELLOW("\u001B[33m","y"),
    NAVY("\u001B[34m","n"),
    MAGENTA("\u001B[35m","m"),
    RED("\u001B[31m","r"),
    GREEN("\u001B[32m","g"),
    TEAL("\u001B[36m","t");

    private final String ansiCode;
    private final String code;

    Color(String ansiCode,String code) {
        this.ansiCode = ansiCode;
        this.code = code;
    }

    public String getAnsiCode() {
        return ansiCode;
    }
    
    public String getCode() {
    	return code;
    }
}



