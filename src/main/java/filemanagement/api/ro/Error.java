package filemanagement.api.ro;

public class Error {
    private int errorcode;
    private String errormsg;
    private String errordescription;

    public Error() {
    }

    public Error(int errorcode, String errormsg, String errordescription) {
        this.errorcode = errorcode;
        this.errormsg = errormsg;
        this.errordescription = errordescription;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public String getErrordescription() {
        return errordescription;
    }

    public void setErrordescription(String errordescription) {
        this.errordescription = errordescription;
    }
}
