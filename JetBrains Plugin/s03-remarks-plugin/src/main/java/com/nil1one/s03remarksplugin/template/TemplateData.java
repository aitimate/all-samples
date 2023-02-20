package com.nil1one.s03remarksplugin.template;

import com.nil1one.s03remarksplugin.MemoryData;

import java.util.List;

public class TemplateData {
    private String path;
    private String fileName;
    private List<MemoryData.Note> notes;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<MemoryData.Note> getNotes() {
        return notes;
    }

    public void setNotes(List<MemoryData.Note> notes) {
        this.notes = notes;
    }

    public TemplateData(String path, String fileName, List<MemoryData.Note> notes) {
        this.path = path;
        this.fileName = fileName;
        this.notes = notes;
    }
}
