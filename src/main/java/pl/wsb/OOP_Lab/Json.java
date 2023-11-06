package pl.wsb.OOP_Lab;

public interface Json {
    default boolean isObjectNotNull(Object object){
        return object != null;
    };

}
