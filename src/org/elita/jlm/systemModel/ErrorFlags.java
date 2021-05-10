package org.elita.jlm.systemModel;

public class ErrorFlags {
    private Boolean noInputs;
    private Boolean noOutputs;
    private Boolean notAllOutputLinked;
    private Boolean notElementInputsLinked;
    private Boolean notAllGatesLinked;


    public Boolean getNoInputs() {
        return noInputs;
    }

    public void setNoInputs(Boolean noInputs) {
        this.noInputs = noInputs;
    }

    public Boolean getNoOutputs() {
        return noOutputs;
    }

    public void setNoOutputs(Boolean noOutputs) {
        this.noOutputs = noOutputs;
    }

    public Boolean getNotAllOutputLinked() {
        return notAllOutputLinked;
    }

    public void setNotAllOutputLinked(Boolean notAllOutputLinked) {
        this.notAllOutputLinked = notAllOutputLinked;
    }

    public Boolean getNotElementInputsLinked() {
        return notElementInputsLinked;
    }

    public void setNotElementInputsLinked(Boolean notElementInputsLinked) {
        this.notElementInputsLinked = notElementInputsLinked;
    }

    public Boolean getNotAllGatesLinked() {
        return notAllGatesLinked;
    }

    public void setNotAllGatesLinked(Boolean notAllGatesLinked) {
        this.notAllGatesLinked = notAllGatesLinked;
    }

    public Boolean isSystemValid() {
        if(noInputs != null && noOutputs != null && notAllOutputLinked != null && notElementInputsLinked != null && notAllGatesLinked != null) {
            return !(noInputs && noOutputs && notAllOutputLinked && notElementInputsLinked && notAllGatesLinked);
        } else return false;
    }
}
