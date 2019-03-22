package com.solusindo.kreativa.companyprofilekreativaadmin.table;

public class Berita {
    private String ID_BERITA, JUDUL_BERITA, DESK_BERITA, TANGGAL, PICTURE_BERITA, DATETIME_TGL;

    public void setDATETIME_TGL(String DATETIME_TGL) {
        this.DATETIME_TGL = DATETIME_TGL;
    }

    public void setPICTURE_BERITA(String PICTURE_BERITA) {
        this.PICTURE_BERITA = PICTURE_BERITA;
    }

    public void setDESK_BERITA(String DESK_BERITA) {
        this.DESK_BERITA = DESK_BERITA;
    }

    public void setID_BERITA(String ID_BERITA) {
        this.ID_BERITA = ID_BERITA;
    }

    public void setJUDUL_BERITA(String JUDUL_BERITA) {
        this.JUDUL_BERITA = JUDUL_BERITA;
    }

    public void setTANGGAL(String TANGGAL) {
        this.TANGGAL = TANGGAL;
    }

    public String getDESK_BERITA() {
        return DESK_BERITA;
    }

    public String getID_BERITA() {
        return ID_BERITA;
    }

    public String getDATETIME_TGL() {
        return DATETIME_TGL;
    }

    public String getJUDUL_BERITA() {
        return JUDUL_BERITA;
    }

    public String getTANGGAL() {
        return TANGGAL;
    }

    public String getPICTURE_BERITA() {
        return PICTURE_BERITA;
    }
}
