package bean;

/**
 * Created by zhenya huang on 2016/7/13.
 */
public enum CBCommonResultCode {

    SUCCESS(0), FAILED(-1), EXCEPTION(-2), OTHERS(-3);

    private int value;

    CBCommonResultCode(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
