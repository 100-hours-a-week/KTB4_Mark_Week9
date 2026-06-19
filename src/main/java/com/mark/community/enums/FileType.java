package com.mark.community.enums;

public enum FileType {
    IMAGE, UNKNOWN;


    public static FileType checkFileType(String fileType){
        if(fileType.startsWith("image")) return IMAGE;
        return UNKNOWN;
    }
}
