package ${basePackage}.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
* @author ${authorName}
*/
public enum ${enumName} implements DescribedEnumValue<Byte> {
    SINGLE_CHOICE(1, "单选"),
    MULTI_CHOICE(2, "多选"),
    FILL_BLANK(3, "填空"),
    ORDER_SORT(4, "排序"),
    TRUE_FALSE(5, "判断"),
    ;

    private final Byte value;
    private final String description;

    QuestionType(int value, String description) {
        this.value = (byte)value;
        this.description = description;
    }

    public Byte getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}