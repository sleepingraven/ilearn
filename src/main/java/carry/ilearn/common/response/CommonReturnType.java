package carry.ilearn.common.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Carry
 * @since 2021/4/9
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonReturnType<T> {
    /**
     * 对应请求的返回处理结果。"success" 或 "fail"
     */
    private String status;
    /**
     * 若 status = success，表示前端需要的 json 数据
     * 若 status = fail，则使用通用的错误码格式
     */
    private T data;
    
    public static CommonReturnType<?> empty() {
        return new CommonReturnType<>("success", null);
    }
    
    public static <T> CommonReturnType<T> create(T result) {
        return new CommonReturnType<>("success", result);
    }
    
    public static <T> CommonReturnType<T> withError(T t) {
        return new CommonReturnType<>("failure", t);
    }
    
}
