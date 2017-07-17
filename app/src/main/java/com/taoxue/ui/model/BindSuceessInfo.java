package com.taoxue.ui.model;

/**
 * Created by User on 2017/5/3.
 */

public class BindSuceessInfo extends BaseModel {
    private  String readerName;
    private  String reader_card_id;
    private  String myLib_name;

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getReader_card_id() {
        return reader_card_id;
    }

    public void setReader_card_id(String reader_card_id) {
        this.reader_card_id = reader_card_id;
    }

    public String getMyLib_name() {
        return myLib_name;
    }

    public void setMyLib_name(String myLib_name) {
        this.myLib_name = myLib_name;
    }

    @Override
    public String toString() {
        return "BindSuceessInfo{" +
                "readerName='" + readerName + '\'' +
                ", reader_card_id='" + reader_card_id + '\'' +
                ", myLib_name='" + myLib_name + '\'' +
                '}';
    }
}
