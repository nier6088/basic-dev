package io.github.nier6088.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class BaseEntity {
    @TableId("id")
    public Long id;

    @TableField("record_status")
    public Integer recordStatus;
}
