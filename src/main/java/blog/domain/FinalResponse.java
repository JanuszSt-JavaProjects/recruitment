package blog.domain;

public class FinalResponse {

    private int code = 200;
    private String stringToSend;

    public FinalResponse(int code, String stringToSend) {
        this.code = code;
        this.stringToSend = stringToSend;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStringToSend() {
        return stringToSend;
    }

    public void setStringToSend(String stringToSend) {
        this.stringToSend = stringToSend;
    }
}
