package org.example.Constant;

public enum RoomType {
    STANDARD(1),
    MODERATE(2),
    SUPERIOR(3),
    JUNIOR_SUITE(4),
    SUITE(5);
    private int value;
    RoomType(int value){
        this.value = value;
    }
    public static RoomType roomType(int value){
        for(RoomType type: RoomType.values()){
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid room type value: " + value);
    }
}
