package com.solusindo.kreativa.companyprofilekreativaadmin.table;

public class Peserta_event {
    String ID_PESERTA, ID_EVENT, NAMA_PESERTA, NO_HP, PENDIDIKAN_TERAKHIR, JENIS_KELAMIN, ALAMAT_PESERTA,
            EMAIL_PESERTA;

    public void setID_EVENT(String ID_EVENT) {
        this.ID_EVENT = ID_EVENT;
    }

    public void setALAMAT_PESERTA(String ALAMAT_PESERTA) {
        this.ALAMAT_PESERTA = ALAMAT_PESERTA;
    }

    public void setID_PESERTA(String ID_PESERTA) {
        this.ID_PESERTA = ID_PESERTA;
    }

    public void setNAMA_PESERTA(String NAMA_PESERTA) {
        this.NAMA_PESERTA = NAMA_PESERTA;
    }

    public void setEMAIL_PESERTA(String EMAIL_PESERTA) {
        this.EMAIL_PESERTA = EMAIL_PESERTA;
    }

    public void setNO_HP(String NO_HP) {
        this.NO_HP = NO_HP;
    }

    public void setJENIS_KELAMIN(String JENIS_KELAMIN) {
        this.JENIS_KELAMIN = JENIS_KELAMIN;
    }

    public void setPENDIDIKAN_TERAKHIR(String PENDIDIKAN_TERAKHIR) {
        this.PENDIDIKAN_TERAKHIR = PENDIDIKAN_TERAKHIR;
    }

    public String getID_EVENT() {
        return ID_EVENT;
    }

    public String getID_PESERTA() {
        return ID_PESERTA;
    }

    public String getJENIS_KELAMIN() {
        return JENIS_KELAMIN;
    }

    public String getNAMA_PESERTA() {
        return NAMA_PESERTA;
    }

    public String getNO_HP() {
        return NO_HP;
    }

    public String getALAMAT_PESERTA() {
        return ALAMAT_PESERTA;
    }

    public String getEMAIL_PESERTA() {
        return EMAIL_PESERTA;
    }

    public String getPENDIDIKAN_TERAKHIR() {
        return PENDIDIKAN_TERAKHIR;
    }
}
