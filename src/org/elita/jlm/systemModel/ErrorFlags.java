package org.elita.jlm.systemModel;

public class ErrorFlags {
    Boolean noInputs;
    Boolean noOutputs;
    Boolean notAllOutputLinked;
    Boolean notElementInputsLinked;


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

    public Boolean isSystemValid() {
        if(noInputs != null && noOutputs != null && notAllOutputLinked != null && notElementInputsLinked != null) {
            return noInputs && noOutputs && notAllOutputLinked && notElementInputsLinked;
        } else return false;
    }
}
