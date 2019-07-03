package com.solusindo.kreativa.companyprofilekreativaadmin.table;

public class Join_event {
    String ID_JOIN_EVENT, ID_PESERTA, ID_EVENT, TGL_JOIN, BUKTI_PEMBAYARAN, STATUS_JOIN, NAMA_PESERTA, NO_HP, PENDIDIKAN_TERAKHIR,
            JENIS_KELAMIN, ALAMAT_PESERTA;

    public void setID_JOIN_EVENT(String ID_JOIN_EVENT) {
        this.ID_JOIN_EVENT = ID_JOIN_EVENT;
    }

    public void setID_PESERTA(String ID_PESERTA) {
        this.ID_PESERTA = ID_PESERTA;
    }

    public void setID_EVENT(String ID_EVENT) {
        this.ID_EVENT = ID_EVENT;
    }

    public void setTGL_JOIN(String TGL_JOIN) {
        this.TGL_JOIN = TGL_JOIN;
    }

    public void setNAMA_PESERTA(String NAMA_PESERTA) {
        this.NAMA_PESERTA = NAMA_PESERTA;
    }

    public void setBUKTI_PEMBAYARAN(String BUKTI_PEMBAYARAN) {
        this.BUKTI_PEMBAYARAN = BUKTI_PEMBAYARAN;
    }

    public void setSTATUS_JOIN(String STATUS_JOIN) {
        this.STATUS_JOIN = STATUS_JOIN;
    }

    public void setNO_HP(String NO_HP) {
        this.NO_HP = NO_HP;
    }

    public void setPENDIDIKAN_TERAKHIR(String PENDIDIKAN_TERAKHIR) {
        this.PENDIDIKAN_TERAKHIR = PENDIDIKAN_TERAKHIR;
    }

    public void setJENIS_KELAMIN(String JENIS_KELAMIN) {
        this.JENIS_KELAMIN = JENIS_KELAMIN;
    }

    public void setALAMAT_PESERTA(String ALAMAT_PESERTA) {
        this.ALAMAT_PESERTA = ALAMAT_PESERTA;
    }

    public String getPENDIDIKAN_TERAKHIR() {
        return PENDIDIKAN_TERAKHIR;
    }

    public String getNO_HP() {
        return NO_HP;
    }

    public String getNAMA_PESERTA() {
        return NAMA_PESERTA;
    }

    public String getID_PESERTA() {
        return ID_PESERTA;
    }

    public String getID_EVENT() {
        return ID_EVENT;
    }

    public String getBUKTI_PEMBAYARAN() {
        return BUKTI_PEMBAYARAN;
    }

    public String getALAMAT_PESERTA() {
        return ALAMAT_PESERTA;
    }

    public String getID_JOIN_EVENT() {
        return ID_JOIN_EVENT;
    }

    public String getJENIS_KELAMIN() {
        return JENIS_KELAMIN;
    }

    public String getSTATUS_JOIN() {
        return STATUS_JOIN;
    }

    public String getTGL_JOIN() {
        return TGL_JOIN;
    }
}
