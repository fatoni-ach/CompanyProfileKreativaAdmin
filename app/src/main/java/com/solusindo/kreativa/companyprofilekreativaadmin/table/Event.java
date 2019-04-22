package com.solusindo.kreativa.companyprofilekreativaadmin.table;

public class Event {
    String ID_EVENT, NAMA_EVENT, TGL_EVENT, TEMPAT, KAPISITAS, HTM, FOTO_EVENT, STATUS;

    public void setID_EVENT(String ID_EVENT) {
        this.ID_EVENT = ID_EVENT;
    }

    public void setHTM(String HTM) {
        this.HTM = HTM;
    }

    public void setKAPISITAS(String KAPISITAS) {
        this.KAPISITAS = KAPISITAS;
    }

    public void setFOTO_EVENT(String FOTO_EVENT) {
        this.FOTO_EVENT = FOTO_EVENT;
    }

    public void setNAMA_EVENT(String NAMA_EVENT) {
        this.NAMA_EVENT = NAMA_EVENT;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public void setTEMPAT(String TEMPAT) {
        this.TEMPAT = TEMPAT;
    }

    public void setTGL_EVENT(String TGL_EVENT) {
        this.TGL_EVENT = TGL_EVENT;
    }

    public String getFOTO_EVENT() {
        return FOTO_EVENT;
    }

    public String getHTM() {
        return HTM;
    }

    public String getID_EVENT() {
        return ID_EVENT;
    }

    public String getKAPISITAS() {
        return KAPISITAS;
    }

    public String getNAMA_EVENT() {
        return NAMA_EVENT;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public String getTEMPAT() {
        return TEMPAT;
    }

    public String getTGL_EVENT() {
        return TGL_EVENT;
    }

}
