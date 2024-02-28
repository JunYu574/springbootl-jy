package com.jy.emp.vo;

import com.jy.common.vo.Page;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: JunYu
 * @Date: 2024/2/26 19:19
 * @Description:
 * @Version: V1.0.0
 */
@Data
public class EmpQuery extends Page {
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
}
