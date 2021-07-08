package xyc.maruko.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈分页类〉
 *
 * @author maruko
 * @date 2021/7/7 10:49
 * @since 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PageBaseDTO implements Serializable {
    /**
     * 总条数
     */
    private Long total;

    /**
     * 当前页
     */
    private Long current;

    /**
     * 每页条数
     */
    private Long size;
}
