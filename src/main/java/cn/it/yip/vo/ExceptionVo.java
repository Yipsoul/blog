package cn.it.yip.vo;

import cn.it.yip.Enum.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 统一异常返回对象
 */
public class ExceptionVo {
    private String msg;
    private int status;

    public ExceptionVo(ExceptionEnum e) {
        this.msg = e.getMsg();
        this.status = e.getCode();
    }
}
