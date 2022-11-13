package fr.polytech.bbr.fsj.model;

public class File {
    private String fileName;

    public File(){}

    public File(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName(){
        return this.fileName;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }
}
