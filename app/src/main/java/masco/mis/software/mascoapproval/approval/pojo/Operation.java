package masco.mis.software.mascoapproval.approval.pojo;

/**
 * Created by TahmidH_MIS on 12/6/2016.
 */

public class Operation {
    public String att1;
    public String att2;
    public String att3;
    public boolean att4;
    public  String PROId;
    public String ApprovalId;
    public String AutoDtlId;

    public String getAutoDtlId() {
        return AutoDtlId;
    }

    public void setAutoDtlId(String autoDtlId) {
        AutoDtlId = autoDtlId;
    }

    public String getApprovalId() {
        return ApprovalId;
    }

    public void setApprovalId(String approvalId) {
        ApprovalId = approvalId;
    }

    public boolean isAtt4() {
        return att4;
    }

    public void setAtt4(boolean att4) {
        this.att4 = att4;
    }

    public String getAtt1() {
        return att1;
    }

    public void setAtt1(String att1) {
        this.att1 = att1;
    }

    public String getAtt2() {
        return att2;
    }

    public void setAtt2(String att2) {
        this.att2 = att2;
    }

    public String getAtt3() {
        return att3;
    }

    public void setAtt3(String att3) {
        this.att3 = att3;
    }

    public String getPROId() {
        return PROId;
    }

    public void setPROId(String PROId) {
        this.PROId = PROId;
    }
}
