package IkMen.exceptions;

/**
 *
 * MySql hibák kezelése
 *
 * Created by tom on 2016.03.13..
 */
public class DataBaseException extends  RuntimeException {

    public DataBaseException(String msg){
        super(msg);
    }


}
