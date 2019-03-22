package com.solusindo.kreativa.companyprofilekreativaadmin.table;

public class Product {
    String ID_PRODUCT, JUDUL_PRODUCT, DESK_PRODUCT, TANGGAL_PRODUCT, FOTO_PRODUCT ;

    public void setFOTO_PRODUCT(String FOTO_PRODUCT) {
        this.FOTO_PRODUCT = FOTO_PRODUCT;
    }

    public void setDESK_PRODUCT(String DESK_PRODUCT) {
        this.DESK_PRODUCT = DESK_PRODUCT;
    }

    public void setID_PRODUCT(String ID_PRODUCT) {
        this.ID_PRODUCT = ID_PRODUCT;
    }

    public void setJUDUL_PRODUCT(String JUDUL_PRODUCT) {
        this.JUDUL_PRODUCT = JUDUL_PRODUCT;
    }

    public void setTANGGAL_PRODUCT(String TANGGAL_PRODUCT) {
        this.TANGGAL_PRODUCT = TANGGAL_PRODUCT;
    }

    public String getDESK_PRODUCT() {
        return DESK_PRODUCT;
    }

    public String getID_PRODUCT() {
        return ID_PRODUCT;
    }

    public String getJUDUL_PRODUCT() {
        return JUDUL_PRODUCT;
    }

    public String getTANGGAL_PRODUCT() {
        return TANGGAL_PRODUCT;
    }

    public String getFOTO_PRODUCT() {
        return FOTO_PRODUCT;
    }
}
