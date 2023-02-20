package com.nil1one.s03remarksplugin;

import java.util.ArrayList;
import java.util.List;

public class MemoryData {

    public static String SELECTED_TEXT;
    public static String FILE_NAME;

    public static List<Note> notes = new ArrayList<>();

    public static class Note {
        private String title;
        private String remarks;
        private String content;
        private String fileName;

        private String fileType;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileType() {
            return fileType;
        }

        public void setFileType(String fileType) {
            this.fileType = fileType;
        }

        public Note(String title, String remarks) {
            this.title = title;
            this.remarks = remarks;

            this.fileName = MemoryData.FILE_NAME;
            this.content = MemoryData.SELECTED_TEXT;
            this.fileType = this.fileName.substring(this.fileName.lastIndexOf(".") + 1);
        }

        @Override
        public String toString() {
            return "Note{" +
                    "title='" + title + '\'' +
                    ", remarks='" + remarks + '\'' +
                    ", content='" + content + '\'' +
                    ", fileName='" + fileName + '\'' +
                    ", fileType='" + fileType + '\'' +
                    '}';
        }

        public String[] toTableModel() {
            String[] model = new String[4];
            model[0] = this.title;
            model[1] = this.remarks;
            model[2] = this.fileName;
            model[3] = this.content;
            return model;
        }

    }
}
