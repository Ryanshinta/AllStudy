package live.allstudy.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Ryan
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ResponseResult<T> {
    /**
     * HTTP Status Codes
     */
    private Integer code;

    /**
     * Response Msg to front-end
     */
    private String msg;

    /**
     * The Data in the json
     */
    private T data;

    public ResponseResult(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(Integer code, T data ){
        this.code = code;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg,T data ){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
